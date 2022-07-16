package com.klikdigital.lifecycleexample.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.klikdigital.lifecycleexample.R;

public class Tools {
    public static void setSystemBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static String getEmailFromName(String str) {
        return (str == null || str.equals("")) ? str : str.replaceAll(" ", ".").toLowerCase().concat("@mail.com");
    }

    public static void displayImageRound(final Context context, final ImageView imageView, int i) {
        try {
            Glide.with(context)
                    .asBitmap()
                    .load(Integer.valueOf(i))
                    .centerCrop().into(new BitmapImageViewTarget(imageView) {
                /* access modifiers changed from: protected */
                public void setResource(Bitmap bitmap) {
                    RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                    create.setCircular(true);
                    imageView.setImageDrawable(create);
                }
            });
        } catch (Exception unused) {
        }
    }

    public static void displayImageOriginal(Context context, ImageView imageView, int i) {
        try {
            Glide.with(context).load(Integer.valueOf(i)).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        } catch (Exception unused) {
        }
    }

    public static void displayImageOriginal(Context context, ImageView imageView, String str) {
        try {
            Glide.with(context).load(str).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        } catch (Exception unused) {
        }
    }


}
