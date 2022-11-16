package com.bdpp.common.ext

import android.content.Context
import com.bdpp.common.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener

//inline fun <D : DialogInterface> Context.alert(
//    factory: AlertBuilderFactory<D>,
//    message: CharSequence,
//    title: CharSequence? = null,
//    noinline block: (AlertBuilder<D>.() -> Unit)? = null
//) =
//    alertDialog(factory) {
//        title?.let { this.title = it }
//        this.message = message
//        block?.invoke(this)
//    }.show()


inline fun Context.alert(
    title: String = "提示",
    content: CharSequence = "",
    cancelBtnText: CharSequence = "取消",
    okBtnText: CharSequence = "确定",
    crossinline cancelBtnBlock: () -> Unit,
    crossinline okBtnBlock: () -> Unit,
    isHideCancel:Boolean=false,
    layoutId:Int = R.layout.dialog_common,
    dismissOnBackPressed :Boolean = true,
    dismissOnTouchOutside :Boolean = true,
    isDestroyOnDismiss:Boolean = false,
    hasBlurBg :Boolean = false,
    autoDismiss :Boolean = true,
    ) =
    XPopup.Builder(this)
        .isDestroyOnDismiss(isDestroyOnDismiss)
        .dismissOnBackPressed(dismissOnBackPressed)
        .dismissOnTouchOutside(dismissOnTouchOutside)
        .hasBlurBg(hasBlurBg)
        .autoDismiss(autoDismiss)
        .asConfirm(title,content,cancelBtnText,okBtnText, OnConfirmListener { okBtnBlock.invoke() }, OnCancelListener {cancelBtnBlock.invoke()},isHideCancel,layoutId)
        .show()


