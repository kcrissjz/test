
@file:Suppress("unused")

package com.bdpp.common.ktx

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(vararg pairs: Pair<String, *>) = apply {
  arguments = bundleOf(*pairs)
}

fun <T> Fragment.arguments(key: String) = lazy<T?> {
  arguments[key]
}

fun <T> Fragment.arguments(key: String, default: T) = lazy {
  arguments[key] ?: default
}

fun <T> Fragment.safeArguments(name: String) = lazy<T> {
  checkNotNull(arguments[name]) { "No intent value for key \"$name\"" }
}

fun Fragment.pressBackTwiceToExitApp(toastText: String, delayMillis: Long = 2000,action:()->Unit) =
  requireActivity().pressBackTwiceToExitApp(toastText, delayMillis, viewLifecycleOwner,action)

fun Fragment.pressBackTwiceToExitApp(@StringRes toastText: Int, delayMillis: Long = 2000,action:()->Unit) =
  requireActivity().pressBackTwiceToExitApp(toastText, delayMillis, viewLifecycleOwner,action)

fun Fragment.pressBackTwiceToExitApp(delayMillis: Long = 2000, onFirstBackPressed: () -> Unit,  onSecondBackPressed: () -> Unit) =
  requireActivity().pressBackTwiceToExitApp(delayMillis, viewLifecycleOwner, onFirstBackPressed,onSecondBackPressed)

fun Fragment.pressBackToNotExitApp() =
  requireActivity().pressBackToNotExitApp(viewLifecycleOwner)

fun Fragment.doOnBackPressed(onBackPressed: () -> Unit) =
  requireActivity().doOnBackPressed(viewLifecycleOwner, onBackPressed)
