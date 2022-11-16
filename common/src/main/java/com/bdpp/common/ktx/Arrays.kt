
@file:Suppress("unused")

package com.bdpp.common.ktx

inline fun <T> Array<T>.percentage(predicate: (T) -> Boolean) =
  filter(predicate).size.toFloat() / size
