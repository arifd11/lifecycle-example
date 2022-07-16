package com.klikdigital.lifecycleexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initMotionList();
    }

    private void initMotionList() {
        Button motionList = findViewById(R.id.motionList),
                reMotionList = findViewById(R.id.reMotionList);
        motionList.setOnClickListener(
                view -> {
                    startActivity(new Intent(this, MotionListActivity.class));
                }
        );
        reMotionList.setOnClickListener(
                view -> {
                    startActivity(new Intent(this, RecreateMotionList.class));
                }
        );
    }
}