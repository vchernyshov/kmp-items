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

// Like ViewHolder
class Example1Cell: UICollectionViewCell {
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var textView: UILabel!
    
    var deleteCallback: (() -> ())!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    @IBAction func onDeleteClicked(_ sender: UIButton) {
        deleteCallback()
    }
}

class Example1Delegate: CellDelegate<ExampleItem1, Example1Cell> {
    
    override func nibName() -> String {
        return "Example1Cell"
    }
        
    override func onBindViewCell(item: ExampleItem1, cell: Example1Cell) {
        cell.imageView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView.text = item.text
        cell.deleteCallback = {
            self.sendEvent(event: DeleteItemEvent(item: item))
        }
    }
}
