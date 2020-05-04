//
//  TableViewController.swift
//  app-ios
//
//  Created by Volodymyr Chernyshov on 27.04.2020.
//  Copyright © 2020 Garage Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class TableViewController: UIViewController, UITableViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    
    private var adapter: ItemsAdapter!
    private var holder: ItemsHolder!
       
    var isSimple: Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if isSimple {
            title = "TableView Simple Sample"
        } else {
            title = "TableView Diffable Sample"
        }
        tableView.delegate = self
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
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        adapter.didSelectItem(indexPath: indexPath)
    }
    
    @IBAction func onShuffleClicked(_ sender: UIButton) {
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
            return ItemsAdapterKt.default(for: tableView, with: ExampleTableViewFactory())
        } else {
            return ItemsAdapterKt.diffable(for: tableView, with: ExampleTableViewFactory())
        }
    }
    
    static func newInstance(_ simple: Bool) -> TableViewController {
        let storyboard = UIStoryboard.init(name: "TableViewStoryboard", bundle: nil)
        let controller = storyboard.instantiateViewController(identifier: "TableViewStoryboard") as TableViewController
        controller.isSimple = simple
        return controller
    }
    
}
