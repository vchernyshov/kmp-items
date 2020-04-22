package dev.garage.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface DelegatesManager {

    fun setDelegates(items: List<Item>)

    fun getItemViewType(items: List<Item>, position: Int): Int

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindViewHolder(items: List<Item>, position: Int, vh: RecyclerView.ViewHolder)

    fun onBindViewHolder(items: List<Item>, position: Int, vh: RecyclerView.ViewHolder, payloads: List<Any>)

    fun onViewRecycled(vh: RecyclerView.ViewHolder)

    fun onFailedToRecycleView(vh: RecyclerView.ViewHolder): Boolean

    fun onViewAttachedToWindow(vh: RecyclerView.ViewHolder)

    fun onViewDetachedFromWindow(vh: RecyclerView.ViewHolder)
}