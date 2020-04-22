//
//  CollectionViewController.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 14.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryItems

class CollectionViewController: UIViewController, ItemEventListener {
    
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var shuffleView: UIButton!
    @IBOutlet weak var refreshView: UIButton!
    
    private var adapter: ItemsAdapter!
    private var holder: ItemsHolder!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        collectionView.collectionViewLayout = LinearFlowLayout()
        holder = ItemsHolder()
        adapter = ItemsAdapterKt.diffable(for: collectionView, with: ExampleFactory())
        adapter.setItemEventListener(listener: self)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        holder.listener = { items in
            self.adapter.items = items
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        holder.listener = nil
    }
    
    func onItemEvent(event: ItemEvent) {
        if let deleteEvent = event as? DeleteItemEvent {
            holder.onDeleteEvent(event: deleteEvent)
        }
    }
    
    @IBAction func onSuffleClicked(_ sender: UIButton) {
        holder.onShuffle()
    }
    
    @IBAction func onRefreshClicked(_ sender: UIButton) {
        holder.onRefresh()
    }
}

class ExampleFactory: DelegatesFactory {
    func create(item: Item) -> ItemDelegate? {
        if item is ExampleItem1 {
            return Example1Delegate()
        }
        if item is ExampleItem2 {
            return Example2Delegate()
        }
        if item is ExampleItem3 {
            return Example3Delegate()
        }
        return nil
    }
}

class LinearFlowLayout: UICollectionViewFlowLayout {
    
    override func prepare() {
        super.prepare()
        guard let collectionView = collectionView else { return }
        
        let availableWidth = collectionView.bounds.inset(by: collectionView.layoutMargins).width
        let cellWidth = availableWidth
        let cellHeight = self.itemSize.height
        
        self.itemSize = CGSize(width: cellWidth, height: cellHeight)
    }
}
