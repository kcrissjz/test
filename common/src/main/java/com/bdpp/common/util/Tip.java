package com.bdpp.common.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * 可在任意线程执行本类方法
 */
public class Tip {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast;

    public static void show(int msgResId) {
        show(msgResId, false);
    }


    public static void show(int msgResId, boolean timeLong) {
        show(AppGlobals.getApplication().getString(msgResId), timeLong);
    }

    public static void show(CharSequence msg) {
        show(msg, false);
    }

    public static void show(final CharSequence msg, final boolean timeLong) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                int duration = timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
                mToast = Toast.makeText(AppGlobals.getApplication(), msg, duration);
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    private static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }
}
