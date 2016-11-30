package com.smart.cloud.fire.geTuiPush;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.igexin.sdk.PushManager;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpError;
import com.smart.cloud.fire.retrofit.ApiStores;
import com.smart.cloud.fire.retrofit.AppClient;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;

import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/11/25.
 */
public class GeTuiService extends Service{
    private CompositeSubscription mCompositeSubscription;
    private Timer mTimer;
    private String userNum;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userNum = intent.getExtras().getString("UserNum");
        regFilter();
        mTimer = new Timer();
        setTimerDoAction(doActionHandler,mTimer);
        return startId;
    }

    private void setTimerDoAction(final Handler oj, Timer t) {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message = oj.obtainMessage();
                message.what = 2;
                oj.sendMessage(message);
            }
        }, 1000, 5000/*表示1000毫秒之後，每隔1000毫秒執行一次 */);
    }

    private Handler doActionHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    initPush();
                    break;
                default:
                    break;
            }
        }
    };

    private void initPush() {
        PushManager.getInstance().initialize(getApplicationContext());
    }

    private void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantValues.Action.GET_GE_TUI_PUSH_CID);
        getApplicationContext().registerReceiver(mReceiver, filter);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ConstantValues.Action.GET_GE_TUI_PUSH_CID)){
                String cid = intent.getExtras().getString("cid");
                if(cid!=null&&cid.length()>0&&userNum!=null&&userNum.length()>0){
                    if(mTimer!=null){
                        mTimer.cancel();
                        mTimer=null;
                        goToServer(cid,userNum);
                    }
                }
            }
        }
    };

    private void goToServer(String cid,String userId){
        ApiStores apiStores = AppClient.retrofit(ConstantValues.SERVER_PUSH).create(ApiStores.class);
        Observable observable = apiStores.bindAlias( userId,cid,"scfire");
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<HttpError>() {
            @Override
            public void onSuccess(HttpError model) {
                MyApp.app.setPushState(model.getState());
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onCompleted() {
                stopSelf();
            }
        }));
    }

    private void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    @Override
    public void onDestroy() {
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        if(mCompositeSubscription!=null){
            mCompositeSubscription=null;
        }
        super.onDestroy();
    }
}
