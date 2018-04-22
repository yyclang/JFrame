package com.zinc.jframe.jframe.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public class MyHandler extends Handler {

    private WeakReference<Fragment> weakReference;

    public MyHandler(Fragment fragment) {
        weakReference = new WeakReference<Fragment>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        Fragment fragment = weakReference.get();
    }
}