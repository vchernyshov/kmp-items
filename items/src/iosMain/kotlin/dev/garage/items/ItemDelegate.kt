package dev.garage.items

import platform.Foundation.NSBundle
import platform.UIKit.UINib
import platform.darwin.NSInteger

actual abstract class ItemDelegate {

    /**
     * Listener for any ui events from item view
     */
    lateinit var eventListener: ItemEventListener

    abstract fun xibName(): String

    open fun <View, Cell> registerCellIn(view: ItemsView<View, Cell>, reusableId: String) {
        val nib = UINib.nibWithNibName(xibName(), NSBundle.bundleWithIdentifier(reusableId))
        view.register(nib, reusableId)
    }

    open fun <Cell> onBindViewCell(items: List<Item>, position: NSInteger, cell: Cell) {

    }

    fun sendEvent(event: ItemEvent) {
        eventListener.onItemEvent(event)
    }
}