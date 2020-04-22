package dev.garage.items.app.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.garage.app.common.DeleteItemEvent
import dev.garage.app.common.items.ExampleItem1
import dev.garage.items.BaseViewHolder
import dev.garage.items.GenericDelegate
import dev.garage.items.Item
import dev.garage.items.app.R
import dev.garage.items.app.databinding.ItemExample1Binding

class ExampleDelegate1 : GenericDelegate<ExampleItem1, ExampleDelegate1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent, R.layout.item_example_1).apply {
            setItemViewClickListener()
            binding.deleteView.clicks<ExampleItem1>(this) { item, _ -> DeleteItemEvent(item) }
        }
    }

    override fun bind(items: List<Item>, item: ExampleItem1, position: Int, holder: ViewHolder) {
        with(holder.binding) {
            Glide.with(this.root).load(item.icon).into(iconView)
            textView.text = item.text
        }
        println(this::class.java.canonicalName + " bind")
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseViewHolder(parent, layoutId) {
        val binding = ItemExample1Binding.bind(itemView)
    }
}