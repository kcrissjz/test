package com.bdpp.common.base

import androidx.lifecycle.LifecycleOwner

interface IUiView : LifecycleOwner {

    fun showLoading(loadingText: String = "",
                    dismissOnBackPressed: Boolean = false,
                    dismissOnTouchOutside: Boolean = false)

    fun dismissLoading()
}