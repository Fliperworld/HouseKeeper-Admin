package com.smart.cloud.fire.mvp.Alarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrsst.housekeeper.admin.R;
import com.smart.cloud.fire.base.ui.MvpActivity;
import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.Contact;
import com.smart.cloud.fire.ui.ApMonitorActivity;
import com.smart.cloud.fire.utils.MusicManger;
import com.smart.cloud.fire.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/27.
 */
public class AlarmActivity extends MvpActivity<AlarmPresenter> implements AlarmView {
    Context mContext;
    @Bind(R.id.alarm_fk_img)
    ImageView alarm_fk_img;
    @Bind(R.id.alarm_msg_tv)
    TextView alarm_msg_tv;
    @Bind(R.id.alarm_msg_time_tv)
    TextView alarm_msg_time_tv;
    @Bind(R.id.alarm_back)
    ImageView alarm_back;
    @Bind(R.id.watch_video_image)
    ImageView watch_video_image;
    private int TIME_OUT = 20 * 1000;
    boolean isAlarm;
    private Contact mContact;
    private String cameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        mContext = this;
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mContact = new Contact();
        cameraId = getIntent().getExtras().getString("cameraId");
        long alarmTime = Long.parseLong(getIntent().getExtras().getString("alarmTime"));
        mvpPresenter.getCameraInfo(cameraId);
        String alarmTimeStr = Utils.ConvertTimeByLong(alarmTime);
        alarm_msg_time_tv.setText("报警时间:"+alarmTimeStr);
        alarmInit();
        excuteTimeOutTimer();
    }

    @OnClick({R.id.alarm_back,R.id.watch_video_image})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.alarm_back:
                finish();
                break;
            case R.id.watch_video_image:
                if (null != mContact) {
                    mContact.contactType = 0;
                    mContact.apModeState = 1;
                    Intent monitor = new Intent();
                    monitor.setClass(mContext, ApMonitorActivity.class);
                    monitor.putExtra("contact", mContact);
                    monitor.putExtra("connectType", ConstantValues.ConnectType.P2PCONNECT);
                    monitor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(monitor);
                    finish();
                } else {
                    watch_video_image.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    private void alarmInit() {
        final AnimationDrawable anim = (AnimationDrawable) alarm_fk_img
                .getBackground();
        ViewTreeObserver.OnPreDrawListener opdl = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                anim.start();
                return true;
            }

        };
        alarm_fk_img.getViewTreeObserver().addOnPreDrawListener(opdl);
    }

    boolean viewed = false;
    Timer timeOutTimer;
    public static final int USER_HASNOTVIEWED = 3;

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case USER_HASNOTVIEWED:
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    // 超时计时器
    public void excuteTimeOutTimer() {
        timeOutTimer = new Timer();
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                // 弹出一个对话框
                if (!viewed) {
                    Message message = new Message();
                    message.what = USER_HASNOTVIEWED;
                    mHandler.sendMessage(message);
                }
            }
        };
        timeOutTimer.schedule(mTask, TIME_OUT);
    }

    // 报警声音
    public void loadMusicAndVibrate() {
        isAlarm = true;
        MusicManger.getInstance().playAlarmMusic(this);
        new Thread() {
            public void run() {
                while (isAlarm) {
                    MusicManger.getInstance().Vibrate();
                    Utils.sleepThread(100);
                }
                MusicManger.getInstance().stopVibrate();
            }
        }.start();
    }

    @Override
    public void getAlarmCameraResult(AlarmCameraInfo.CameraBean cameraBean) {
        mContact.contactName = cameraBean.getCameraName();
        mContact.contactPassword = cameraBean.getCameraPwd();
        mContact.contactId = cameraId;
        alarm_msg_tv.setText(cameraBean.getCameraAddress()+"发生报警");
    }

    @Override
    public void ackResult() {

    }

    @Override
    protected AlarmPresenter createPresenter() {
        return new AlarmPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMusicAndVibrate();
        acquireWakeLock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicManger.getInstance().stop();
        releaseWakeLock();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        isAlarm = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeOutTimer != null) {
            timeOutTimer.cancel();
            timeOutTimer = null;
        }
    }

    private void releaseWakeLock() {
        if (mWakelock != null && mWakelock.isHeld()) {
            mWakelock.release();
            mWakelock = null;
        }
    }
    private PowerManager.WakeLock mWakelock;
    private void acquireWakeLock() {
        if (mWakelock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakelock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,this.getClass().getCanonicalName());
            mWakelock.acquire();
        }
    }
}
