package dev.garage.items

abstract class CellDelegate<T : Item, C : Cell> : ItemDelegate() {

    @Suppress("UNCHECKED_CAST")
    final override fun <Cell> bindCell(item: Item, cell: Cell) {
        onBindViewCell(item as T, cell as C)
    }

    open fun onBindViewCell(item: T, cell: C) {

    }
}