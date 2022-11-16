
@file:Suppress("unused")

package com.bdpp.common.ktx

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.bdpp.common.mmkv.MMKVOwner
import com.tencent.mmkv.MMKV

class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) = init(context)

  override fun dependencies() = emptyList<Class<Initializer<*>>>()

  companion object {

    fun init(context: Context) {
      if (!isApplicationInitialized) {
        application = context as Application
        application.doOnActivityLifecycle(
          onActivityCreated = { activity, _ ->
            activityCache.add(activity)
          },
          onActivityDestroyed = { activity ->
            activityCache.remove(activity)
          }
        )
      }
      if (MMKVOwner.default == null) {
        MMKV.initialize(context,context.filesDir.absolutePath + "/mmkv_k")
        MMKVOwner.default = MMKV.defaultMMKV()
      }
    }
  }
}
