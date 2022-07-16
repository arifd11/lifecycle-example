package com.klikdigital.lifecycleexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public BatteryInfo info;
    private MotionLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = findViewById(R.id.main);
        main.setOnClickListener(view -> {
            main.transitionToEnd();
//            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        });
//        main.setOrientation(LinearLayout.VERTICAL);
        info = new BatteryInfo(this, getLifecycle());
        info.setEnabled();
        /*info.getBatteryInfo().observe(this, stringStringHashMap -> {
            for ( Map.Entry<String, String> entry : stringStringHashMap.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                TextView tv = new TextView(this);
                tv.setText(key+" : "+val);
                tv.setGravity(Gravity.CENTER);
                main.addView(tv);
                Log.d(TAG, "onCreate: "+key+" "+val);
            }
        });*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        info.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        info.destroy();
    }
}