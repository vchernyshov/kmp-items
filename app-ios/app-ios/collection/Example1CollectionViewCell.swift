//
//  Example1Cell.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 14.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import AlamofireImage
import MultiPlatformLibrary

class Example1CollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var textView: UILabel!
    
    var deleteCallback: (() -> ())!
    
    @IBAction func onDeleteClicked(_ sender: UIButton) {
        deleteCallback()
    }
}

class Example1CollectionViewDelegate: GenericDelegate<ExampleItem1, Example1CollectionViewCell> {
    
    override func xibName() -> String {
        return "Example1CollectionViewCell"
    }

    override func bind(items: [Item], item: ExampleItem1, position: Int64, cell: Example1CollectionViewCell) {
        cell.imageView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView.text = item.text
        cell.deleteCallback = {
            self.sendEvent(event: DeleteItemEvent(item: item))
        }
    }
}
