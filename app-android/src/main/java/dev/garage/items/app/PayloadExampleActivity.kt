package dev.garage.items.app

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback
import dev.garage.app.common.items.ExampleItemWithPayloads
import dev.garage.items.*
import dev.garage.items.app.databinding.ActivityPayloadExampleBinding

class PayloadExampleActivity : AppCompatActivity(), ColorPickerCallback {

    private lateinit var binding: ActivityPayloadExampleBinding
    private lateinit var adapter: ItemsAdapter

    private var useDiffUtils: Boolean = true

    @ColorInt
    private var itemColor: Int = Color.RED

    private val colorPicker by lazy(LazyThreadSafetyMode.NONE) {
        ColorPicker(
            this,
            Color.red(itemColor),
            Color.green(itemColor),
            Color.blue(itemColor)
        )
    }
    private val itemsCallback: (List<Item>) -> Unit = {
        adapter.submitList(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayloadExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Payload example"

        adapter = ItemsAdapter(ExampleDelegatesFactory, object : ItemEventListener {
            override fun onItemEvent(event: ItemEvent) {
                when (event) {
                    is ItemClicked -> Toast.makeText(
                        this@PayloadExampleActivity,
                        "Clicked ${event.item}",
                        Toast.LENGTH_SHORT
                    ).show()
                    is PayloadItemEvent -> {
                        val text = "${binding.logView.text}\n${event.payload}"
                        binding.logView.text = text
                    }
                }
            }
        })

        with(binding) {
            itemsView.layoutManager = LinearLayoutManager(root.context)
            itemsView.adapter = adapter

            val item = ExampleItemWithPayloads("Initial title", "Initial subtitle", itemColor)
            itemsCallback.invoke(listOf(item))

            titleInputView.setText(item.title)
            subTitleInputView.setText(item.subTitle)
            useDiffUtilsView.isChecked = useDiffUtils
            useDiffUtilsView.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
                useDiffUtils = checked
            }
            pickColorButtonView.setOnClickListener {
                colorPicker.show()
            }
            applyChangesButtonView.setOnClickListener {
                val newTitle = titleInputView.text.toString()
                val newSubTitle = subTitleInputView.text.toString()
                val newItem = item.copy(title = newTitle, subTitle = newSubTitle, color = itemColor)
                if (useDiffUtils) itemsCallback.invoke(listOf(newItem))
                else {
                    adapter.currentList.clear()
                    adapter.currentList.add(newItem)
                    adapter.notifyDataSetChanged()
                }
            }

            logView.movementMethod = ScrollingMovementMethod()
        }
    }

    override fun onColorChosen(color: Int) {
        itemColor = color
        binding.pickColorButtonView.setBackgroundColor(itemColor)
        colorPicker.dismiss()
    }
}