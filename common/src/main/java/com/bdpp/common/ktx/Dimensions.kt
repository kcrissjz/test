
@file:Suppress("unused")

package com.bdpp.common.ktx

import android.content.res.Resources
import android.util.TypedValue
import java.math.BigDecimal

inline val Int.dp: Float get() = toFloat().dp

inline val Long.dp: Float get() = toFloat().dp

inline val Double.dp: Float get() = toFloat().dp

inline val Float.dp: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline val Int.sp: Float get() = toFloat().sp

inline val Long.sp: Float get() = toFloat().sp

inline val Double.sp: Float get() = toFloat().sp

inline val Float.sp: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

fun Int.pxToDp(): Int = toFloat().pxToDp()

fun Long.pxToDp(): Int = toFloat().pxToDp()

fun Double.pxToDp(): Int = toFloat().pxToDp()

fun Float.pxToDp(): Int =
  (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Int.pxToSp(): Int = toFloat().pxToSp()

fun Long.pxToSp(): Int = toFloat().pxToSp()

fun Double.pxToSp(): Int = toFloat().pxToSp()

fun Float.pxToSp(): Int =
  (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

fun Int.dpToPx():Int =
  (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Double.getFormatSize():String{
  val kiloByte = this/1024
  if (kiloByte < 1){
    return "${this}Byte"
  }
  val megaByte = kiloByte / 1024
  if (megaByte < 1){
    val result = BigDecimal(kiloByte.toString())
    return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"KB"
  }
  val gigaByte = megaByte / 1024
  if (gigaByte < 1) {
    val result2 = BigDecimal(megaByte.toString())
    return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
  }
  val teraBytes = gigaByte / 1024
  if (teraBytes < 1) {
    val result3 =  BigDecimal(gigaByte.toString())
    return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
  }
  val result4 = BigDecimal(teraBytes)
  return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
}