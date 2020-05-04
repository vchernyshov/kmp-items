//
//  ExampleTableViewFactory.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import MultiPlatformLibrary

class ExampleTableViewFactory: DelegatesFactory {
    func create(item: Item) -> ItemDelegate? {
        if item is ExampleItem1 {
            return Example1TableViewDelegate()
        }
        if item is ExampleItem2 {
            return Example2TableViewDelegate()
        }
        if item is ExampleItem3 {
            return Example3TableViewDelegate()
        }
        return nil
    }
}
