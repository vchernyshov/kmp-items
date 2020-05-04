//
//  Example2TableViewCell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class Example2TableViewCell: UITableViewCell {
        
    @IBOutlet weak var iconView: UIImageView!
    @IBOutlet weak var textView1: UILabel!
    @IBOutlet weak var textView2: UILabel!
}

class Example2TableViewDelegate: GenericDelegate<ExampleItem2, Example2TableViewCell> {
    
    override func xibName() -> String {
        return "Example2TableViewCell"
    }
    
    override func bind(items: [Item], item: ExampleItem2, position: Int64, cell: Example2TableViewCell) {
        cell.iconView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView1.text = item.text1
        cell.textView2.text = item.text2
    }
}
