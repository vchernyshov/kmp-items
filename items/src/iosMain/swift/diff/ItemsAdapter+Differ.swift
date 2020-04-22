//
//  ItemsAdapter+Differ.swift
//
//  Created by Volodymyr Chernyshov on 18.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import Differ
import UIKit
import MultiPlatformLibrary

extension ItemsAdapterKt {
    public static func diffable(for collectionView: UICollectionView, with factory: DelegatesFactory) -> ItemsAdapter {
        return ItemsAdapterKt.create(forCollectionView: collectionView, withFactory: factory, withReloadHandler_: { (view, old, new) in
            view.animateItemChanges(
                    oldData: old ?? [],
                    newData: new ?? [],
                    isEqual: { $0.areItemsTheSame(other: $1) && $0.areContentsTheSame(other: $1) },
                    updateData: {}
            )
        })
    }

    public static func diffable(for collectionView: UICollectionView, with factory: @escaping (Item) -> ItemDelegate?) -> ItemsAdapter {
        return ItemsAdapterKt.create(forCollectionView: collectionView, withFactory: factory, withReloadHandler: { (view, old, new) in
            view.animateItemChanges(
                    oldData: old ?? [],
                    newData: new ?? [],
                    isEqual: { $0.areItemsTheSame(other: $1) && $0.areContentsTheSame(other: $1) },
                    updateData: {}
            )
        })
    }

    public static func diffable(for tableView: UITableView, with factory: DelegatesFactory) -> ItemsAdapter {
        return ItemsAdapterKt.create(forTableView: tableView, withFactory: factory, withReloadHandler_: { (view, old, new) in
            view.animateRowChanges(
                    oldData: old ?? [],
                    newData: new ?? [],
                    isEqual: { $0.areItemsTheSame(other: $1) && $0.areContentsTheSame(other: $1) }
            )
        })
    }

    public static func diffable(for tableView: UITableView, with factory: @escaping (Item) -> ItemDelegate?) -> ItemsAdapter {
        return ItemsAdapterKt.create(forTableView: tableView, withFactory: factory, withReloadHandler: { (view, old, new) in
            view.animateRowChanges(
                    oldData: old ?? [],
                    newData: new ?? [],
                    isEqual: { $0.areItemsTheSame(other: $1) && $0.areContentsTheSame(other: $1) }
            )
        })
    }
}
