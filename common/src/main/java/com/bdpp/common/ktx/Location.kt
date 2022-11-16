
@file:Suppress("unused")

package com.bdpp.common.ktx

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData

inline val isLocationEnabled: Boolean
  get() = application.getSystemService<LocationManager>()?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

class LocationEnabledLiveDate : LiveData<Boolean>() {

  override fun onActive() {
    value = isLocationEnabled
    application.registerReceiver(locationReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
  }

  override fun onInactive() {
    application.unregisterReceiver(locationReceiver)
  }

  override fun setValue(value: Boolean?) {
    if (this.value != value) {
      super.setValue(value)
    }
  }

  private val locationReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      value = isLocationEnabled
    }
  }
}
