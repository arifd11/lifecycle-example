package com.klikdigital.lifecycleexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.*
import java.util.*

class InfoBattery (private val _context: Context,
                   private val _lifecycle: Lifecycle) : LifecycleObserver{
    val map = HashMap<String, String>()
    private var _enabled = false
    private val _batteryInfoMap = MutableLiveData<HashMap<String, String>>()

    val batteryInfo: LiveData<HashMap<String, String>>
        get() = _batteryInfoMap

    init {
        _lifecycle.addObserver(this)
    }

    private val broadcastBatteryInfoListener = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.let {

                val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val temperature = it.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
                val voltage = it.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)
                val technology = it.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, -1)
                val plugged = it.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                val health = it.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)

                map["level"] = level.toString()
                map["temperature"] = temperature.toString()
                map["voltage"] = voltage.toString()
                map["technology"] = technology.toString()
                map["plugged"] = plugged.toString()
                map["health"] = health.toString()

                _batteryInfoMap.value = map
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (_enabled) {
            _context.registerReceiver(
                broadcastBatteryInfoListener,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        _context.unregisterReceiver(broadcastBatteryInfoListener)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        _lifecycle.removeObserver(this)
    }

    fun enable() {
        _enabled = true

        if (_lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            start()
        }
    }

}