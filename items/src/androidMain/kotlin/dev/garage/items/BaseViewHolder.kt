package dev.garage.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Has more suitable constructor for easier use in [ItemDelegate.onCreateViewHolder].
 */
open class BaseViewHolder : RecyclerView.ViewHolder {

    constructor(view: View) : super(view)

    constructor(parent: ViewGroup, layoutId: Int) : super(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )
}