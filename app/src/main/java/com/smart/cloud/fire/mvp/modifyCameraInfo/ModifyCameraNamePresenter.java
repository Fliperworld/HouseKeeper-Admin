package com.smart.cloud.fire.mvp.modifyCameraInfo;

import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.global.PostResult;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ModifyCameraNamePresenter extends BasePresenter<ModifyCameraNameView>{

    public ModifyCameraNamePresenter(ModifyCameraNameView modifyCameraNameView){
        attachView(modifyCameraNameView);
    }

    public void modifyName(final String cameraName, String cameraId){
        if(cameraName==null||cameraName.length()<=0){
            mvpView.errorMessage("请填写摄像机名称");
            return;
        }
        mvpView.showLoading();
        Observable<PostResult> observable = apiStoreServer.changeCameraName(cameraId,cameraName);
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<PostResult>() {
            @Override
            public void onSuccess(PostResult model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){
                    mvpView.modifyCameraPwdResult("修改成功",cameraName);
                }else{
                    mvpView.errorMessage("修改失败");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.errorMessage("网络错误");
            }

            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }
        }));
    }
}
