package com.bdpp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bdpp.common.R
import com.bdpp.common.viewbinding.FragmentBinding
import com.bdpp.common.viewbinding.FragmentBindingDelegate
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseFragment<VB : ViewBinding> : Fragment(),
    FragmentBinding<VB> by FragmentBindingDelegate() , IUiView {
    protected var mTag: String? = null
    private var loadingPopup: LoadingPopupView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        createViewWithBinding(inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTag = javaClass.simpleName
        initView()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun initView(){
        initListener()
    }
    open fun startObserve(){}
    open fun initListener(){}
    override fun showLoading(
        loadingText: String,
        dismissOnBackPressed: Boolean,
        dismissOnTouchOutside: Boolean
    ) {
        loadingPopup.takeIf { it?.isShow ?: true } ?.dismiss()
        loadingPopup = XPopup.Builder(context)
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