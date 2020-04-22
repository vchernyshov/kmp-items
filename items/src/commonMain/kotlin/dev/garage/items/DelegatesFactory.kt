package dev.garage.items

/**
 * Factory interface that creates [ItemDelegate]s by class of [Item].
 */
interface DelegatesFactory {

    fun create(item: Item): ItemDelegate?
}