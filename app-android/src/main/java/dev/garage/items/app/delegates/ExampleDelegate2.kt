package dev.garage.items.app.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.garage.app.common.DeleteItemEvent
import dev.garage.app.common.items.ExampleItem2
import dev.garage.items.*
import dev.garage.items.app.R
import dev.garage.items.app.databinding.ItemExample2Binding

class ExampleDelegate2 : GenericDelegate<ExampleItem2, ExampleDelegate2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent, R.layout.item_example_2).apply {
            setItemViewClickListener()
            binding.deleteView.clicks<ExampleItem2>(this) { item, _ -> DeleteItemEvent(item) }
        }
    }

    override fun bind(items: List<Item>, item: ExampleItem2, position: Int, holder: ViewHolder) {
        with(holder.binding) {
            Glide.with(holder.itemView).load(item.icon).into(iconView)
            text1View.text = item.text1
            text2View.text = item.text2
        }
        println(this::class.java.canonicalName + " bind")
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseViewHolder(parent, layoutId) {
        val binding = ItemExample2Binding.bind(itemView)
    }
}