//
//  Example3TableViewCell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class Example3TableViewCell: UITableViewCell {
    
    @IBOutlet weak var imageView1: UIImageView!
    @IBOutlet weak var textView: UILabel!
    @IBOutlet weak var imageView2: UIImageView!
}

class Example3TableViewDelegate: GenericDelegate<ExampleItem3, Example3TableViewCell> {
    
    override func xibName() -> String {
        return "Example3TableViewCell"
    }
    
    override func bind(items: [Item], item: ExampleItem3, position: Int64, cell: Example3TableViewCell) {
        cell.imageView1.af.setImage(withURL: URL(string: item.icon1)!)
        cell.textView.text = item.text
        cell.imageView2.af.setImage(withURL: URL(string: item.icon2)!)
    }
}
