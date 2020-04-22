package dev.garage.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AndroidDelegatesManager(
    private val eventListener: ItemEventListener,
    factory: DelegatesFactory
) : CommonDelegatesManager(factory) {

    fun getItemViewTypeAndCreateDelegate(items: List<Item>, position: Int): Int {
        val item = items[position]
        createDelegateIfNeeded(item) { delegate ->
            delegate.eventListener = eventListener
        }
        return viewTypeFromItem(item)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getDelegateOrThrow(viewType).onCreateViewHolder(parent)

    fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        vh: RecyclerView.ViewHolder
    ) = getDelegateOrThrow(vh.itemViewType).onBindViewHolder(items, position, vh)

    fun onBindViewHolder(
        items: List<Item>,
        position: Int, vh: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) = getDelegateOrThrow(vh.itemViewType).onBindViewHolder(items, position, vh, payloads)

    fun onViewRecycled(vh: RecyclerView.ViewHolder) =
        getDelegateOrThrow(vh.itemViewType).onViewRecycled(vh)

    fun onFailedToRecycleView(vh: RecyclerView.ViewHolder): Boolean =
        getDelegateOrThrow(vh.itemViewType).onFailedToRecycleView(vh)

    fun onViewAttachedToWindow(vh: RecyclerView.ViewHolder) =
        getDelegateOrThrow(vh.itemViewType).onViewAttachedToWindow(vh)


    fun onViewDetachedFromWindow(vh: RecyclerView.ViewHolder) =
        getDelegateOrThrow(vh.itemViewType).onViewDetachedFromWindow(vh)
}