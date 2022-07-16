package com.klikdigital.lifecycleexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListActivity extends AppCompatActivity {
    private RecyclerView rvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        rvMain = findViewById(R.id.rvMain);
        initList();
    }

    private void initList() {
        List<String> data = Arrays.asList("WBSLINK","WBSCHAT","WBSPRO","CHATAJA.ME","DETAPOS","BPOS","GOOGLE","YOUTUBE","NETFLIX","SAMSUNG","APPLE","ANDROID","LINUX","MICROSOFT");
        final int[] delay = {1000};
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.item_list, parent, false);
                return new ItemHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                int finalPosition = position;
                ((ItemHolder) holder).text.setText(data.get(finalPosition));
                if (holder instanceof ItemHolder){
                    if (position == 0){
                        ((ItemHolder) holder).main.setOnClickListener(view -> {
                            ((ItemHolder) holder).main.transitionToEnd();
                        });
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(() -> ((ItemHolder) holder).main.performClick());
                                // this code will be executed after 2 seconds
                            }
                        }, delay[0]);
                        delay[0] = delay[0] + 100;
                    }
                }
            }

            @Override
            public int getItemCount() {
                return data.size();
            }

            class ItemHolder extends RecyclerView.ViewHolder {
                MotionLayout main;
                TextView text;
                public ItemHolder(@NonNull View itemView) {
                    super(itemView);
                    main = itemView.findViewById(R.id.main);
                    text = itemView.findViewById(R.id.text);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}