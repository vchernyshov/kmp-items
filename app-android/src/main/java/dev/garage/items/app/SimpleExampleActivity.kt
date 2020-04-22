package dev.garage.items.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.garage.app.common.DeleteItemEvent
import dev.garage.app.common.ItemsHolder
import dev.garage.items.ItemClicked
import dev.garage.items.ItemEvent
import dev.garage.items.ItemEventListener
import dev.garage.items.ItemsAdapter
import dev.garage.items.app.databinding.ActivityAdapterExampleBinding

class SimpleExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdapterExampleBinding
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdapterExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Simple example"

        adapter = ItemsAdapter(ExampleDelegatesFactory, object : ItemEventListener {
            override fun onItemEvent(event: ItemEvent) {
                if (event is ItemClicked) {
                    Toast.makeText(
                        this@SimpleExampleActivity,
                        "Clicked ${event.item.uniqueProperty}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (event is DeleteItemEvent) {
                    ItemsHolder.onDeleteEvent(event)
                }
            }
        })

        with(binding) {
            itemsView.layoutManager = LinearLayoutManager(root.context)
            itemsView.adapter = adapter
            itemsView.addItemDecoration(
                DividerItemDecoration(
                    root.context,
                    DividerItemDecoration.VERTICAL
                ).apply { setDrawable(getDrawable(R.drawable.item_devider)!!) }
            )
            shuffleButton.setOnClickListener {
                ItemsHolder.onShuffle()
            }
            refreshButton.setOnClickListener {
                ItemsHolder.onRefresh()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ItemsHolder.listener = {
            adapter.submitList(it)
        }
    }

    override fun onPause() {
        super.onPause()
        ItemsHolder.listener = null
    }
}