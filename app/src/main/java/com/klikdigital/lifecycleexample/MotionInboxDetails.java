package com.klikdigital.lifecycleexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.klikdigital.lifecycleexample.utils.Tools;

public class MotionInboxDetails extends AppCompatActivity {

    private int duration = 1200;
    private ImageView mHeaderImageView;
    private TextView mHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.duration = getIntent().getIntExtra("EXTRA_DURATION", 400);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(13);
            findViewById(android.R.id.content).setTransitionName("EXTRA_VIEW");
            setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
            getWindow().setSharedElementEnterTransition(buildContainerTransform(true));
            getWindow().setSharedElementReturnTransition(buildContainerTransform(false));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_inbox_details);
        int intExtra = getIntent().getIntExtra("EXTRA_IMAGE", 0);
        String stringExtra = getIntent().getStringExtra("EXTRA_NAME");
        String stringExtra2 = getIntent().getStringExtra("EXTRA_BRIEF");
        this.mHeaderImageView = findViewById(R.id.image);
        TextView textView = findViewById(R.id.name);
        this.mHeaderTitle = textView;
        textView.setText(stringExtra);
        ((TextView) findViewById(R.id.brief)).setText(stringExtra2);
        Tools.displayImageOriginal(this, this.mHeaderImageView, intExtra);
        if (Build.VERSION.SDK_INT < 21) {
            ViewCompat.setTransitionName(findViewById(android.R.id.content), "EXTRA_VIEW");
        }
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private MaterialContainerTransform buildContainerTransform(boolean z) {
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();
        materialContainerTransform.setTransitionDirection(z ? MaterialContainerTransform.TRANSITION_DIRECTION_ENTER : MaterialContainerTransform.TRANSITION_DIRECTION_RETURN);
        materialContainerTransform.setAllContainerColors(MaterialColors.getColor(findViewById(android.R.id.content), R.attr.colorSurface));
        materialContainerTransform.addTarget(android.R.id.content);
        materialContainerTransform.setDuration(this.duration);
        return materialContainerTransform;
    }

}