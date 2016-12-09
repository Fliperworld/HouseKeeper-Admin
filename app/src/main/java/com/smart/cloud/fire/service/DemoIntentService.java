package com.smart.cloud.fire.service;

import android.content.Context;
import android.content.Intent;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.mvp.Alarm.ReceiveCallAlarmActivity;
import com.smart.cloud.fire.mvp.Alarm.ReceiveCallAlarmBean;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpError;
import com.smart.cloud.fire.retrofit.ApiStores;
import com.smart.cloud.fire.retrofit.AppClient;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;
import com.smart.cloud.fire.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/12/2.
 */
public class DemoIntentService extends GTIntentService {
    private CompositeSubscription mCompositeSubscription;
    @Override
    public void onReceiveServicePid(Context context, int i) {
    }

    @Override
    public void onReceiveClientId(Context context, String cid) {
        String userNumber = SharedPreferencesManager.getInstance().getData(context, SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_RECENTPASS_NUMBER);
        PushManager.getInstance().bindAlias(this.getApplicationContext(),userNumber);
        goToServer(cid,userNumber);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String msg = new String(gtTransmitMessage.getPayload());
        try {
            JSONObject dataJson = new JSONObject(msg);
            ReceiveCallAlarmBean receiveCallAlarmBean = new ReceiveCallAlarmBean();
            receiveCallAlarmBean.setAddress(dataJson.getString("address"));
            receiveCallAlarmBean.setLatitude(dataJson.getString("latitude"));
            receiveCallAlarmBean.setLongitude(dataJson.getString("longitude"));
            receiveCallAlarmBean.setAlarmSerialNumber(dataJson.getString("alarmSerialNumber"));
            receiveCallAlarmBean.setAlarmTime(dataJson.getString("alarmTime"));
            receiveCallAlarmBean.setAreaName(dataJson.getString("areaName"));
            receiveCallAlarmBean.setCallerId(dataJson.getString("callerId"));
            receiveCallAlarmBean.setInfo(dataJson.getString("info"));
            receiveCallAlarmBean.setCallerName(dataJson.getString("callerName"));
            receiveCallAlarmBean.setCameraId(dataJson.getString("cameraId"));
            Intent intent = new Intent(context, ReceiveCallAlarmActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("receiveCallAlarmBean",receiveCallAlarmBean);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        System.out.print(b);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        System.out.print(gtCmdMessage);
    }

    private void goToServer(String cid, String userId){
        ApiStores apiStores = AppClient.retrofit(ConstantValues.SERVER_PUSH).create(ApiStores.class);
        Observable observable = apiStores.bindAlias( userId,cid,"house");
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
}
