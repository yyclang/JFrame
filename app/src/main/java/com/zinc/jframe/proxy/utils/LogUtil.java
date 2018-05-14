package com.zinc.jframe.proxy.utils;

import android.util.Log;

/**
 * Created by zinc on 2018/5/12.
 */
public class LogUtil {

    public static void i(String tag, String msg) {
//        System.out.println("【" + tag + "】 " + msg);
        Log.i(tag, msg);
    }

}
