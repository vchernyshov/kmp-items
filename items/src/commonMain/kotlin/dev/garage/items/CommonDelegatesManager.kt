package dev.garage.items

open class CommonDelegatesManager(
    val factory: DelegatesFactory
) {

    protected val delegates = mutableMapOf<Int, ItemDelegate>()

    protected fun createDelegateIfNeeded(
        item: Item,
        onCreate: ((ItemDelegate) -> Unit)? = null
    ): ItemDelegate {
        val viewType = viewTypeFromItem(item)
        var delegate = delegates[viewType]
        if (delegate == null) {
            delegate = factory.create(item)

            requireNotNull(delegate) {
                "No delegate defined for ${item::class.qualifiedName} at ${factory::class.qualifiedName}"
            }

            onCreate?.invoke(delegate)
            delegates[viewType] = delegate
        }
        return delegate
    }

    protected fun getDelegateOrThrow(viewType: Int): ItemDelegate =
        delegates[viewType] ?: throw DelegateNotFoundException()

    protected fun viewTypeFromItem(item: Item): Int = item.reusableId.hashCode()

}