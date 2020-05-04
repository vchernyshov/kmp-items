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

class CollectionViewController: UIViewController, UICollectionViewDelegate {
    
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var shuffleView: UIButton!
    @IBOutlet weak var refreshView: UIButton!
    
    private var adapter: ItemsAdapter!
    private var holder: ItemsHolder!
    
    var isSimple: Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if isSimple {
            title = "CollectionView Simple Sample"
        } else {
            title = "CollectionView Diffable Sample"
        }
        collectionView.collectionViewLayout = LinearFlowLayout()
        collectionView.delegate = self
        holder = ItemsHolder()
        adapter = createAdapter(simple: isSimple)
        adapter.setItemEventListener(listener: { event in
            if let clickedEvent = event as? ItemClicked {
                self.showToast(message: "Clicked \(clickedEvent.item.uniqueProperty)")
            }
            if let deleteEvent = event as? DeleteItemEvent {
                self.holder.onDeleteEvent(event: deleteEvent)
            }
        })
        holder.listener = { items in
            self.adapter.items = items
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        self.adapter.didSelectItem(indexPath: indexPath)
    }
    
    @IBAction func onSuffleClicked(_ sender: UIButton) {
        holder.onShuffle()
    }
    
    @IBAction func onRefreshClicked(_ sender: UIButton) {
        holder.onRefresh()
    }
    
    deinit {
        holder.listener = nil
    }
    
    private func createAdapter(simple: Bool)-> ItemsAdapter {
        if simple {
            return ItemsAdapterKt.default(for: collectionView, with: ExampleCollectionViewFactory())
        } else {
            return ItemsAdapterKt.diffable(for: collectionView, with: ExampleCollectionViewFactory())
        }
    }
    
    static func newInstance(_ simple: Bool) -> CollectionViewController {
        let storyboard = UIStoryboard.init(name: "CollectionViewStoryboard", bundle: nil)
        let controller = storyboard.instantiateViewController(identifier: "CollectionViewStoryboard") as CollectionViewController
        controller.isSimple = simple
        return controller
    }
}

extension UICollectionView {
    var widestCellWidth: CGFloat {
        let insets = contentInset.left + contentInset.right
        return bounds.width - insets
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
