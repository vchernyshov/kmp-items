package dev.garage.items.app.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.garage.app.common.items.ExampleItemWithPayloads
import dev.garage.items.BaseViewHolder
import dev.garage.items.GenericDelegate
import dev.garage.items.Item
import dev.garage.items.app.PayloadItemEvent
import dev.garage.items.app.R
import dev.garage.items.app.databinding.ItemExampleWithPayloadsBinding

class ExampleDelegateWithPayloads : GenericDelegate<ExampleItemWithPayloads, ExampleDelegateWithPayloads.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent, R.layout.item_example_with_payloads).apply {
            setItemViewClickListener()
        }
    }

    override fun bind(items: List<Item>, item: ExampleItemWithPayloads, position: Int, holder: ViewHolder) {
        with(holder.binding) {
            titleView.text = item.title
            subTitleView.text = item.subTitle
            colorView.setBackgroundColor(item.color)
        }
        sendEvent(PayloadItemEvent("onBindViewHolder"))
    }

    override fun bind(items: List<Item>, item: ExampleItemWithPayloads, position: Int, holder: ViewHolder, payload: Any) {
        when (payload) {
            ExampleItemWithPayloads.Payload.TITLE_CHANGED -> {
                holder.binding.titleView.text = item.title
            }
            ExampleItemWithPayloads.Payload.SUB_TITLE_CHANGED -> {
                holder.binding.subTitleView.text = item.subTitle
            }
            ExampleItemWithPayloads.Payload.COLOR_CHANGED -> {
                holder.binding.colorView.setBackgroundColor(item.color)
            }
        }
        sendEvent(PayloadItemEvent(payload))
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int): BaseViewHolder(parent, layoutId) {
        val binding = ItemExampleWithPayloadsBinding.bind(itemView)
    }
}