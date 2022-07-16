package com.klikdigital.lifecycleexample.utils;

import android.view.View;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.klikdigital.lifecycleexample.R;

public class MaterialTransition {
    private View view;
    private int duration;

    public MaterialTransition(View view, int duration) {
        this.view = view;
        this.duration = duration;
    }

}
