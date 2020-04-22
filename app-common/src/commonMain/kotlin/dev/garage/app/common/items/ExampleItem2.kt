package dev.garage.app.common.items

import dev.garage.items.Item

data class ExampleItem2(
    val icon: String,
    val text1: String,
    val text2: String,
    override val uniqueProperty: Any = text1
) : Item