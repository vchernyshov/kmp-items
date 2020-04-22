package dev.garage.items.app

import dev.garage.app.common.items.ExampleItem1
import dev.garage.app.common.items.ExampleItem2
import dev.garage.app.common.items.ExampleItem3
import dev.garage.app.common.items.ExampleItemWithPayloads
import dev.garage.items.DelegatesFactory
import dev.garage.items.Item
import dev.garage.items.ItemDelegate
import dev.garage.items.app.delegates.ExampleDelegate1
import dev.garage.items.app.delegates.ExampleDelegate2
import dev.garage.items.app.delegates.ExampleDelegate3
import dev.garage.items.app.delegates.ExampleDelegateWithPayloads

object ExampleDelegatesFactory : DelegatesFactory {
    @Suppress("UNCHECKED_CAST")
    override fun create(item: Item): ItemDelegate? {
        return when (item) {
            is ExampleItem1 -> ExampleDelegate1()
            is ExampleItem2 -> ExampleDelegate2()
            is ExampleItem3 -> ExampleDelegate3()
            is ExampleItemWithPayloads -> ExampleDelegateWithPayloads()
            else -> null
        }
    }
}