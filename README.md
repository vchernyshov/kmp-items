# KotlinMultiPlatform Items
This is multiplatform solution to create list based interfaces from common code.

Original concept proposed by Hannes Dorfmann in [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates).
This version is multiplatform implementation of modified [solution](https://github.com/nullgr/app-core/tree/master/core-adapter).

## Installation

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
Each platform need to define: ```ItemDelegate``` for each ```Item```, ```DelegatesFactory``` and crete ```ItemsAdapter``` 

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