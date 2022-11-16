
@file:Suppress("unused")

package com.bdpp.common.ktx

import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.*
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bdpp.common.R
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private var View.lastClickTime: Long? by viewTags(R.id.tag_last_click_time)
var debouncingClickIntervals = 500

@Deprecated("Use doOnDebouncingClick instead", ReplaceWith("doOnDebouncingClick(clickIntervals, isSharingIntervals, block)"))
fun List<View>.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingClick(clickIntervals, isSharingIntervals, block)

@Deprecated("Use doOnDebouncingClick instead", ReplaceWith("doOnDebouncingClick(clickIntervals, isSharingIntervals, block)"))
fun View.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingClick(clickIntervals, isSharingIntervals, block)

fun List<View>.doOnDebouncingClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnDebouncingClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnDebouncingClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  setOnClickListener {
    val view = if (isSharingIntervals) context.asActivity()?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    if (currentTime - (view.lastClickTime ?: 0L) > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnClick(crossinline block: () -> Unit) = forEach { it.doOnClick(block) }

inline fun View.doOnClick(crossinline block: () -> Unit) = setOnClickListener { block() }

@Deprecated("Use doOnDebouncingLongClick instead", ReplaceWith("doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)"))
fun List<View>.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)

@Deprecated("Use doOnDebouncingLongClick instead", ReplaceWith("doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)"))
fun View.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)

fun List<View>.doOnDebouncingLongClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnDebouncingLongClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnLongClick {
    val view = if (isSharingIntervals) context.asActivity()?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    if (currentTime - (view.lastClickTime ?: 0L) > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnLongClick(crossinline block: () -> Unit) = forEach { it.doOnLongClick(block) }

inline fun View.doOnLongClick(crossinline block: () -> Unit) =
  setOnLongClickListener {
    block()
    true
  }

fun View.expandClickArea(expandSize: Float) = expandClickArea(expandSize.toInt())

fun View.expandClickArea(expandSize: Int) =
  expandClickArea(expandSize, expandSize, expandSize, expandSize)

fun View.expandClickArea(top: Float, left: Float, right: Float, bottom: Float) =
  expandClickArea(top.toInt(), left.toInt(), right.toInt(), bottom.toInt())

fun View.expandClickArea(top: Int, left: Int, right: Int, bottom: Int) {
  val parent = parent as? ViewGroup ?: return
  parent.post {
    val rect = Rect()
    getHitRect(rect)
    rect.top -= top
    rect.left -= left
    rect.right += right
    rect.bottom += bottom
    val touchDelegate = parent.touchDelegate
    if (touchDelegate == null || touchDelegate !is MultiTouchDelegate) {
      parent.touchDelegate = MultiTouchDelegate(rect, this)
    } else {
      touchDelegate.put(rect, this)
    }
  }
}

var View.roundCorners: Float
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
      override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(0, 0, view.width, view.height, value)
      }
    }
  }

fun View?.isTouchedAt(x: Float, y: Float): Boolean =
  isTouchedAt(x.toInt(), y.toInt())

fun View?.isTouchedAt(x: Int, y: Int): Boolean =
  this?.locationOnScreen?.contains(x, y) == true

fun View.findTouchedChild(x: Float, y: Float): View? =
  findTouchedChild(x.toInt(), y.toInt())

fun View.findTouchedChild(x: Int, y: Int): View? =
  touchables.find { it.isTouchedAt(x, y) }

/**
 * Computes the coordinates of this view on the screen.
 */
inline val View.locationOnScreen: Rect
  get() = IntArray(2).let {
    getLocationOnScreen(it)
    Rect(it[0], it[1], it[0] + width, it[1] + height)
  }

@Deprecated(
  "Replace with new api",
  replaceWith = ReplaceWith("withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)")
)
inline fun View.withStyledAttrs(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) =
  context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)

inline fun View.withStyledAttributes(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) =
  context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)

val View.rootWindowInsetsCompat: WindowInsetsCompat? by viewTags(R.id.tag_root_window_insets) {
  ViewCompat.getRootWindowInsets(this)
}

val View.windowInsetsControllerCompat: WindowInsetsControllerCompat? by viewTags(R.id.tag_window_insets_controller) {
  ViewCompat.getWindowInsetsController(this)
}

fun View.doOnApplyWindowInsets(action: (View, WindowInsetsCompat) -> WindowInsetsCompat) =
  ViewCompat.setOnApplyWindowInsetsListener(this, action)

fun <T> viewTags(key: Int) = object : ReadWriteProperty<View, T?> {
  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: View, property: KProperty<*>) =
    thisRef.getTag(key) as? T

  override fun setValue(thisRef: View, property: KProperty<*>, value: T?) =
    thisRef.setTag(key, value)
}

@Suppress("UNCHECKED_CAST")
fun <T> viewTags(key: Int, block: View.() -> T) = ReadOnlyProperty<View, T> { thisRef, _ ->
  if (thisRef.getTag(key) == null) {
    thisRef.setTag(key, block(thisRef))
  }
  thisRef.getTag(key) as T
}
/**
 * Set view visible
 */
fun View.visible() {
  visibility = View.VISIBLE
}

/**
 * Set view invisible
 */
fun View.invisible() {
  visibility = View.INVISIBLE
}

/**
 * Set view gone
 */
fun View.gone() {
  visibility = View.GONE
}

/**
 * Reverse the view's visibility
 */
fun View.reverseVisibility(needGone: Boolean = true) {
  if (isVisible) {
    if (needGone) gone() else invisible()
  } else visible()
}

fun View.changeVisible(visible: Boolean, needGone: Boolean = true) {
  when {
    visible -> visible()
    needGone -> gone()
    else -> invisible()
  }
}

var View.isVisible: Boolean
  get() = visibility == View.VISIBLE
  set(value) = if (value) visible() else gone()

var View.isInvisible: Boolean
  get() = visibility == View.INVISIBLE
  set(value) = if (value) invisible() else visible()

var View.isGone: Boolean
  get() = visibility == View.GONE
  set(value) = if (value) gone() else visible()

/**
 * Set padding
 * @param size top, bottom, left, right padding are same
 */
fun View.setPadding(@Px size: Int) {
  setPadding(size, size, size, size)
}

/**
 * Causes the Runnable which contains action() to be added to the message queue, to be run
 * after the specified amount of time elapses.
 * The runnable will be run on the user interface thread
 *
 * @param action Will be invoked in the Runnable
 * @param delayInMillis The delay (in milliseconds) until the action() will be invoked
 */
inline fun View.postDelayed(delayInMillis: Long, crossinline action: () -> Unit): Runnable {
  val runnable = Runnable { action() }
  postDelayed(runnable, delayInMillis)
  return runnable
}

@Deprecated("use View.drawToBitmap()")
fun View.toBitmap(scale: Float = 1f, config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap? {
  if (this is ImageView) {
    if (drawable is BitmapDrawable) return (drawable as BitmapDrawable).bitmap
  }
  this.clearFocus()
  val bitmap = createBitmapSafely((width * scale).toInt(), (height * scale).toInt(), config, 1)
  if (bitmap != null) {
    Canvas().run {
      setBitmap(bitmap)
      save()
      drawColor(Color.WHITE)
      scale(scale, scale)
      this@toBitmap.draw(this)
      restore()
      setBitmap(null)
    }
  }
  return bitmap
}
/**
 * View 转 bitmap
 */
fun View.view2Bitmap(): Bitmap {
  var ret = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
  var canvas = Canvas(ret)
  var bgDrawable = this.background
  bgDrawable?.draw(canvas) ?: canvas.drawColor(Color.WHITE)
  this.draw(canvas)
  return ret
}

fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config, retryCount: Int): Bitmap? {
  try {
    return Bitmap.createBitmap(width, height, config)
  } catch (e: OutOfMemoryError) {
    e.printStackTrace()
    if (retryCount > 0) {
      System.gc()
      return createBitmapSafely(width, height, config, retryCount - 1)
    }
    return null
  }

}
//-----扩展属性-----

var View.bottomMargin: Int
  get():Int {
    return (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
  }
  set(value) {
    (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
  }


var View.topMargin: Int
  get():Int {
    return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
  }
  set(value) {
    (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
  }


var View.rightMargin: Int
  get():Int {
    return (layoutParams as ViewGroup.MarginLayoutParams).rightMargin
  }
  set(value) {
    (layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
  }

var View.leftMargin: Int
  get():Int {
    return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
  }
  set(value) {
    (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
  }
/**
 * Register a callback to be invoked when the global layout state or the visibility of views
 * within the view tree changes
 *
 * @param callback The callback() to be invoked
 */
inline fun View.onGlobalLayout(crossinline callback: () -> Unit) = with(viewTreeObserver) {
  addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onGlobalLayout() {
      removeOnGlobalLayoutListener(this)
      callback()
    }
  })
}

/**
 * Register a callback to be invoked after the view is measured
 *
 * @param callback The callback() to be invoked
 */
inline fun View.afterMeasured(crossinline callback: View.() -> Unit) {
  viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onGlobalLayout() {
      if (measuredWidth > 0 && measuredHeight > 0) {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        callback()
      }
    }
  })
}

fun EditText.onTextChanged(action: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit){
  addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      action.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
    }
  })
}
fun EditText.afterTextChanged(action: (s: Editable) -> Unit){
  addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
      action.invoke(s)
    }
  })
}
class MultiTouchDelegate(bound: Rect, delegateView: View) : TouchDelegate(bound, delegateView) {
  private val map = mutableMapOf<View, Pair<Rect, TouchDelegate>>()
  private var targetDelegate: TouchDelegate? = null

  init {
    put(bound, delegateView)
  }

  fun put(bound: Rect, delegateView: View) {
    map[delegateView] = bound to TouchDelegate(bound, delegateView)
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    val x = event.x.toInt()
    val y = event.y.toInt()
    when (event.actionMasked) {
      MotionEvent.ACTION_DOWN -> {
        targetDelegate = map.entries.find { it.value.first.contains(x, y) }?.value?.second
      }
      MotionEvent.ACTION_CANCEL -> {
        targetDelegate = null
      }
    }
    return targetDelegate?.onTouchEvent(event) ?: false
  }
}
