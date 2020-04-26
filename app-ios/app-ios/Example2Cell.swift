//
//  Example2Cell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 16.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class Example2Cell: UICollectionViewCell {
        
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var textView1: UILabel!
    @IBOutlet weak var textView2: UILabel!
}

class Example2Delegate: GenericDelegate<ExampleItem2, Example2Cell> {
    override func xibName() -> String {
        return "Example2Cell"
    }
    
    override func bind(items: [Item], item: ExampleItem2, position: Int64, cell: Example2Cell) {
        cell.imageView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView1.text = item.text1
        cell.textView2.text = item.text2
    }
}
