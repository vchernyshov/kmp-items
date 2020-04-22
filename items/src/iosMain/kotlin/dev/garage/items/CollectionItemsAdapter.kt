package dev.garage.items

import kotlinx.cinterop.ExportObjCClass
import platform.Foundation.NSIndexPath
import platform.UIKit.UICollectionView
import platform.UIKit.UICollectionViewCell
import platform.UIKit.UICollectionViewDataSourceProtocol
import platform.UIKit.UINib
import platform.darwin.NSInteger
import platform.darwin.NSObject

typealias UICollectionViewReloadHandler = (UICollectionView, oldData: List<Item>?, newData: List<Item>?) -> Unit

@ExportObjCClass
class CollectionItemsAdapter internal constructor(
    private val view: UICollectionView,
    private val factory: DelegatesFactory,
    private val reloadHandler: UICollectionViewReloadHandler
) : NSObject(), UICollectionViewDataSourceProtocol {

    private val eventListenerWrapper = object : ItemEventListener {
        override fun onItemEvent(event: ItemEvent) {
            listener?.onItemEvent(event)
        }
    }
    private val manager = IosDelegatesManager<UICollectionView, UICollectionViewCell>(factory, eventListenerWrapper)
    private val itemsView = object : ItemsView<UICollectionView, UICollectionViewCell> {
        override fun register(nib: UINib, id: String) {
            view.registerNib(nib, id)
        }

        override fun getReusableCell(id: String, indexPath: NSIndexPath): UICollectionViewCell {
            return view.dequeueReusableCellWithReuseIdentifier(id, indexPath)
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
    override fun collectionView(
        collectionView: UICollectionView,
        cellForItemAtIndexPath: NSIndexPath
    ): UICollectionViewCell {
        return manager.getReusableCell(itemsView, items, cellForItemAtIndexPath)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun collectionView(
        collectionView: UICollectionView,
        numberOfItemsInSection: NSInteger
    ): NSInteger {
        return items.size.toLong()
    }

    override fun numberOfSectionsInCollectionView(collectionView: UICollectionView): NSInteger {
        return 1
    }
}