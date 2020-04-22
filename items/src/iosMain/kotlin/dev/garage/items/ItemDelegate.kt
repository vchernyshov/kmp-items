package dev.garage.items

import platform.UIKit.UINib

actual abstract class ItemDelegate {

    /**
     * Listener for any ui events from item view
     */
    lateinit var eventListener: ItemEventListener

    abstract fun nibName(): String

    open fun <View, Cell> registerCellIn(view: ItemsView<View, Cell>, reusableId: String) {
        val nib = UINib.nibWithNibName(nibName(), null)
        view.register(nib, reusableId)
    }

    open fun <Cell> bindCell(item: Item, cell: Cell) {

    }

    fun sendEvent(event: ItemEvent) {
        eventListener.onItemEvent(event)
    }
}