package com.smart.cloud.fire.mvp.Alarm;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrsst.housekeeper.admin.R;
import com.jakewharton.rxbinding.view.RxView;
import com.smart.cloud.fire.base.ui.MvpActivity;
import com.smart.cloud.fire.data.CameraData;
import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.InitBaiduNavi;
import com.smart.cloud.fire.utils.MusicManger;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.utils.Utils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ReceiveCallAlarmActivity extends MvpActivity<AlarmPresenter> implements AlarmView {

    @Bind(R.id.alarm_fk_img)
    ImageView alarm_fk_img;
    @Bind(R.id.alarm_fk_center)
    LinearLayout alarmFkCenter;
    @Bind(R.id.alarm_tc_image)
    ImageView alarmTcImage;
    @Bind(R.id.alarm_do_it_btn)
    Button alarmDoItBtn;
    @Bind(R.id.alarm_lead_to_btn)
    Button alarmLeadToBtn;
    @Bind(R.id.alarm_type)
    TextView alarmType;
    @Bind(R.id.alarm_time)
    TextView alarmTime;
    @Bind(R.id.cancel_alarm_tv)
    TextView cancelAlarmTv;
    @Bind(R.id.cancel_alarm)
    RelativeLayout cancelAlarm;
    @Bind(R.id.alarm_address)
    TextView alarmAddress;
    private ReceiveCallAlarmBean receiveCallAlarmBean;
    private Context mContext;
    private boolean isAlarm;

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
        setContentView(R.layout.activity_receive_call_alarm);
        ButterKnife.bind(this);
        receiveCallAlarmBean = (ReceiveCallAlarmBean) getIntent().getExtras().getSerializable("receiveCallAlarmBean");
        init();
        alarmInit();
    }

    @Override
    protected AlarmPresenter createPresenter() {
        return new AlarmPresenter(this);
    }

    private void init() {
        alarmType.setText(receiveCallAlarmBean.getCallerName() + receiveCallAlarmBean.getInfo());
        alarmTime.setText("报警时间:" + receiveCallAlarmBean.getAlarmTime());
        alarmAddress.setText("地址:"+receiveCallAlarmBean.getAddress());
        RxView.clicks(alarmLeadToBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                CameraData.CameraBean cameraBean1 = new CameraData.CameraBean();
                cameraBean1.setLongitude(receiveCallAlarmBean.getLongitude());
                cameraBean1.setLatitude(receiveCallAlarmBean.getLatitude());
                Reference<Activity> reference = new WeakReference(mContext);
                new InitBaiduNavi(reference.get(), cameraBean1);
            }
        });
    }

    @OnClick({R.id.alarm_tc_image, R.id.cancel_alarm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alarm_tc_image:
                finish();
                break;
            case R.id.cancel_alarm:
                String userNumber = SharedPreferencesManager.getInstance().getData(mContext, SharedPreferencesManager.SP_FILE_GWELL,
                        SharedPreferencesManager.KEY_RECENTPASS_NUMBER);
                mvpPresenter.textAlarmAck(userNumber, receiveCallAlarmBean.getAlarmSerialNumber());
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
            mWakelock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
            mWakelock.acquire();
        }
    }

    @Override
    public void getAlarmCameraResult(AlarmCameraInfo.CameraBean cameraBean) {
    }

    @Override
    public void ackResult() {
        finish();
    }
}
