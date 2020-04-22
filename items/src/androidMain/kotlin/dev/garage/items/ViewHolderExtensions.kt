package dev.garage.items

import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.ViewHolder.items(): List<Item>? {
    val parent = itemView.parent as? RecyclerView ?: return null
    val adapter = parent.adapter as? ItemsAdapter ?: return null
    return adapter.currentList
}

/**
 * Extension function that invoke given [block] if position not equals [RecyclerView.NO_POSITION]
 */
inline fun <reified T : Item> RecyclerView.ViewHolder.withAdapterPosition(
    block: (item: T, position: Int) -> Unit
) {
    with(adapterPosition) {
        if (this != RecyclerView.NO_POSITION) {
            val items = items()
            if (items != null && this >= 0 && this < items.size) {
                block.invoke(items[this] as T, this)
            }
        }
    }
}