package com.gaohui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gaohui.loader.XTabImageLoader;

public class GlideImageLoader implements XTabImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView, int size, final RequestCallback requestCallback) {
        Glide.with(context).asBitmap().load(path)
                .override(size)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (requestCallback != null)
                            requestCallback.onResourceReady(resource.getWidth(), resource.getHeight());
                        return false;
                    }
                }).into(imageView);
    }

}
