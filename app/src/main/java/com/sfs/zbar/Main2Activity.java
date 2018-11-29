/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sfs.zbar;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sfs.zbar.base.LoadingAct;
import com.sfs.zbar.camera.CameraPreview;
import com.sfs.zbar.camera.ScanCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * <p>Scan UI.</p>
 * Created by Yan Zhenjie on 2017/5/10.
 */
public class Main2Activity extends LoadingAct  {

    private RelativeLayout mScanCropView;
    private ImageView mScanLine;
    private ValueAnimator mScanAnimator;//动画 扫描

    private CameraPreview mPreviewView;//zbar预览

    private void initMyView() {

        //
        mPreviewView = (CameraPreview) findViewById(R.id.capture_preview);
        mScanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mScanLine = (ImageView) findViewById(R.id.capture_scan_line);

        mPreviewView.setScanCallback(resultCallback);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan2);
        initMyView();
    }


    /**
     * zbar识别结果处理
     */
    private long time = System.currentTimeMillis();
    private ScanCallback resultCallback = new ScanCallback() {
        @Override
        public void onScanResult(String result) {
            Log.e("SJY", "耗时=" + (System.currentTimeMillis() - time) +"ms----result="+ result);
            time = System.currentTimeMillis();
            if (mPreviewView.start()) {
                mScanAnimator.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mScanAnimator != null) {
            startScanUnKnowPermission();
        }
    }

    @Override
    public void onPause() {
        // Must be called here, otherwise the camera should not be released properly.
        stopScan();
        super.onPause();
    }


    /**
     * Do not have permission to request for permission and start scanning.
     */
    private void startScanUnKnowPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, List<String> grantPermissions) {
                        startScanWithPermission();
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> deniedPermissions) {
                        AndPermission.defaultSettingDialog(Main2Activity.this).show();
                    }
                })
                .start();
    }

    /**
     * There is a camera when the direct scan.
     */
    private void startScanWithPermission() {
        if (mPreviewView.start()) {
            mScanAnimator.start();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.camera_failure)
                    .setMessage(R.string.camera_hint)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    /**
     * Stop scan.
     */
    private void stopScan() {
        if (mScanAnimator != null) {
            mScanAnimator.cancel();
        }
        mPreviewView.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mScanAnimator == null) {
            int height = mScanCropView.getMeasuredHeight() - 25;
            mScanAnimator = ObjectAnimator.ofFloat(mScanLine, "translationY", 0F, height).setDuration(3000);
            mScanAnimator.setInterpolator(new LinearInterpolator());
            mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);

            startScanUnKnowPermission();
        }
    }
}

