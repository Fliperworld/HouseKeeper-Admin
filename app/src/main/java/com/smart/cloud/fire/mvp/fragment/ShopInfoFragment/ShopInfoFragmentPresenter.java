package com.smart.cloud.fire.mvp.fragment.ShopInfoFragment;

import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.ShopType;
import com.smart.cloud.fire.mvp.fragment.MapFragment.Camera;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpAreaResult;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpError;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ShopInfoFragmentPresenter extends BasePresenter<ShopInfoFragmentView> {
    public ShopInfoFragmentPresenter(ShopInfoFragmentView view){
        attachView(view);
    }

    public void getAllCamera(String userId, String privilege, String page, boolean refresh, final boolean getMore){
        if(!refresh){
            mvpView.showLoading();
        }
        Observable<Camera> mObservable = apiStoreServer.managerGetAllCameraDetail(userId,privilege,page);
        addSubscription(mObservable,new SubscriberCallBack<>(new ApiCallback<Camera>() {
            @Override
            public void onSuccess(Camera model) {
                int result = model.getErrorCode();
                if(result==0){
                   List<Camera.CameraBean> cameraBeanList = model.getCamera();
                    if(getMore){
                        mvpView.onLoadingMore(cameraBeanList);
                    }else {
                        mvpView.getDataSuccess(cameraBeanList);
                    }
                }else{
                    List<Camera.CameraBean> cameraList = new ArrayList<>();
                    mvpView.getDataSuccess(cameraList);
                    mvpView.getDataFail("无数据");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                List<Camera.CameraBean> cameraList = new ArrayList<>();
                mvpView.getDataSuccess(cameraList);
                mvpView.getDataFail("网络错误");
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
                    if(model!=null&&model.size()>0){
                        mvpView.getAreaType(model,type);
                    }else{
                        mvpView.getAreaTypeFail("无数据",type);
                    }
            }
            @Override
            public void onFailure(int code, String msg) {
                    mvpView.getAreaTypeFail("网络错误",type);
            }
            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void getShop(ShopType shopType) {
        super.getShop(shopType);
        mvpView.getChoiceShop(shopType);
    }

    @Override
    public void getArea(Area area) {
        super.getArea(area);
        mvpView.getChoiceArea(area);
    }

}
