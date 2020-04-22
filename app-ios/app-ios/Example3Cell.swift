//
//  Example3Cell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 16.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class Example3Cell: UICollectionViewCell {
    
    @IBOutlet weak var imageView1: UIImageView!
    @IBOutlet weak var textView: UILabel!
    @IBOutlet weak var imageView2: UIImageView!
}

class Example3Delegate: CellDelegate<ExampleItem3, Example3Cell> {
    
    override func nibName() -> String {
        return "Example3Cell"
    }
    
    override func onBindViewCell(item: ExampleItem3, cell: Example3Cell) {
        cell.imageView1.af.setImage(withURL: URL(string: item.icon1)!)
        cell.textView.text = item.text
        cell.imageView2.af.setImage(withURL: URL(string: item.icon2)!)
    }
}
