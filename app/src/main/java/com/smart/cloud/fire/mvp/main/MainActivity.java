package com.smart.cloud.fire.mvp.main;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hrsst.housekeeper.R;
import com.igexin.sdk.PushManager;
import com.p2p.core.P2PHandler;
import com.smart.cloud.fire.base.ui.MvpActivity;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.MainService;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.mvp.camera.AddCameraFirstActivity;
import com.smart.cloud.fire.mvp.login.SplashActivity;
import com.smart.cloud.fire.mvp.main.presenter.MainPresenter;
import com.smart.cloud.fire.mvp.main.view.MainView;
import com.smart.cloud.fire.service.DemoIntentService;
import com.smart.cloud.fire.service.DemoPushService;
import com.smart.cloud.fire.service.RemoteService;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.view.MyRadioButton;
import com.smart.cloud.fire.view.NormalDialog;
import com.smart.cloud.fire.yoosee.P2PListener;
import com.smart.cloud.fire.yoosee.SettingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/21.
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @Bind(R.id.call_alarm)
    MyRadioButton callAlarm;
    @Bind(R.id.title_rela)
    RelativeLayout titleRela;
    @Bind(R.id.add_device)
    ImageView addDevice;
    @Bind(R.id.layout_add)
    LinearLayout layoutAdd;
    private Context mContext;
    private AlertDialog dialog_update;
    @Bind(R.id.bottom_group)
    RadioGroup bottom_group;
    @Bind(R.id.radio_comment)
    MyRadioButton radio_comment;
    @Bind(R.id.radio_comment1)
    MyRadioButton radio_comment1;
    @Bind(R.id.radio_letter)
    MyRadioButton radio_letter;
    @Bind(R.id.radio_home)
    MyRadioButton radio_home;
    @Bind(R.id.main_content)
    FrameLayout mainContent;
    @Bind(R.id.otherFrameLayout)
    FrameLayout otherFrameLayout;
    private Animation animation_out, animation_in;
    public boolean isHideAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        regFilter();
        startService(new Intent(MainActivity.this, RemoteService.class));
        mvpPresenter.updateVersion(mContext, true);
    }

    private void initView() {
        P2PHandler.getInstance().p2pInit(this,
                new P2PListener(),
                new SettingListener());
        connect();
        List<MyRadioButton> list = new ArrayList<>();
        list.add(radio_comment1);
        list.add(radio_letter);
        list.add(radio_comment);
        list.add(radio_home);
        list.add(callAlarm);
        mvpPresenter.initWidget(bottom_group, list, MyApp.app.getPrivilege(), this, otherFrameLayout, mainContent);
        mainContent.setVisibility(View.VISIBLE);
        otherFrameLayout.setVisibility(View.INVISIBLE);
        bottom_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mvpPresenter.replaceFragment(checkedId, otherFrameLayout, mainContent);
            }
        });
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        animation_out = AnimationUtils.loadAnimation(mContext,
                R.anim.scale_amplify);
        animation_in = AnimationUtils.loadAnimation(mContext,
                R.anim.scale_narrow);
    }

    private void connect() {
        Intent service = new Intent(mContext, MainService.class);
        startService(service);
    }

    private void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("APP_EXIT");
        filter.addAction(ConstantValues.CHECK_VERSION_UPDATE);
        mContext.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("APP_EXIT")) {
                SharedPreferencesManager.getInstance().putData(mContext,
                        SharedPreferencesManager.SP_FILE_GWELL,
                        SharedPreferencesManager.KEY_RECENTPASS,
                        "");
                PushManager.getInstance().stopService(getApplicationContext());
                Intent in = new Intent(mContext, SplashActivity.class);
                startActivity(in);
                finish();
            }
            if (intent.getAction().equals(ConstantValues.CHECK_VERSION_UPDATE)) {
                mvpPresenter.updateVersion(mContext, false);
            }
        }
    };

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mvpPresenter.exitBy2Click(mContext);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void exitBy2Click(boolean isExit) {
        if (isExit) {
            moveTaskToBack(false);
        }
    }

    @Override
    public void showUpdateDialog(String message, String urlStr) {
        new NormalDialog(mContext, message, urlStr).showVersionDialog();
    }

    @Override
    public void hideTitle() {
        titleRela.setVisibility(View.GONE);
        addDevice.setVisibility(View.GONE);
        layoutAdd.setVisibility(View.GONE);
        isHideAdd = true;
    }

    @Override
    public void showTitle(boolean ifAdd) {
        titleRela.setVisibility(View.VISIBLE);
        if (ifAdd) {
            addDevice.setVisibility(View.VISIBLE);
        } else {
            addDevice.setVisibility(View.GONE);
            layoutAdd.setVisibility(View.GONE);
            isHideAdd = true;
        }
    }

    @OnClick({R.id.add_device,R.id.radar_add,R.id.manually_add})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_device:
                Intent intent = new Intent(mContext, AddCameraFirstActivity.class);
                startActivity(intent);
                layoutAdd.setVisibility(View.GONE);
                break;
            case R.id.radar_add:
//                Intent intent = new Intent(mContext, AddCameraFirstActivity.class);
//                startActivity(intent);
//                layoutAdd.setVisibility(View.GONE);
                break;
            case R.id.manually_add:
                layoutAdd.setVisibility(View.GONE);
                break;
        }

    }

    public void hideAdd() {
        layoutAdd.startAnimation(animation_in);
        layoutAdd.setVisibility(LinearLayout.GONE);
        isHideAdd = true;
    }

    public void showAdd() {
        layoutAdd.setVisibility(LinearLayout.VISIBLE);
        layoutAdd.startAnimation(animation_out);
        isHideAdd = false;
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
