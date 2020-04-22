package dev.garage.items.app.delegates

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.garage.app.common.items.ExampleItem3
import dev.garage.items.*
import dev.garage.items.app.R

class ExampleDelegate3 : GenericDelegate<ExampleItem3, ExampleDelegate3.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent, R.layout.item_example_3).apply {
            setItemViewClickListener()
        }
    }

    override fun bind(items: List<Item>, item: ExampleItem3, position: Int, holder: ViewHolder) {
        with(holder) {
            Glide.with(this.itemView).load(item.icon1).into(icon1View)
            textView.text = item.text
            Glide.with(this.itemView).load(item.icon2).into(icon2View)
        }
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseViewHolder(parent, layoutId) {
        val icon1View: ImageView = itemView.findViewById(R.id.icon1View)
        val icon2View: ImageView = itemView.findViewById(R.id.icon2View)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}