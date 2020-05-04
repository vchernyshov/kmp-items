//
//  ExampleCollectionViewFactory.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import MultiPlatformLibrary

class ExampleCollectionViewFactory: DelegatesFactory {
    func create(item: Item) -> ItemDelegate? {
        if item is ExampleItem1 {
            return Example1CollectionViewDelegate()
        }
        if item is ExampleItem2 {
            return Example2CollectionViewDelegate()
        }
        if item is ExampleItem3 {
            return Example3CollectionViewDelegate()
        }
        return nil
    }
}
