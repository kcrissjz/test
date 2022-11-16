
package com.bdpp.common.ktx.activityresult

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts

fun ActivityResultCaller.registerForStartActivityResult(callback: ActivityResultCallback<ActivityResult>) =
  registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)
