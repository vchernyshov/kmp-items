package dev.garage.items

import androidx.recyclerview.widget.DiffUtil

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.areContentsTheSame(newItem)

    override fun getChangePayload(oldItem: Item, newItem: Item): Any =
        oldItem.getChangePayload(newItem)
}