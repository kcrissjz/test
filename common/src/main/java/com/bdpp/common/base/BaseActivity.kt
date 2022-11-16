package com.bdpp.common.base

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bdpp.common.R
import com.bdpp.common.ktx.doOnClick
import com.bdpp.common.viewbinding.ActivityBinding
import com.bdpp.common.viewbinding.ActivityBindingDelegate
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate(), IUiView {
    protected var mTag: String? = null
    protected var mContext: Context? = null
    private var loadingPopup: LoadingPopupView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()
        mTag = this.javaClass.simpleName
        mContext = this
        immersionBar {
            statusBarDarkFont(false)
            titleBar(R.id.toolbar_layout)
        }
        startObserve()
        setToolBarTitle()
        initView()
        initData()

    }

    open fun initView() {
        initListener()
    }

    override fun onResume() {
        super.onResume()
    }

    open fun initData() {

    }

    open fun initListener() {

    }

    protected fun setToolBarTitle(str: String = "") {
        findViewById<ImageView>(R.id.iv_back)?.doOnClick {
            finish()
        }
        if (str.isNotEmpty()) findViewById<TextView>(R.id.tv_title)?.text = str
    }

    open fun startObserve() {

    }

    override fun showLoading(
        loadingText: String,
        dismissOnBackPressed: Boolean,
        dismissOnTouchOutside: Boolean
    ) {
        loadingPopup.takeIf { it?.isShow ?: true } ?.dismiss()
        loadingPopup = XPopup.Builder(this)
            .dismissOnBackPressed(dismissOnBackPressed)
            .dismissOnTouchOutside(dismissOnTouchOutside)
            .isLightNavigationBar(true) //.asLoading(null, R.layout.custom_loading_popup)
            .asLoading(loadingText, R.layout.dialog_loading, null)
            .show() as LoadingPopupView
    }

    override fun dismissLoading() {
        loadingPopup.takeIf { it?.isShow ?: true }?.dismiss()
    }

}