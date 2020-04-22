package dev.garage.app.common.items

import dev.garage.items.Item

data class ExampleItem3(
    val icon1: String,
    val text: String,
    val icon2: String,
    override val uniqueProperty: Any = text
) : Item