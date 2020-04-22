package dev.garage.app.common.items

import dev.garage.items.Item

data class ExampleItemWithPayloads(
    val title: String,
    val subTitle: String,
    val color: Int
) : Item {

    override val uniqueProperty: Any = this::class

    override fun getChangePayload(other: Item): Any {
        if (this::class == other::class) {
            other as ExampleItemWithPayloads
            return mutableSetOf<Payload>().apply {
                if (title != other.title) add(Payload.TITLE_CHANGED)
                if (subTitle != other.subTitle) add(Payload.SUB_TITLE_CHANGED)
                if (color != other.color) add(Payload.COLOR_CHANGED)
            }
        }
        return super.getChangePayload(other)
    }

    enum class Payload {
        TITLE_CHANGED, SUB_TITLE_CHANGED, COLOR_CHANGED
    }
}