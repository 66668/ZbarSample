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
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sfs.zbar.adapter.MergeListAdapter;
import com.sfs.zbar.adapter.MyLinearLayout;
import com.sfs.zbar.adapter.MyListView;
import com.sfs.zbar.base.LoadingAct;
import com.sfs.zbar.bean.Delivery;
import com.sfs.zbar.bean.DispatchData;
import com.sfs.zbar.bean.UserRefuseData;
import com.sfs.zbar.camera.CameraPreview;
import com.sfs.zbar.camera.ScanCallback;
import com.sfs.zbar.common.Constant;
import com.sfs.zbar.dao.DeliveryDao;
import com.sfs.zbar.net.GetDataManager;
import com.sfs.zbar.net.IVolleyResponse;
import com.sfs.zbar.net.Urls;
import com.sfs.zbar.net.volley.VolleyError;
import com.sfs.zbar.tools.CommonTools;
import com.sfs.zbar.tools.Logg;
import com.sfs.zbar.tools.Toaster;
import com.sfs.zbar.widget.MyTitleView;
import com.sfs.zbar.widget.dialog.MyDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Scan UI.</p>
 * Created by Yan Zhenjie on 2017/5/10.
 */
public class MainActivity extends LoadingAct implements MyLinearLayout.OnScrollListener,View.OnClickListener {
    private static final String TAG = "SJY";
    private static final String AID = "2246";//快递公司id
    private static final String VID = "123";//
    private final int HANDLER_DEL_SUCCESS = 4;//是否删除

    public final int REFUSE_SUCCESS = 5;//dao操作回调
    public final int REFUSE_FAILURE = 6;//


    private RelativeLayout mScanCropView;
    private ImageView mScanLine;
    private ValueAnimator mScanAnimator;//动画 扫描

    private CameraPreview mPreviewView;//zbar预览

    private Button btn;
    private MyTitleView lMyTitleView;
    private TextView tv_speek;

    //接口相关
    private String Did;
    private String mExpcom;// 后台返回快递公司名称
    private String ExpNum;// 后台返回的快递单号
    private String ConID = "";// 货架号
    String Smobile = "";
    String Sname = "";
    String Saddress = "";

    //listview相关
    private MyListView listView;
    private MyLinearLayout mLastScrollView;
    private List<Delivery> infos;
    private int position;
    private MergeListAdapter madapter;
    private String expressnum;
    private DeliveryDao mDao;

    //统计耗时相关
    private boolean isTask =true;
    private long startTime;
    private long endTime;
    private long totleTime;

    private void initMyView() {
        //topbar
        lMyTitleView = (MyTitleView) findViewById(R.id.title);
        lMyTitleView.setRightButton(lMyTitleView.STYLE_BLUE);
        lMyTitleView.setContent("派件扫描", true, true, new MyTitleView.OnTitleCallBack() {

            @Override
            public void RightOnClick() {
                Intent intent = new Intent();
                //                intent.setClass(getApplicationContext(), BlueToothScanActivity.class);
                //                intent.putExtra("signate", "paijian");
                //                startActivity(intent);

            }

            @Override
            public void LeftOnClick() {
                finish();
            }
        });

        //
        tv_speek = (TextView) findViewById(R.id.tv_speek);
        listView=(MyListView)findViewById(R.id.mylistview);
        tv_speek.setOnClickListener(this);

        //
        mPreviewView = (CameraPreview) findViewById(R.id.capture_preview);
        mScanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mScanLine = (ImageView) findViewById(R.id.capture_scan_line);

        mPreviewView.setScanCallback(resultCallback);

        mDao = new DeliveryDao(this);//db初始化
        infos = new ArrayList<Delivery>();

        creatRefuse();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        initBeepSound();
        initMyView();
    }


    /**
     * zbar识别结果处理
     */

    private ScanCallback resultCallback = new ScanCallback() {
        @Override
        public void onScanResult(String result) {
            if (result.toString().trim().length()>0){
                if (isTask){
                    isTask = false;
                    startTime =System.currentTimeMillis();
                }
                ExpNum = result;

                mExpcom = "重复扫描";
                createUpdate(result);//调接口
                if (mPreviewView.start()) {
                    mScanAnimator.start();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        vibrate = true;
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

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_DEL_SUCCESS://删除数据 弹窗
                    madapter.removeItem(position);
                    mDao.delete(expressnum);
                    CommonTools.setLoadingVisible(MainActivity.this,
                            false);
                    refreshData();
                    Toast toast = Toast.makeText(getApplicationContext(), "删除成功!",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    ImageView imageCodeProject = new ImageView(
                            getApplicationContext());
                    imageCodeProject
                            .setImageResource(R.drawable.correct_457px_1163714_easyicon);
                    toastView.addView(imageCodeProject, 0);
                    toast.show();
                    break;
            }
        }
    };

    /**
     * Do not have permission to request for permission and start scanning.
     */
    private void startScanUnKnowPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode,  List<String> grantPermissions) {
                        startScanWithPermission();
                    }

                    @Override
                    public void onFailed(int requestCode,  List<String> deniedPermissions) {
                        AndPermission.defaultSettingDialog(MainActivity.this).show();
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

    /**
     * 初始化声音
     */
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;
    private boolean vibrate;

    protected void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };




    /**
     * 派件扫描接口
     * @param resultString
     */
    public void createUpdate(String resultString) {
        CommonTools.setLoadingVisible(MainActivity.this, true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Aid", AID);//
        jsonObject.addProperty("Vid", VID);//
        //jsonObject.addProperty("Did", Did);
        jsonObject.addProperty("Expnum", resultString);

        GetDataManager.get(Urls.CmdGet.SCHECK, jsonObject,
                new IVolleyResponse<DispatchData>() {

                    @Override
                    public void onResponse(DispatchData response) {

                        if (response != null && response.getCode() == Constant.NET_SUCCESS) {
                            //耗时统计
                            endTime=System.currentTimeMillis();
                            totleTime = endTime-startTime;
                            Logg.d(TAG,"总耗时="+totleTime);
                            isTask = true;

                            Toaster.show("扫描成功！"+totleTime+"ms");

                            //
                            playBeepSoundAndVibrate();//设置震动
                            //
                            ExpNum = response.ExpNum;// 快递单号
                            Did = response.getDid();
                            ConID = response.getConID();// 货架号
                            Saddress = response.getSaddress();// 地址
                            mExpcom = response.getExptitle();
//
                            mDao.add(ExpNum, null, Did, mExpcom, Smobile, Sname, ConID, Saddress);
//
                            refreshData();
//
                            Logg.d(TAG,"调接口成功");

                        } else {
                            //耗时统计
                            endTime=System.currentTimeMillis();
                            totleTime = endTime-startTime;
                            Logg.d(TAG,"总耗时="+totleTime);
                            isTask = true;
                            mExpcom = "重复扫描";

                            playBeepSoundAndVibrate();//设置震动
                            mDao.add(ExpNum, null, Did, mExpcom, Smobile, Sname, ConID, Saddress);
                            //
                            refreshData();

                            Toaster.show("重复扫描，后台数据存在！"+totleTime+"ms");
                            Logg.d(TAG,"重复扫描，后台数据存在");
                        }
                        CommonTools.setLoadingVisible(MainActivity.this, false);//加载动画去除
                    }

                    @Override
                    public void onErrorListener(VolleyError error) {
                        CommonTools.setLoadingVisible(MainActivity.this, false);
                        isTask = true;
                        Toaster.show("数据添加失败，请重试!");
                        Logg.d(TAG,"调接口异常");
                    }
                }, DispatchData.class, null);
    }

    /**
     * 获取数据库的全部记录,刷新显示数据
     */
    private void refreshData() {
        infos = mDao.findAll();
        Collections.reverse(infos);//反转
        if (infos.size() > 0) {
            tv_speek.setText("点击删除（" + infos.size() + "件)");
        } else {
            tv_speek.setText("完成");
        }
        if (madapter == null) {
            madapter = new MergeListAdapter(MainActivity.this, infos, this, this);
            //mAdapter = new MyAdapter();
            listView.setAdapter(madapter);
        } else {
            // 通知数据适配器更新数据,而不是new出来新的数据适配器
            madapter.setList(infos);
            madapter.notifyDataSetChanged();
        }
    }

    public void showDialog(String Content, boolean isSigleButton, int type) {
        final MyDialog lMyDialog = new MyDialog(MainActivity.this);
        lMyDialog.setContent(Content, isSigleButton, "取消", "确定", type);
        lMyDialog.setDialogCallBack(new MyDialog.onDialogCallBack() {

            @Override
            public void leftButton(int type) {
                lMyDialog.dismiss();
            }

            @Override
            public void RightButton(int type) {
                lMyDialog.dismiss();

                delExp(infos.get(position).getDid());

            }
        });
        lMyDialog.showDialog();
    }
    /**
     * 48、删除一条数据
     */
    public void delExp(String Did) {
        CommonTools.setLoadingVisible(MainActivity.this, true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Aid", AID);
        jsonObject.addProperty("Vid", VID);
        jsonObject.addProperty("Did", Did);
        GetDataManager.get(Urls.CmdGet.PAIJIANDEL, jsonObject,
                new IVolleyResponse<DispatchData>() {
                    @Override
                    public void onResponse(DispatchData response) {

                        if (response != null
                                && response.getCode() == Constant.NET_SUCCESS) {
                            Message msg = Message.obtain();
                            msg.what = HANDLER_DEL_SUCCESS;
                            msg.obj = response;
                            mHandler.sendMessage(msg);
                        } else {
                            Toaster.show(response.getMessage());
                            CommonTools.setLoadingVisible(MainActivity.this,
                                    false);
                        }

                    }

                    @Override
                    public void onErrorListener(VolleyError error) {
                        CommonTools.setLoadingVisible(MainActivity.this,
                                false);
                        Toaster.show("数据删除失败，请重试!");

                    }
                }, DispatchData.class, null);
    }


    /**
     *
     */
    private void creatRefuse() {
        CommonTools.setLoadingVisible(this, true);
        JsonObject jsonObject = new JsonObject();
        GetDataManager.get(Urls.CmdGet.REFUSE, jsonObject,
                new IVolleyResponse<UserRefuseData>() {

                    @Override
                    public void onResponse(UserRefuseData response) {
                        if (response != null
                                && response.getCode() == Constant.NET_SUCCESS) {
                            CommonTools.setLoadingVisible(MainActivity.this,
                                    false);
                            Message msg = Message.obtain();
                            msg.what = REFUSE_SUCCESS;
                            msg.obj = response;
                            mHandler.sendMessage(msg);

                        } else {
                            CommonTools.setLoadingVisible(MainActivity.this,
                                    false);
                            Toaster.show(R.string.error);
                            mHandler.sendEmptyMessage(REFUSE_FAILURE);
                        }
                    }

                    @Override
                    public void onErrorListener(VolleyError error) {
                        CommonTools.setLoadingVisible(MainActivity.this,
                                false);
                        Toaster.show(R.string.error);
                        mHandler.sendEmptyMessage(REFUSE_FAILURE);
                    }
                }, UserRefuseData.class, getClass().getName());
    }

    //========================================接口实现 回调 覆写=======================================
    @Override
    public void OnScroll(MyLinearLayout view) {
        if (mLastScrollView != null) {
            mLastScrollView.smoothScrollTo(0, 0);
        }
        mLastScrollView = view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.del://侧滑删除按钮
                position = listView.getPositionForView(v);
                expressnum = infos.get(position).getExpNum();
                showDialog("要删除\n" + expressnum + " 吗？", false, 0);
                break;

                case R.id.tv_speek://删除所有数据
                    mDao.clearAll();
                    refreshData();
                break;
        }
    }
}

