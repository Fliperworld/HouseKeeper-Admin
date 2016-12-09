package com.smart.cloud.fire.mvp.Alarm;

import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.PostResult;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/27.
 */
public class AlarmPresenter extends BasePresenter<AlarmView>{
    public AlarmPresenter(AlarmView view){
        attachView(view);
    }

    public void getCameraInfo(String cameraId){
        Observable<AlarmCameraInfo> observable = apiStoreServer.getOneCamera(cameraId);
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<AlarmCameraInfo>() {
            @Override
            public void onSuccess(AlarmCameraInfo model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){
                    AlarmCameraInfo.CameraBean cameraBean = model.getCamera();
                    mvpView.getAlarmCameraResult(cameraBean);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    public void textAlarmAck(String userId,String alarmSerialNumber){
        Observable<PostResult> observable = apiStoreServer.textAlarmAck(userId,alarmSerialNumber);
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<PostResult>() {
            @Override
            public void onSuccess(PostResult model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){

                }
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

}
