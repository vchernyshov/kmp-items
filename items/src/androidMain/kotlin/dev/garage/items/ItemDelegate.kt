package dev.garage.items

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Delegate of specific [Item] that responses for creating and binds view for item.
 */
actual abstract class ItemDelegate {

    /**
     * Listener for any ui events from item view
     */
    lateinit var eventListener: ItemEventListener

    /**
     * Creates ViewHolder for delegate. By default creates [BaseViewHolder].
     */
    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    /**
     * Place your bind logic here.
     *
     * @param items List of items.
     * @param position Position of item in [items].
     * @param holder ViewHolder for this item type.
     */
    open fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
    }

    /**
     * Called when item changed with payloads.
     *
     * More that one property of item can be changed during particular update.
     * In this case element in [payloads] can be Collection with nested payloads,
     * by default this method iterate over each payload in [payloads]
     * and call [onBindViewHolder] with nested payload
     * or with [payloads] element if it is not Collection.
     *
     * @param items List of items.
     * @param position Position of item in [items].
     * @param holder ViewHolder for this item type.
     * @param payloads List of payloads objects.
     */
    open fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) {
        payloads.forEach { payload ->
            when (payload) {
                is Collection<*> -> payload.forEach { nestedPayload ->
                    nestedPayload?.let {
                        onBindViewHolder(items, position, holder, nestedPayload)
                    }
                }
                else -> onBindViewHolder(items, position, holder, payload)
            }
        }
    }

    /**
     * Extended version of [onBindViewHolder] with payloads.
     *
     * @param items List of items.
     * @param position Position of item in items.
     * @param holder ViewHolder for this item type.
     * @param payload Nested payload.
     */
    open fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: Any
    ) {
    }

    /**
     * @see [RecyclerView.Adapter.onViewRecycled]
     */
    open fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    /**
     * @see [RecyclerView.Adapter.onFailedToRecycleView]
     */
    open fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean = false

    /**
     * @see [RecyclerView.Adapter.onViewAttachedToWindow]
     */
    open fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {}

    /**
     * @see [RecyclerView.Adapter.onViewDetachedFromWindow]
     */
    open fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {}

    fun sendEvent(event: ItemEvent) {
        eventListener.onItemEvent(event)
    }

    inline fun RecyclerView.ViewHolder.setItemViewClickListener() {
        itemView.clicks<Item>(this) { item, _ -> ItemClicked(item) }
    }

    inline fun <reified T : Item> View.clicks(
        holder: RecyclerView.ViewHolder,
        crossinline event: (item: T, position: Int) -> ItemEvent
    ) {
        setOnClickListener {
            holder.withAdapterPosition<T> { item, position ->
                sendEvent(event.invoke(item, position))
            }
        }
    }
}