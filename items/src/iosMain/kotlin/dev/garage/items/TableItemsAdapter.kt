package dev.garage.items

import kotlinx.cinterop.ExportObjCClass
import platform.Foundation.NSIndexPath
import platform.UIKit.UINib
import platform.UIKit.UITableView
import platform.UIKit.UITableViewCell
import platform.UIKit.UITableViewDataSourceProtocol
import platform.darwin.NSInteger
import platform.darwin.NSObject

typealias UITableViewReloadHandler = (UITableView, oldData: List<Item>?, newData: List<Item>?) -> Unit

@ExportObjCClass
class TableItemsAdapter internal constructor(
    private val view: UITableView,
    private val factory: DelegatesFactory,
    private val reloadHandler: UITableViewReloadHandler
) : NSObject(), UITableViewDataSourceProtocol {

    private val eventListenerWrapper = object : ItemEventListener {
        override fun onItemEvent(event: ItemEvent) {
            listener?.onItemEvent(event)
        }
    }
    private val manager = IosDelegatesManager<UITableView, UITableViewCell>(factory, eventListenerWrapper)
    private val itemsView = object : ItemsView<UITableView, UITableViewCell> {
        override fun register(nib: UINib, id: String) {
            view.registerNib(nib, forCellReuseIdentifier = id)
        }

        override fun getReusableCell(id: String, indexPath: NSIndexPath): UITableViewCell {
            return view.dequeueReusableCellWithIdentifier(id, indexPath)
        }
    }

    var items: List<Item> = emptyList()
        set(value) {
            val old = field
            field = value
            reloadHandler(view, old, value)
        }

    var listener: ItemEventListener? = null

    init {
        view.dataSource = this
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun tableView(
        tableView: UITableView,
        cellForRowAtIndexPath: NSIndexPath
    ): UITableViewCell {
        return manager.getReusableCell(itemsView, items, cellForRowAtIndexPath)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun tableView(tableView: UITableView, numberOfRowsInSection: NSInteger): NSInteger {
        return items.size.toLong()
    }

    override fun numberOfSectionsInTableView(tableView: UITableView): NSInteger {
        return 1
    }
}