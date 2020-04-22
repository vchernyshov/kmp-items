package dev.garage.items

interface Item {

    /**
     * Some unique data that represent this item. It can be entity id from server.
     * It have to be unique in set of data passed to adapter.
     */
    val uniqueProperty: Any

    /**
     * String representation of full class name.
     * Used in adapters and managers to create [Item] to [ItemDelegate] relation or create viewType.
     */
    val reusableId: String
        get() = this::class.qualifiedName ?: ""

    /**
     * Method makes check by two parameters: class of item and unique property returned by [uniqueProperty].
     * In most cases you should not override this method and use default implementation.
     */
    fun areItemsTheSame(other: Item): Boolean =
        this::class == other::class && this.uniqueProperty == other.uniqueProperty


    /**
     * By default method checks equality using [Any.equals].
     * It is a good idea to use Kotlin data classes as items.
     * If you using Java you should specify equals method or override this.
     */
    fun areContentsTheSame(other: Item): Boolean = this == other


    /**
     * If item supports changes of few properties at same time return set of payloads
     * to prevent many same changes and handle all changes at UI.
     */
    fun getChangePayload(other: Item): Any = Unit
}