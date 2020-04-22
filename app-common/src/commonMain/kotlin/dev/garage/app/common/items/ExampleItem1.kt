package dev.garage.app.common.items

import dev.garage.items.Item

data class ExampleItem1(
    val icon: String,
    val text: String,
    override val uniqueProperty: Any = text
) : Item