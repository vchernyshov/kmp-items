package dev.garage.items

import platform.Foundation.NSIndexPath
import platform.UIKit.UICollectionView
import platform.UIKit.UITableView
import platform.UIKit.item

interface ItemsAdapter {

    var items: List<Item>

    fun setItemEventListener(listener: ItemEventListener?)

    fun setItemEventListener(listener: ((ItemEvent) -> Unit)?)

    fun didSelectItem(indexPath: NSIndexPath)
}

fun create(
    forCollectionView: UICollectionView,
    withFactory: DelegatesFactory,
    withReloadHandler: UICollectionViewReloadHandler = { collectionView, _, _ -> collectionView.reloadData() }
): ItemsAdapter =
    object : ItemsAdapter {
        val innerAdapter =
            CollectionItemsAdapter(forCollectionView, withFactory, withReloadHandler)
        override var items: List<Item>
            get() = innerAdapter.items
            set(value) {
                innerAdapter.items = value
            }

        override fun setItemEventListener(listener: ItemEventListener?) {
            innerAdapter.listener = listener
        }

        override fun setItemEventListener(listener: ((ItemEvent) -> Unit)?) {
            innerAdapter.listener = object: ItemEventListener {
                override fun onItemEvent(event: ItemEvent) {
                    listener?.invoke(event)
                }
            }
        }

        override fun didSelectItem(indexPath: NSIndexPath) {
            innerAdapter.listener?.onItemEvent(ItemClicked(items[indexPath.item.toInt()]))
        }
    }

fun create(
    forCollectionView: UICollectionView,
    withFactory: (Item) -> ItemDelegate?,
    withReloadHandler: UICollectionViewReloadHandler = { collectionView, _, _ -> collectionView.reloadData() }
): ItemsAdapter =
    create(
        forCollectionView,
        object : DelegatesFactory {
            override fun create(item: Item): ItemDelegate? {
                return withFactory.invoke(item)
            }
        },
        withReloadHandler
    )

fun create(
    forTableView: UITableView,
    withFactory: DelegatesFactory,
    withReloadHandler: UITableViewReloadHandler = { tableView, _, _ -> tableView.reloadData() }
): ItemsAdapter =
    object : ItemsAdapter {
        val innerAdapter = TableItemsAdapter(forTableView, withFactory, withReloadHandler)
        override var items: List<Item>
            get() = innerAdapter.items
            set(value) {
                innerAdapter.items = value
            }

        override fun setItemEventListener(listener: ItemEventListener?) {
            innerAdapter.listener = listener
        }

        override fun setItemEventListener(listener: ((ItemEvent) -> Unit)?) {
            innerAdapter.listener = object: ItemEventListener {
                override fun onItemEvent(event: ItemEvent) {
                    listener?.invoke(event)
                }
            }
        }

        override fun didSelectItem(indexPath: NSIndexPath) {
            innerAdapter.listener?.onItemEvent(ItemClicked(items[indexPath.item.toInt()]))
        }
    }

fun create(
    forTableView: UITableView,
    withFactory: (Item) -> ItemDelegate?,
    withReloadHandler: UITableViewReloadHandler = { tableView, _, _ -> tableView.reloadData() }
): ItemsAdapter =
    create(
        forTableView,
        object : DelegatesFactory {
            override fun create(item: Item): ItemDelegate? {
                return withFactory.invoke(item)
            }
        },
        withReloadHandler
    )