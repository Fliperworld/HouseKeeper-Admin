package com.smart.cloud.fire.mvp.fragment.MapFragment;

import android.app.Activity;
import android.os.Bundle;

import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.data.CameraData;
import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.Contact;
import com.smart.cloud.fire.global.InitBaiduNavi;
import com.smart.cloud.fire.global.PostResult;
import com.smart.cloud.fire.global.ShopType;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.view.NormalDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MapFragmentPresenter extends BasePresenter<MapFragmentView> {
    private MapFragment mapFragment;
    public MapFragmentPresenter(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        attachView(mapFragment);
    }

    public void getAllCamera(String userId, String privilege){
        mvpView.showLoading();
        Observable<CameraData> mObservable = apiStoreServer.managerGetAllCamera(userId,privilege,"");
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<CameraData>() {
            @Override
            public void onSuccess(CameraData model) {
                if(model!=null){
                    int errorCode = model.getErrorCode();
                    if(errorCode==0){
                        List<CameraData.CameraBean> cameraBeen = model.getCamera();
                        mvpView.getDataSuccess(cameraBeen);
                    }else{
                        mvpView.getDataFail("无数据");
                    }
                }
            }
            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail(msg);
            }
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }
        }));
    }

    //type:1表示查询商铺类型，2表示查询区域类型
    public void getPlaceTypeId(String userId, String privilege, final int type){
        Observable mObservable = null;
        if(type==1){
            mObservable= apiStoreServer.getPlaceTypeId(userId,privilege,"").map(new Func1<HttpError,ArrayList<Object>>() {
                @Override
                public ArrayList<Object> call(HttpError o) {
                    return o.getPlaceType();
                }
            });
        }else{
            mObservable= apiStoreServer.getAreaId(userId,privilege,"").map(new Func1<HttpAreaResult,ArrayList<Object>>() {
                @Override
                public ArrayList<Object> call(HttpAreaResult o) {
                    return o.getArea();
                }
            });
        }
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<ArrayList<Object>>() {
            @Override
            public void onSuccess(ArrayList<Object> model) {
                if(type==1){
                    if(model!=null&&model.size()>0){
                        mvpView.getShopType(model);
                    }else{
                        mvpView.getShopTypeFail("无数据");
                    }
                }else{
                    if(model!=null&&model.size()>0){
                        mvpView.getAreaType(model);
                    }else{
                        mvpView.getAreaTypeFail("无数据");
                    }
                }
            }
            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail("网络错误");
            }
            @Override
            public void onCompleted() {
            }
        }));
    }

    public void getNeedSmoke(String userId, String privilege,String areaId,String placeTypeId){
        mvpView.showLoading();
        Observable mObservable = apiStoreServer.getNeedSmoke(userId,privilege,areaId,"",placeTypeId);
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<HttpError>() {
            @Override
            public void onSuccess(HttpError model) {
                if(model!=null){
                    int errorCode = model.getErrorCode();
                    if(errorCode==0){
                        List<Smoke> smokes = model.getSmoke();
//                        mvpView.getDataSuccess(smokes);
                    }else {
                        mvpView.getAreaTypeFail("无数据");
                    }
                }else{
                    mvpView.getAreaTypeFail("无数据");
                }
            }
            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail("网络错误");
            }
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }
        }));
    }

    private void dealAlarm(String userId, String cameraId){
        mvpView.showLoading();
        Observable<PostResult> mObservable = apiStoreServer.dealAlarm(cameraId,userId);
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<PostResult>() {
            @Override
            public void onSuccess(PostResult model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){
                    mvpView.dealAlarmMsgSuccess();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }
        }));
    }

    public void getClickDev(Bundle bundle){
        final CameraData.CameraBean cameraBean = (CameraData.CameraBean) bundle.getSerializable("mNormalSmoke");
        NormalDialog normalDialog = new NormalDialog(mapFragment.getActivity());
        normalDialog.actionCameraDialog(cameraBean);

        normalDialog.setOnButtonCancelListener(new NormalDialog.OnButtonCancelListener() {//取消报警
            @Override
            public void onClick() {
                String userID = SharedPreferencesManager.getInstance().getData(mapFragment.getActivity(),
                        SharedPreferencesManager.SP_FILE_GWELL,
                        SharedPreferencesManager.KEY_RECENTNAME);
                dealAlarm(userID,cameraBean.getCameraId());
            }
        });
        normalDialog.setOnButtonOkListener(new NormalDialog.OnButtonOkListener() {//查看视频
            @Override
            public void onClick() {
                getOneCamera(cameraBean.getCameraId());
            }
        });
        normalDialog.setOnButtonDeleteListener(new NormalDialog.OnButtonDeleteListener() {//导航
            @Override
            public void onClick() {
                Reference<Activity> reference = new WeakReference(mapFragment.getActivity());
                new InitBaiduNavi(reference.get(),cameraBean);
            }
        });
    }

    @Override
    public void getArea(Area area) {
        mvpView.getChoiceArea(area);
    }

    @Override
    public void getShop(ShopType shopType) {
        mvpView.getChoiceShop(shopType);
    }

    private void getOneCamera(final String cameraId){
        mvpView.showLoading();
        Observable<AlarmCameraInfo> observable = apiStoreServer.getOneCamera(cameraId);
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<AlarmCameraInfo>() {
            @Override
            public void onSuccess(AlarmCameraInfo model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){
                    Contact contact = new Contact();
                    AlarmCameraInfo.CameraBean cameraBean = model.getCamera();
                    contact.contactId = cameraId;
                    contact.contactPassword = cameraBean.getCameraPwd();
                    contact.contactName = cameraBean.getCameraName();
                    mvpView.openCamera(contact);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail("网络不给力，请稍后再试");
            }

            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }
        }));
    }
}
