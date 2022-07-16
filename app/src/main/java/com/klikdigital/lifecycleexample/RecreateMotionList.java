package com.klikdigital.lifecycleexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecreateMotionList extends AppCompatActivity {
    private RecyclerView rvRecreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreate_motion_list);
        rvRecreate = findViewById(R.id.rvRecreate);
        rvRecreate.setLayoutManager(new LinearLayoutManager(this));
        rvRecreate.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(RecreateMotionList.this).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new HolderItem(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof HolderItem){
                    ((HolderItem) holder).text1.setText(position+"");
                    ((HolderItem) holder).text1.setClickable(true);
                    holder.itemView.setOnClickListener(view -> {
                        Intent intent = new Intent(RecreateMotionList.this, RecreateMotionListDetails.class);
                        intent.putExtra("EXTRA_POSITION", position+"");
                        intent.putExtra("EXTRA_DURATION", 1200);
                        if (Build.VERSION.SDK_INT >= 21) {
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(RecreateMotionList.this, view, "EXTRA_VIEW").toBundle());
                        } else {
                            ActivityCompat.startActivity(RecreateMotionList.this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(RecreateMotionList.this, view, "EXTRA_VIEW").toBundle());
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return 15;
            }

            class HolderItem extends RecyclerView.ViewHolder{
                TextView text1;
                public HolderItem(@NonNull View itemView) {
                    super(itemView);
                    text1 = itemView.findViewById(android.R.id.text1);
                }
            }
        });
    }
}