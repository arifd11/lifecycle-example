package com.klikdigital.lifecycleexample.model;

import android.graphics.drawable.Drawable;

public class People {
    public String email;
    public int image;
    public Drawable imageDrw;
    public String name;
    public boolean section = false;

    public People() {
    }

    public People(String str, boolean z) {
        this.name = str;
        this.section = z;
    }
}
