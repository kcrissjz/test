@file:Suppress("unused")

package com.bdpp.common.ktx.activityresult

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract

fun ActivityResultCaller.registerForLaunchAppSettingsResult(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchAppSettingsContract(), callback)

class LaunchAppSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      .setData(Uri.fromParts("package", context.packageName, null))

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

fun interface AppSettingsScope {
  fun launchAppSettings()
}
