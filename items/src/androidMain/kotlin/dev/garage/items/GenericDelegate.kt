package dev.garage.items

import androidx.recyclerview.widget.RecyclerView

abstract class GenericDelegate<T: Item, V: RecyclerView.ViewHolder>: ItemDelegate() {

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        bind(items, items[position] as T, position, holder as V)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: Any
    ) {
        bind(items, items[position] as T, position, holder as V, payload)
    }

    final override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) {
        super.onBindViewHolder(items, position, holder, payloads)
    }

    open fun bind(items: List<Item>, item: T, position: Int, holder: V) {

    }

    open fun bind(items: List<Item>, item: T, position: Int, holder: V, payload: Any) {

    }
}