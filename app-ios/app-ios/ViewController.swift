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
        helloView.text = HelloKt.hello()
    }
}

