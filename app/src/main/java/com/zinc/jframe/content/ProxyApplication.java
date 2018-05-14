package com.zinc.jframe.content;

import android.content.Intent;

import com.zinc.jframe.ProxyService;
import com.zinc.libjframe.content.OkHttpApplication;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/5/14
 * @description
 */

public class ProxyApplication extends OkHttpApplication {

    @Override
    public void onCreate() {
        startService(new Intent(getApplicationContext(), ProxyService.class));

        super.onCreate();
    }
}
