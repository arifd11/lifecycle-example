package com.klikdigital.lifecycleexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class RecreateMotionListDetails extends AppCompatActivity {
    private TextView tvPos;
    private int duration = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(13);
            findViewById(android.R.id.content).setTransitionName("EXTRA_VIEW");
            setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
            getWindow().setSharedElementEnterTransition(buildContainerTransform(true));
            getWindow().setSharedElementReturnTransition(buildContainerTransform(false));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreate_motion_list_details);
        String stringExtra = getIntent().getStringExtra("EXTRA_POSITION");
        tvPos = findViewById(R.id.tvPos);
        tvPos.setText(stringExtra);
        if (Build.VERSION.SDK_INT < 21) {
            ViewCompat.setTransitionName(findViewById(android.R.id.content), "EXTRA_VIEW");
        }
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