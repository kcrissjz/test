package com.bdpp.common.widgets.guidel.listener;

import android.view.View;

import com.bdpp.common.widgets.guidel.core.Controller;
import com.bdpp.common.widgets.guidel.model.GuidePage;


/**
 * <p>
 * 用于引导层布局初始化
 */

public interface OnLayoutInflatedListener {

    /**
     * @param view       {@link GuidePage#setLayoutRes(int, int...)}方法传入的layoutRes填充后的view
     * @param controller {@link Controller}
     */
    void onLayoutInflated(View view, Controller controller);
}
