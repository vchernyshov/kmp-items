package dev.garage.items

import platform.darwin.NSInteger

abstract class GenericDelegate<T : Item, C : Cell> : ItemDelegate() {

    @Suppress("UNCHECKED_CAST")
    final override fun <Cell> onBindViewCell(items: List<Item>, position: NSInteger, cell: Cell) {
        bind(items, items[position.toInt()] as T, position, cell as C)
    }

    open fun bind(items: List<Item>, item: T, position: NSInteger, cell: C) {

    }
}