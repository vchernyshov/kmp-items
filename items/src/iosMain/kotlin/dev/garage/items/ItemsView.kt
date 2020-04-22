package dev.garage.items

import platform.Foundation.NSIndexPath
import platform.UIKit.UINib

interface ItemsView<V, C> {

    fun register(nib: UINib, id: String)

    fun getReusableCell(id: String, indexPath: NSIndexPath): C
}