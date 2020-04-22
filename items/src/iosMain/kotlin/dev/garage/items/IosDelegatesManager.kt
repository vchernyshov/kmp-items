package dev.garage.items

import platform.Foundation.NSIndexPath
import platform.UIKit.item

class IosDelegatesManager<V, C>(
    factory: DelegatesFactory,
    private val listener: ItemEventListener
) : CommonDelegatesManager(factory) {

    fun getReusableCell(view: ItemsView<V, C>, items: List<Item>, indexPath: NSIndexPath): C {
        val item = items[indexPath.item.toInt()]
        val delegate = createDelegateIfNeeded(item) { created ->
            created.eventListener = listener
            created.registerCellIn(view, item.reusableId)
        }
        val reusableCell = view.getReusableCell(item.reusableId, indexPath)
//        (reusableCell as? Cell)?.setItemEventListener(listener)
        delegate.bindCell(item, reusableCell)
        return reusableCell
    }
}