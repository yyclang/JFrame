package com.zinc.jframe;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zinc.libpermission.annotation.Permission;
import com.zinc.libpermission.annotation.PermissionCanceled;
import com.zinc.libpermission.annotation.PermissionDenied;
import com.zinc.libpermission.bean.CancelInfo;
import com.zinc.libpermission.bean.DenyInfo;
import com.zinc.libpermission.utils.JPermissionHelper;
import com.zinc.libpermission.utils.JPermissionUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_req).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req();
            }
        });

    }

    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    private void req() {
        Toast.makeText(this, "请求一个权限成功（写权限）", Toast.LENGTH_SHORT).show();
    }

    @PermissionCanceled()
    private void cancel(CancelInfo cancelInfo) {
        Log.i(JPermissionHelper.TAG, "writeCancel: " + cancelInfo.getRequestCode());
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied()
    private void deny(DenyInfo denyInfo) {
        Log.i(JPermissionHelper.TAG, "writeDeny: code:" + denyInfo.getRequestInfo() + " ; deny:" + denyInfo.getDeniedPermissions());
        Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show();

        //前往开启权限的界面
        JPermissionUtil.goToMenu(this);
    }

}
