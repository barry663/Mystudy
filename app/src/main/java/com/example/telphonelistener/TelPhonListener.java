package com.example.telphonelistener;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ 功能
 * Ctreate by barry on 2020/9/3.
 */
public class TelPhonListener extends Service {
    /**
     * 获取电话管理器
     */
    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //注册来电监听器,event是只监听接听状态事件
        manager.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class MyPhoneStateListener extends PhoneStateListener{
         private MediaRecorder recorder;
         private boolean isCall;//电话的状态

        /**
         * 回调函数，当电话状态改变时会自动调用
         * @param state 电话状态 3种状态  闲置， 摘机，响铃
         * @param phoneNumber 来电号码
         */
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE://闲置
                    Log.d("PHoneState", "当前电话处于闲置状态");
                    if (isCall){//状态由摘机转换到闲置
                        recorder.stop();
                        recorder.release();
                        Log.d("PHoneListener", "录制完成");
                    }
                    isCall =false;

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://摘机
                    Log.d("PHoneState", "当前电话处于摘机状态");
                    //初始化录音设备
                    recorder = new MediaRecorder();
                    //进行一系列初始化设置，1.设置指定音频输入源---麦克风
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置输出格式为3pp
                   recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                   //指定音频编码模式
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //输出地址-以时间为文件名
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                    //每次执行的时间都不相同，所有格式化的名称也不相同
                    String name = format.format(new Date());
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                            File.separator+name+".3pp";
                    //指定输出位置
                    recorder.setOutputFile(path);
                    Log.d("PHoneListener", "path :"+path);
                    try {
                        recorder.prepare();
                        recorder.start();
                        isCall = true;
                        Log.d("PHoneListener", "录音设备设置完成,已启动");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃
                    Log.d("PHoneState", "当前电话处响铃状态");
                    break;
            }
        }
    }
}
