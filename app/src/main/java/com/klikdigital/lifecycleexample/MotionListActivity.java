package com.klikdigital.lifecycleexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.klikdigital.lifecycleexample.adapter.AdapterListSectioned;
import com.klikdigital.lifecycleexample.data.DataGenerator;
import com.klikdigital.lifecycleexample.model.People;
import com.klikdigital.lifecycleexample.utils.Tools;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MotionListActivity extends AppCompatActivity {
    private AdapterListSectioned mAdapter;
    private View parent_view;
    private RecyclerView recyclerView;
    private boolean slow = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_list);
        this.parent_view = findViewById(android.R.id.content);
        initToolbar();
        initComponent();
        initTimer();
    }

    private void initTimer() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int hrBackward = 23 - LocalDateTime.now().getHour();
                    int minBackward = 59 - LocalDateTime.now().getMinute();
                    int secBackward = 59 - LocalDateTime.now().getSecond();
                    String minute = ((String.valueOf(minBackward).length() > 1) ? String.valueOf(minBackward) : "0"+minBackward);
                    String second = ((String.valueOf(secBackward).length() > 1) ? String.valueOf(secBackward) : "0"+secBackward);
                    System.out.println("Hello World "+hrBackward+":"+minute+":"+second);
                }
            }
        }, 0, 1000);
    }

    private void initComponent() {
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setHasFixedSize(true);
        List<People> peopleData = DataGenerator.getPeopleData(this);
        peopleData.addAll(DataGenerator.getPeopleData(this));
        peopleData.addAll(DataGenerator.getPeopleData(this));
        List<String> stringsMonth = DataGenerator.getStringsMonth(this);
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < peopleData.size() / 6; i3++) {
            peopleData.add(i, new People(stringsMonth.get(i2), true));
            i += 5;
            i2++;
        }
        AdapterListSectioned adapterListSectioned = new AdapterListSectioned(this, peopleData);
        this.mAdapter = adapterListSectioned;
        this.recyclerView.setAdapter(adapterListSectioned);
        this.mAdapter.setOnItemClickListener((view, people, i1) -> this.onItemClicked(view, people));
    }

    public void onItemClicked(View view, People people) {
        Intent intent = new Intent(this, MotionInboxDetails.class);
        intent.putExtra("EXTRA_IMAGE", people.image);
        intent.putExtra("EXTRA_NAME", people.name);
        intent.putExtra("EXTRA_BRIEF", people.email);
        intent.putExtra("EXTRA_DURATION", 1200);
        if (Build.VERSION.SDK_INT >= 21) {
            Toast.makeText(this, this.slow ? "Slow" : "Fast", Toast.LENGTH_SHORT).show();
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "EXTRA_VIEW").toBundle());
        } else {
            ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "EXTRA_VIEW").toBundle());
        }
        this.slow = !this.slow;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

}