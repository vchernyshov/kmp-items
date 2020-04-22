package dev.garage.items

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class ItemsAdapter(
    factory: DelegatesFactory,
    private var externalListener: ItemEventListener? = null
) : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    private val eventListenerWrapper: ItemEventListener =
        object : ItemEventListener {
            override fun onItemEvent(event: ItemEvent) {
                externalListener?.onItemEvent(event)
            }
        }

    private val manager = AndroidDelegatesManager(eventListenerWrapper, factory)

    override fun getItemViewType(position: Int): Int =
        manager.getItemViewTypeAndCreateDelegate(currentList, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        manager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        manager.onBindViewHolder(currentList, position, holder)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) manager.onBindViewHolder(currentList, position, holder)
        else manager.onBindViewHolder(currentList, position, holder, payloads)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) =
        manager.onViewAttachedToWindow(holder)

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) =
        manager.onViewDetachedFromWindow(holder)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) =
        manager.onViewRecycled(holder)

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean =
        manager.onFailedToRecycleView(holder)

    fun setItemEventListener(listener: ItemEventListener) {
        externalListener = listener
    }
}