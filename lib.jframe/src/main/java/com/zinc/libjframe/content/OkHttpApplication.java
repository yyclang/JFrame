package com.zinc.libjframe.content;

import android.app.Application;
import android.content.Intent;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/5/14
 * @description
 */

public class OkHttpApplication extends Application {

    private static OkHttpClient okHttpClient;

    private String proxyIP = "127.0.0.1";
    private int proxyPort = 12580;

    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                . proxy(
                         new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort))
                )
                .build();

    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
