package dev.garage.app.common

import dev.garage.app.common.items.ExampleItem1
import dev.garage.app.common.items.ExampleItem2
import dev.garage.app.common.items.ExampleItem3
import dev.garage.items.Item
import kotlin.native.concurrent.ThreadLocal
import kotlin.random.Random

@ThreadLocal
class ItemsHolder {
    private val URLS = mutableListOf<String>().apply {
        repeat(30) {
            add("https://i.picsum.photos/id/${1015 + it}/300/300.jpg")
        }
    }
    private val nextImage: String
        get() = URLS[Random.nextInt(URLS.size)]

    private val original = mutableListOf<Item>().apply {
        add(ExampleItem1(nextImage, "ExampleItem1-1"))
        add(ExampleItem1(nextImage, "ExampleItem1-2"))
        add(ExampleItem1(nextImage, "ExampleItem1-3"))
        add(ExampleItem1(nextImage, "ExampleItem1-4"))
        add(ExampleItem1(nextImage, "ExampleItem1-5"))
        add(ExampleItem2(nextImage, "ExampleItem2-1", "ExampleItem"))
        add(ExampleItem2(nextImage, "ExampleItem2-2", "ExampleItem"))
        add(ExampleItem2(nextImage, "ExampleItem2-3", "ExampleItem"))
        add(ExampleItem2(nextImage, "ExampleItem2-4", "ExampleItem"))
        add(ExampleItem2(nextImage, "ExampleItem2-5", "ExampleItem"))
        add(ExampleItem3(nextImage, "ExampleItem3-1", nextImage))
        add(ExampleItem3(nextImage, "ExampleItem3-2", nextImage))
        add(ExampleItem3(nextImage, "ExampleItem3-3", nextImage))
        add(ExampleItem3(nextImage, "ExampleItem3-4", nextImage))
        add(ExampleItem3(nextImage, "ExampleItem3-5", nextImage))
    }

    var listener: ((List<Item>) -> Unit)? = null
        set(value) {
            field = value
            field?.invoke(items)
        }

    var items: List<Item> = original

    fun shuffled(): List<Item> = items.shuffled()

    fun onShuffle() {
        items = items.shuffled()
        notifyListener()
    }

    fun onDeleteEvent(event: DeleteItemEvent) {
        items = items
            .asSequence()
            .filter { it.uniqueProperty != event.item.uniqueProperty }
            .toList()
        notifyListener()
    }

    fun onRefresh() {
        items = original
        notifyListener()
    }

    private fun notifyListener() {
        listener?.invoke(items)
    }
}