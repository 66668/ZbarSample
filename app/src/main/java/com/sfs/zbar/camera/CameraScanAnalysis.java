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
package com.sfs.zbar.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.sfs.zbar.MyApplication;
import com.yanzhenjie.zbar.Config;
import com.yanzhenjie.zbar.Image;
import com.yanzhenjie.zbar.ImageScanner;
import com.yanzhenjie.zbar.Symbol;
import com.yanzhenjie.zbar.SymbolSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * zbar识别二维码处理类，预览data数据回调类
 *
 * 包含预览返回图片
 */
public class CameraScanAnalysis implements Camera.PreviewCallback {

    //启动线程池
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ImageScanner mImageScanner;
    private Handler mHandler;
    private ScanCallback mCallback;

    private boolean allowAnalysis = true;
    private Image barcode;

    public CameraScanAnalysis() {
        mImageScanner = new ImageScanner();
        mImageScanner.setConfig(0, Config.X_DENSITY, 3);//x
        mImageScanner.setConfig(0, Config.Y_DENSITY, 3);//y

        /**
         * 主线程处理识别结果
         */
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        final byte[] data2 = (byte[]) msg.obj;
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                barcode.setData(data2); //填充预览数据

                                int width= (int) (342* MyApplication.density); //布局扫码框的高度

                                barcode.setCrop(50, 0, width, 1080);//圈出识别区域，参数见该方法说明，很重要

                                int result = mImageScanner.scanImage(barcode);

                                String resultStr = null;
                                if (result != 0) {
                                    SymbolSet symSet = mImageScanner.getResults();
                                    for (Symbol sym : symSet)
                                        resultStr = sym.getData();
                                }

                                if (!TextUtils.isEmpty(resultStr)) {
                                    Message message = mHandler.obtainMessage();
                                    message.obj = resultStr;
                                    message.what = 2;
                                    message.sendToTarget();
                                } else allowAnalysis = true;
                            }
                        });
                        break;
                    case 2:
                        if (mCallback != null) {
                            mCallback.onScanResult((String) msg.obj);
                        }
                        break;

                }

            }
        };
    }
    //=======================================================暴露方法==========================================================
    void setScanCallback(ScanCallback callback) {
        this.mCallback = callback;
    }

    void onStop() {
        this.allowAnalysis = false;
    }

    void onStart() {
        this.allowAnalysis = true;
    }

    /**
     * 说明
     * zbar对原始竖屏预览数据处理
     * 方法barcode.setCrop（）参数说明如下：（都是像素单位）
     * 坐标轴始终在手机右上方，向下是x正向，向左是y正向
     * 第一个参数：实际屏幕上截图距离预顶部大小
     * 第二个参数：实际屏幕上截图距离右边框大小
     * 宽：实际就是屏幕上截图的高
     * 高：实际就是屏幕上截图的宽
     * @param data
     * @param camera
     */
    @Override
    public void onPreviewFrame(final byte[] data, Camera camera) {
        if (allowAnalysis) {
            allowAnalysis = false;

            Camera.Size size = camera.getParameters().getPreviewSize();

            barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int width= (int) (165* MyApplication.density); //布局扫码框的高度 225dp转px
            barcode.setCrop(60, 0, width, 1080);//圈出识别区域，参数见该方法说明，很重要 px
            executorService.execute(mAnalysisTask);
        }

//        if (allowAnalysis) {//tag锁
//            allowAnalysis = false;
//            Camera.Size size = camera.getParameters().getPreviewSize();
//            barcode = new Image(size.width, size.height, "Y800");// 创建一个image 大小和preview size一致
//
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    //获取旋转数据
////                    final byte[] data1 = getRotateBytes(data,45);
////
////                    //数据转换完成 调用zbar数据
////
////                    Message message = mHandler.obtainMessage();
////                    message.what =1;
////                    message.obj = data1;
////                    message.sendToTarget();
//
//                    //
//
//                    barcode.setData(data); //填充预览数据
//
//                    int width= (int) (342* MyApplication.density); //布局扫码框的高度
//
//                    barcode.setCrop(50, 0, width, 1080);//圈出识别区域，参数见该方法说明，很重要
//
//                    int result = mImageScanner.scanImage(barcode);
//
//                    String resultStr = null;
//                    if (result != 0) {
//                        SymbolSet symSet = mImageScanner.getResults();
//                        for (Symbol sym : symSet)
//                            resultStr = sym.getData();
//                    }
//
//                    if (!TextUtils.isEmpty(resultStr)) {
//                        Message message = mHandler.obtainMessage();
//                        message.obj = resultStr;
//                        message.what = 2;
//                        message.sendToTarget();
//                    }
//                }
//            });//异步线程池识别
//
//        }
    }
    private Runnable mAnalysisTask = new Runnable() {
        @Override
        public void run() {
            int result = mImageScanner.scanImage(barcode);

            String resultStr = null;
            if (result != 0) {
                SymbolSet symSet = mImageScanner.getResults();
                for (Symbol sym : symSet)
                    resultStr = sym.getData();
            }

            if (!TextUtils.isEmpty(resultStr)) {
                Message message = mHandler.obtainMessage();
                message.obj = resultStr;
                message.what =2;
                message.sendToTarget();
            } else allowAnalysis = true;
        }
    };


    //=======================================================图片旋转处理==========================================================

//   private byte[] getRotateBytes(byte[] data,int rotate){
//       //byte数据转bitmap
//       Bitmap bitmapOrg = BitmapFactory.decodeByteArray(data,0,data.length);
//
//       //获取这个图片的宽和高
//       int width = bitmapOrg.getHeight();
//       int height = bitmapOrg.getWidth();
//
//       //定义预转换成的图片的宽度和高度
//       int newWidth = bitmapOrg.getHeight();
//       int newHeight = bitmapOrg.getWidth();
//
//       //计算缩放率，新尺寸除原始尺寸
//       float scaleWidth = ((float) newWidth) / width;
//       float scaleHeight = ((float) newHeight) / height;
//
//       // 操作图片对象vmatrix
//       Matrix matrix = new Matrix();
//
//       // 缩放图片
//       matrix.postScale(scaleWidth, scaleHeight);
//
//       //旋转图片 动作
//       matrix.postRotate(rotate);
//
//       // 创建新的图片
//       Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
//               width, height, matrix, true);
//
//       try {
//           ByteArrayOutputStream baos = new ByteArrayOutputStream();
//           resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//           byte[] newdata = baos.toByteArray();
//           baos.close();
//
//           return newdata;
//       } catch (IOException e) {
//           e.printStackTrace();
//           return null;
//       }
//
//   }

}