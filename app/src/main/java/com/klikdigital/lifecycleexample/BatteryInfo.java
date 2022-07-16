package com.klikdigital.lifecycleexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.HashMap;

public class BatteryInfo implements LifecycleObserver {
    private Context context;
    private Lifecycle lifecycle;

    public BatteryInfo(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
    }

    HashMap<String, String> map = new HashMap<>();

    private boolean enabled = false;

    MutableLiveData<HashMap<String, String>> batteryInfoData = new MutableLiveData<>();

    LiveData<HashMap<String, String>> batteryInfo = batteryInfoData;

    public LiveData<HashMap<String, String>> getBatteryInfo() {
        return batteryInfo;
    }

    public void init(){
        lifecycle.addObserver(this);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String level, temp, volt, technology, plugged, health;
            level = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
            temp = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1));
            volt = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1));
            technology = String.valueOf(intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY));
            plugged = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1));
            health = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1));
            map.put("level", level);
            map.put("temp", temp);
            map.put("volt", volt);
            map.put("technology", technology);
            map.put("plugged", plugged);
            map.put("health", health);

            batteryInfoData.setValue(map);
        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(){
        if (enabled){
            context.registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(){
        try {
            context.unregisterReceiver(broadcastReceiver);
        } catch (RuntimeException e){
            e.fillInStackTrace();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        lifecycle.removeObserver(this);
    }

    public void setEnabled(){
        init();
        enabled = true;
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            start();
        }
    }
}
