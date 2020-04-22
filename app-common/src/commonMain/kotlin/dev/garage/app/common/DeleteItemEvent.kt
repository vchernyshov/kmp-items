package dev.garage.app.common

import dev.garage.items.Item
import dev.garage.items.ItemEvent

data class DeleteItemEvent(val item: Item) : ItemEvent