
@file:Suppress("unused")

package com.bdpp.common.ktx

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresPermission
import com.bdpp.common.ktx.activityresult.*

@Deprecated(
  "registerForStartActivityResult(callback) instead.",
  ReplaceWith("registerForStartActivityResult(callback)", "com.bdpp.common.ktx.activityresult.registerForStartActivityResult")
)
fun ActivityResultCaller.startActivityLauncher(callback: ActivityResultCallback<ActivityResult>) = registerForStartActivityResult(callback)

@Deprecated(
  "registerForStartIntentSenderResult(callback) instead.",
  ReplaceWith("registerForStartIntentSenderResult(callback)", "com.bdpp.common.ktx.activityresult.registerForStartIntentSenderResult")
)
fun ActivityResultCaller.startIntentSenderLauncher(callback: ActivityResultCallback<ActivityResult>) = registerForStartIntentSenderResult(callback)

@Deprecated(
  "registerForRequestPermissionResult(onGranted, onDenied, onShowRequestRationale) instead.",
  ReplaceWith(
    "registerForRequestPermissionResult(onGranted, onDenied, onShowRequestRationale)",
    "com.bdpp.common.ktx.activityresult.registerForRequestPermissionResult"
  )
)
fun ActivityResultCaller.requestPermissionLauncher(
  onGranted: () -> Unit,
  onDenied: AppSettingsScope.() -> Unit,
  onShowRequestRationale: PermissionScope.() -> Unit
): ActivityResultLauncher<String> =
  registerForRequestPermissionResult(onGranted, onDenied, onShowRequestRationale)

@Deprecated(
  "registerForRequestPermissionResult(callback) instead.",
  ReplaceWith("registerForRequestPermissionResult(callback)", "com.bdpp.common.ktx.activityresult.registerForRequestPermissionResult")
)
fun ActivityResultCaller.requestPermissionLauncher(callback: ActivityResultCallback<Boolean>) = registerForRequestPermissionResult(callback)

@Deprecated(
  "registerForRequestMultiplePermissionsResult(onAllGranted, onDenied, onShowRequestRationale) instead.",
  ReplaceWith(
    "registerForRequestMultiplePermissionsResult(onAllGranted, onDenied, onShowRequestRationale)",
    "com.bdpp.common.ktx.activityresult.registerForRequestMultiplePermissionsResult"
  )
)
fun ActivityResultCaller.requestMultiplePermissionsLauncher(
  onAllGranted: () -> Unit,
  onDenied: AppSettingsScope.(List<String>) -> Unit,
  onShowRequestRationale: PermissionsScope.(List<String>) -> Unit
) = registerForRequestMultiplePermissionsResult(onAllGranted, onDenied, onShowRequestRationale)

@Deprecated(
  "registerForRequestMultiplePermissionsResult(callback) instead.",
  ReplaceWith(
    "registerForRequestMultiplePermissionsResult(callback)",
    "com.bdpp.common.ktx.activityresult.registerForRequestMultiplePermissionsResult"
  )
)
fun ActivityResultCaller.requestMultiplePermissionsLauncher(callback: ActivityResultCallback<Map<String, Boolean>>) =
  registerForRequestMultiplePermissionsResult(callback)


