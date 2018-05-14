package com.zinc.jframe;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zinc.jframe.proxy.HttpServer;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/5/12
 * @description
 */

public class ProxyService extends Service {

    @Override
    public void onCreate() {
        Thread thread = new Thread(new HttpServer());
        thread.start();
        super.onCreate();
//        HttpServer.startServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpServer.stopServer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
