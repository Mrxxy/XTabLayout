package com.gaohui.loader;

import android.content.Context;
import android.widget.ImageView;

public interface XTabImageLoader {

    void displayImage(Context context, String path, ImageView imageView, int size, RequestCallback requestCallback);

    interface RequestCallback {
        void onResourceReady(int imageWidth, int imageHeight);
    }
}
