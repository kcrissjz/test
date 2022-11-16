
@file:Suppress("unused")

package com.bdpp.common.viewbindingktx

import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView

inline fun <reified VB : ViewBinding> NavigationView.updateHeaderView(index: Int = 0, block: VB.() -> Unit) =
  getHeaderView(index)?.getBinding<VB>()?.run(block)