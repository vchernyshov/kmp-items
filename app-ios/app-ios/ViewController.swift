//
//  ViewController.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 14.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class ViewController: UIViewController {

    @IBOutlet weak var helloView: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "KMP Items Sample"
        helloView.text = HelloKt.hello()
    }
    
    @IBAction func onCollectionSimpleSampleClicked(_ sender: UIButton) {
        navigationController?.pushViewController(CollectionViewController.newInstance(true), animated: true)
    }
    
    @IBAction func onCollectionDiffableSampleClicked(_ sender: UIButton) {
        navigationController?.pushViewController(CollectionViewController.newInstance(false), animated: true)
    }
    
    @IBAction func onTableSimpleSampleClicked(_ sender: UIButton) {
        navigationController?.pushViewController(TableViewController.newInstance(true), animated: true)
    }
    
    @IBAction func onTableDiffableSampleClicked(_ sender: UIButton) {
        navigationController?.pushViewController(TableViewController.newInstance(false), animated: true)
    }
}

