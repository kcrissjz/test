
@file:Suppress("unused")

package com.bdpp.common.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

fun FragmentActivity.FragmentStateAdapter(vararg fragments: Fragment, isLazyLoading: Boolean = false): FragmentStateAdapter =
  FragmentStateAdapter(fragments.size, isLazyLoading) { fragments[it] }

fun Fragment.FragmentStateAdapter(vararg fragments: Fragment, isLazyLoading: Boolean = false): FragmentStateAdapter =
  FragmentStateAdapter(fragments.size, isLazyLoading) { fragments[it] }

fun FragmentActivity.FragmentStateAdapter(itemCount: Int, isLazyLoading: Boolean = false, block: (Int) -> Fragment): FragmentStateAdapter =
  FragmentStateAdapter(supportFragmentManager, lifecycle, itemCount, isLazyLoading, block)

fun Fragment.FragmentStateAdapter(itemCount: Int, isLazyLoading: Boolean = false, block: (Int) -> Fragment): FragmentStateAdapter =
  FragmentStateAdapter(childFragmentManager, lifecycle, itemCount, isLazyLoading, block)

fun FragmentStateAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  itemCount: Int,
  isLazyLoading: Boolean = false,
  block: (Int) -> Fragment
) =
  object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = block(position)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
      super.onAttachedToRecyclerView(recyclerView)
      if (isLazyLoading) recyclerView.setItemViewCacheSize(itemCount)
    }
  }

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> ViewPager2.findFragment(fragmentManager: FragmentManager, position: Int) =
  fragmentManager.findFragmentByTag("f$position") as T?
