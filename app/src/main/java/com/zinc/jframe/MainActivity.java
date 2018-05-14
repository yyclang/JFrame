package com.zinc.jframe;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zinc.jframe.jframe.activity.FragmentMainActivity;
import com.zinc.jframe.jframe.activity.ToolbarDemoActivity;
import com.zinc.libpermission.annotation.Permission;
import com.zinc.libpermission.annotation.PermissionCanceled;
import com.zinc.libpermission.annotation.PermissionDenied;
import com.zinc.libpermission.bean.CancelInfo;
import com.zinc.libpermission.bean.DenyInfo;
import com.zinc.libpermission.utils.JPermissionHelper;
import com.zinc.libpermission.utils.JPermissionUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.tv_req).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                req();
//            }
//        });


        Intent intent = new Intent(this, ProxyService.class);
        startService(intent);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .proxy(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 12580))
                )
                .build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        findViewById(R.id.tv_fragment_demo).setOnClickListener(this);
        findViewById(R.id.tv_toolbar).setOnClickListener(this);
        findViewById(R.id.tv_test_proxy).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_fragment_demo:
                intent = new Intent(this, FragmentMainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_toolbar:
                intent = new Intent(this, ToolbarDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_test_proxy:
                Request request = new Request.Builder().url("https://www.baidu.com").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("PROXY", "success");
                    }
                });
                break;
        }
    }


//    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
//    private void req() {
//        Toast.makeText(this, "请求一个权限成功（写权限）", Toast.LENGTH_SHORT).show();
//    }
//
//    @PermissionCanceled()
//    private void cancel(CancelInfo cancelInfo) {
//        Log.i(JPermissionHelper.TAG, "writeCancel: " + cancelInfo.getRequestCode());
//        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
//    }
//
//    @PermissionDenied()
//    private void deny(DenyInfo denyInfo) {
//        Log.i(JPermissionHelper.TAG, "writeDeny: code:" + denyInfo.getRequestInfo() + " ; deny:" + denyInfo.getDeniedPermissions());
//        Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show();
//
//        //前往开启权限的界面
//        JPermissionUtil.goToMenu(this);
//    }

}
