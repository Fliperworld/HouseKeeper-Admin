package com.smart.cloud.fire.mvp.fragment.MapFragment;

import android.os.Bundle;

import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.CameraMap;
import com.smart.cloud.fire.global.Contact;
import com.smart.cloud.fire.global.ShopType;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MapFragmentPresenter extends BasePresenter<MapFragmentView> {
    public MapFragmentPresenter(MapFragmentView view) {
        attachView(view);
    }

    public void getAllSmoke(String userId, String privilege){
        mvpView.showLoading();
        Observable<CameraMap> mObservable = apiStoreServer.managerGetAllCamera(userId,privilege,"");
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<CameraMap>() {
            @Override
            public void onSuccess(CameraMap model) {
                if(model!=null){
                    int errorCode = model.getErrorCode();
                    if(errorCode==0){
                        List<CameraMap.CameraBean> cameraBeen = model.getCamera();
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
            mObservable= apiStores1.getPlaceTypeId(userId,privilege,"").map(new Func1<HttpError,ArrayList<Object>>() {
                @Override
                public ArrayList<Object> call(HttpError o) {
                    return o.getPlaceType();
                }
            });
        }else{
            mObservable= apiStores1.getAreaId(userId,privilege,"").map(new Func1<HttpAreaResult,ArrayList<Object>>() {
                @Override
                public ArrayList<Object> call(HttpAreaResult o) {
                    return o.getSmoke();
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
        Observable mObservable = apiStores1.getNeedSmoke(userId,privilege,areaId,"",placeTypeId);
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

    public void dealAlarm(String userId, String smokeMac,String privilege){
        mvpView.showLoading();
        final Observable mObservable = apiStores1.getAllSmoke(userId,privilege,"");
        twoSubscription(apiStores1.dealAlarm(userId, smokeMac), new Func1<HttpError,Observable<HttpError>>() {
            @Override
            public Observable<HttpError> call(HttpError httpError) {
                return mObservable;
            }
        },new SubscriberCallBack<>(new ApiCallback<HttpError>() {
            @Override
            public void onSuccess(HttpError model) {
                if(model!=null){
                    int errorCode = model.getErrorCode();
                    if(errorCode==0){
                        List<Smoke> smokes = model.getSmoke();
//                        mvpView.getDataSuccess(smokes);
                    }else{
                        mvpView.getDataFail("无数据");
                    }
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

    public void getClickDev(Bundle bundle){
        CameraMap.CameraBean cameraBean = (CameraMap.CameraBean) bundle.getSerializable("mNormalSmoke");
        getOneCamera(cameraBean.getCameraId());

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
