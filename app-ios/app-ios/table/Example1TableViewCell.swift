//
//  Example1TableViewCell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import AlamofireImage
import MultiPlatformLibrary

class Example1TableViewCell: UITableViewCell {
    
    @IBOutlet weak var iconView: UIImageView!
    @IBOutlet weak var textView: UILabel!
    
    var deleteCallback: (() -> ())!
    
    @IBAction func onDeleteButtonClicked(_ sender: UIButton) {
        deleteCallback()
    }
}

class Example1TableViewDelegate: GenericDelegate<ExampleItem1, Example1TableViewCell> {
    
    override func xibName() -> String {
        return "Example1TableViewCell"
    }

    override func bind(items: [Item], item: ExampleItem1, position: Int64, cell: Example1TableViewCell) {
        cell.iconView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView.text = item.text
        cell.deleteCallback = {
            self.sendEvent(event: DeleteItemEvent(item: item))
        }
    }
}
