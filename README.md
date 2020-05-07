# KotlinMultiPlatform Items
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [ ![Download](https://api.bintray.com/packages/garage-dev/kmp/kmp-items/images/download.svg) ](https://bintray.com/garage-dev/kmp/kmp-items/_latestVersion) ![kotlin-version](https://img.shields.io/badge/kotlin-1.3.71-orange)   

This is multiplatform solution to create list based interfaces from common code.

Original concept proposed by Hannes Dorfmann in [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates).  
This version is multiplatform implementation of modified [solution](https://github.com/nullgr/app-core/tree/master/core-adapter).

## Installation
root build.gradle
```groovy
allprojects {
    repositories {
        maven { url = "https://dl.bintray.com/garage-dev/kmp" }
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.garage.kmp:items:0.0.1-alpha")
}
```

settings.gradle
```groovy
enableFeaturePreview("GRADLE_METADATA")
```

Podfile for iOS app, not sure but should work
```
pod 'MultiPlatformLibraryItems', :git => 'https://github.com/vchernyshov/kmp-items.git', :tag => 'release/0.0.1-alpha'
```

## How to use
### Common code:
To create new item need to implement ```Item``` interface.  
```Item``` interface contains base logic to work with [DiffUtils](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil) at Android platform  
and common field to reuse ```ItemDelegate``` and make relation ```Item``` -> ```ItemDelegate```.  

Typical ```Item``` implementation:  
```kotlin
data class ExampleItem1(
    val icon: String,
    val text: String,
    override val uniqueProperty: Any = text
) : Item
```

### Platform code:
Each platform need to define: ```ItemDelegate``` for ```Item```, ```DelegatesFactory``` and create ```ItemsAdapter``` 

### Android platform:
1. ```ItemDelegate```:  
```kotlin
class ExampleDelegate1 : GenericDelegate<ExampleItem1, ExampleDelegate1.ViewHolder>() {

    override fun bind(items: List<Item>, item: ExampleItem1, position: Int, holder: ViewHolder) {
        with(holder.binding) {
            Glide.with(this.root).load(item.icon).into(iconView)
            textView.text = item.text
        }
    }

    class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseViewHolder(parent, layoutId) {
        val binding = ItemExample1Binding.bind(itemView)
    }
}
```  
2. ```DelegatesFactory```:
```kotlin
object ExampleDelegatesFactory : DelegatesFactory {
    override fun create(item: Item): ItemDelegate? {
        return when (item) {
            is ExampleItem1 -> ExampleDelegate1()
            is ExampleItem2 -> ExampleDelegate2()
            is ExampleItem3 -> ExampleDelegate3()
            is ExampleItemWithPayloads -> ExampleDelegateWithPayloads()
            else -> null
        }
    }
}
```
3. ```ItemsAdapter```:
```kotlin
class SimpleExampleActivity : AppCompatActivity() {

    private lateinit var adapter: ItemsAdapter
    private lateinit var itemsView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        adapter = ItemsAdapter(ExampleDelegatesFactory)
        itemsView.adapter = adapter
    }
    
    fun onItemsReceived(items: List<Item>) {
        adapter.submitList(items)
    }
}
```
#### Handle item events:
Each ```ItemDelegate``` has ```ItemEventListener``` reference.  
To notify listener about new event use ```sendEvent(ItemEvent)``` function.  
For such cases as click events exists convenient functions ```setItemViewClickListener``` and extension  
function ```View.clicks(RecyclerView.ViewHolder, (item: T, position: Int) -> ItemEvent)```:  
```kotlin
class ExampleDelegate1 : GenericDelegate<ExampleItem1, ExampleDelegate1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent, R.layout.item_example_1).apply {
            setItemViewClickListener()
            binding.deleteView.clicks<ExampleItem1>(this) { item, _ -> DeleteItemEvent(item) }
        }
    }
}
```

Receive events:  
```kotlin
class SimpleExampleActivity : AppCompatActivity() {

    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ItemsAdapter(ExampleDelegatesFactory, object : ItemEventListener {
            override fun onItemEvent(event: ItemEvent) {
                if (event is ItemClicked) {
                    Toast.makeText(
                        this@SimpleExampleActivity,
                        "Clicked ${event.item.uniqueProperty}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (event is DeleteItemEvent) {
                    ItemsHolder.onDeleteEvent(event)
                }
            }
        })
    }
}
```

Samples available in ```app-android``` module: [simple](https://github.com/vchernyshov/kmp-items/blob/master/app-android/src/main/java/dev/garage/items/app/SimpleExampleActivity.kt), [with payloads](https://github.com/vchernyshov/kmp-items/blob/master/app-android/src/main/java/dev/garage/items/app/PayloadExampleActivity.kt)   

### iOS platform: 
iOS implementation was inspired by [moko-units](https://github.com/icerockdev/moko-units)   
1. ```ItemDelegate```:  
```swift
class Example1TableViewDelegate: GenericDelegate<ExampleItem1, Example1TableViewCell> {
    
    override func xibName() -> String {
        return "Example1TableViewCell"
    }

    override func bind(items: [Item], item: ExampleItem1, position: Int64, cell: Example1TableViewCell) {
        cell.iconView.af.setImage(withURL: URL(string: item.icon)!)
        cell.textView.text = item.text
    }
}
```  
2. ```Cell```
```swift
class Example1TableViewCell: UITableViewCell {
    
    @IBOutlet weak var iconView: UIImageView!
    @IBOutlet weak var textView: UILabel!
}
```
3. ```DelegatesFactory```:
```swift
class ExampleTableViewFactory: DelegatesFactory {
    func create(item: Item) -> ItemDelegate? {
        if item is ExampleItem1 {
            return Example1TableViewDelegate()
        }
        if item is ExampleItem2 {
            return Example2TableViewDelegate()
        }
        if item is ExampleItem3 {
            return Example3TableViewDelegate()
        }
        return nil
    }
}
```
4. iOS version supports ```default``` and ```diffable``` version of ```ItemsAdapter```:
```swift
class TableViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    private var adapter: ItemsAdapter!
          
    override func viewDidLoad() {
        super.viewDidLoad()
        // default
        adapter = ItemsAdapterKt.default(for: tableView, with: ExampleTableViewFactory())
        // diffable
        // adapter = ItemsAdapterKt.diffable(for: tableView, with: ExampleTableViewFactory())
    }

    func onItemsReceived(items: List<Item>) {
        adapter.items = items
    }
}
```
#### Handle item events:
Each ```ItemDelegate``` has ```ItemEventListener``` reference.   
```swift
class Example1TableViewCell: UITableViewCell {
        
    var deleteCallback: (() -> ())!
    
    @IBAction func onDeleteButtonClicked(_ sender: UIButton) {
        deleteCallback()
    }
}

class Example1TableViewDelegate: GenericDelegate<ExampleItem1, Example1TableViewCell> {
    
    override func bind(items: [Item], item: ExampleItem1, position: Int64, cell: Example1TableViewCell) {
        cell.deleteCallback = {
            self.sendEvent(event: DeleteItemEvent(item: item))
        }
    }
}
```

Receive events:  
```swift
class TableViewController: UIViewController, UITableViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    
    private var adapter: ItemsAdapter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        adapter.setItemEventListener(listener: { event in
            if let clickedEvent = event as? ItemClicked {
                self.showToast(message: "Clicked \(clickedEvent.item.uniqueProperty)")
            }
            if let deleteEvent = event as? DeleteItemEvent {
                self.holder.onDeleteEvent(event: deleteEvent)
            }
        })
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        adapter.didSelectItem(indexPath: indexPath)
    }
}
```

Samples available in ```app-ios``` module: [TableView](https://github.com/vchernyshov/kmp-items/blob/master/app-ios/app-ios/table/TableViewController.swift), [CollectionView](https://github.com/vchernyshov/kmp-items/blob/master/app-ios/app-ios/collection/CollectionViewController.swift)
