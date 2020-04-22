//
//  ItemsAdapter+Reload.swift
//
//  Created by Volodymyr Chernyshov on 18.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

extension ItemsAdapterKt {
    public static func `default`(for collectionView: UICollectionView, with factory: DelegatesFactory) -> ItemsAdapter {
        return ItemsAdapterKt.create(forCollectionView: collectionView, withFactory: factory, withReloadHandler_: { (view, _, _) in
          view.reloadData()
        })
    }

    public static func `default`(for collectionView: UICollectionView, with factory: @escaping (Item) -> ItemDelegate?) -> ItemsAdapter {
        return ItemsAdapterKt.create(forCollectionView: collectionView, withFactory: factory, withReloadHandler: { (view, _, _) in
          view.reloadData()
        })
    }

    public static func `default`(for tableView: UITableView, with factory: DelegatesFactory) -> ItemsAdapter {
        return ItemsAdapterKt.create(forTableView: tableView, withFactory: factory, withReloadHandler_: { (view, _, _) in
          view.reloadData()
        })
    }

    public static func `default`(for tableView: UITableView, with factory: @escaping (Item) -> ItemDelegate?) -> ItemsAdapter {
        return ItemsAdapterKt.create(forTableView: tableView, withFactory: factory, withReloadHandler: { (view, _, _) in
          view.reloadData()
        })
    }
}