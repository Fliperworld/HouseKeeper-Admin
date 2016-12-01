package com.smart.cloud.fire.mvp.register.presenter;

import android.content.Context;

import com.p2p.core.utils.MD5;
import com.p2p.core.utils.MyUtils;
import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.global.PostResult;
import com.smart.cloud.fire.mvp.register.model.RegisterModel;
import com.smart.cloud.fire.mvp.register.view.RegisterView;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.utils.Utils;

import java.util.Random;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RegisterPresenter  extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView view) {
        attachView(view);
    }

    public void getMesageCode(String phoneNo){
        String AppVersion = MyUtils.getBitProcessingVersion();
        mvpView.showLoading();
        Random random = new Random();
        int value = random.nextInt(4);
        addSubscription(apiStores[value].getMesageCode("86", phoneNo,AppVersion),
                new SubscriberCallBack<>(new ApiCallback<RegisterModel>() {
                    @Override
                    public void onSuccess(RegisterModel model) {
                        String errorCode = model.getError_code();
                        switch (errorCode){
                            case "0":
                                mvpView.getMesageSuccess();
                                break;
                            case "6":
                                mvpView.getDataFail("手机号已被注册");
                                break;
                            case "9":
                                mvpView.getDataFail("手机号码格式错误");
                                break;
                            case "27":
                                mvpView.getDataFail("获取手机验证码超时，请稍后再试");
                                break;
                            default:
                                break;
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

    public void register(final String phoneNo, final String pwd, String rePwd, final String code, final Context mContext){
        MD5 md = new MD5();
        final String password = md.getMD5ofStr(pwd);
        final String rePassword = md.getMD5ofStr(rePwd);
        mvpView.showLoading();
        Random random = new Random();
        final int value = random.nextInt(4);
        twoSubscription(apiStores[value].verifyPhoneCode("86", phoneNo,code),new Func1<RegisterModel, Observable<RegisterModel>>(){
                    @Override
                    public Observable<RegisterModel> call(RegisterModel registerModel) {
                        return apiStores[value].register("1","","86",phoneNo,password,rePassword,code,"1");
                    }
                },
                new SubscriberCallBack<>(new ApiCallback<RegisterModel>() {
                    @Override
                    public void onSuccess(RegisterModel model) {
                        String errorCode = model.getError_code();
                        switch (errorCode){
                            case "0":
                                String userID = "0"+String.valueOf((Integer.parseInt(model.getUserID())&0x7fffffff));
                                SharedPreferencesManager.getInstance().putData(mContext,
                                        SharedPreferencesManager.SP_FILE_GWELL,
                                        SharedPreferencesManager.KEY_RECENTPASS,
                                        pwd);
                                SharedPreferencesManager.getInstance().putData(mContext,
                                        SharedPreferencesManager.SP_FILE_GWELL,
                                        SharedPreferencesManager.KEY_RECENTNAME,
                                        phoneNo);
                                SharedPreferencesManager.getInstance().putData(mContext,
                                        SharedPreferencesManager.SP_FILE_GWELL,
                                        SharedPreferencesManager.KEY_RECENTPASS_NUMBER, userID);
                                registerToServer(userID,"",phoneNo,pwd,"3");
                                break;
                            case "6":
                                mvpView.getDataFail("手机号已被注册");
                                break;
                            case "9":
                                mvpView.getDataFail("手机号码格式错误");
                                break;
                            case "18":
                                mvpView.getDataFail("验证码输入错误");
                            case "10":
                                mvpView.getDataFail("两次输入的密码不一致");
                                break;
                            default:
                                break;
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

    private void registerToServer(String userId,String userName,String phone,String email,String privilege){
        Observable<PostResult> observable = apiStoreServer.registerServerIp(userId,userName,phone,email,privilege);
        addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<PostResult>() {
            @Override
            public void onSuccess(PostResult model) {
                int errorCode = model.getErrorCode();
                if(errorCode==0){
                    mvpView.register();
                }else{
                    mvpView.getDataFail("注册失败");
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

    public void registerEmail(final String phoneNo, final String pwd, String rePwd, final Context mContext){
        boolean isEmail = Utils.isEmial(phoneNo);
        if(isEmail){
            mvpView.showLoading();
            MD5 md = new MD5();
            final String password = md.getMD5ofStr(pwd);
            final String rePassword = md.getMD5ofStr(rePwd);
            Random random = new Random();
            final int value = random.nextInt(4);
            Observable<RegisterModel> observable = apiStores[value].register("1",phoneNo,"","",password,rePassword,"","1");
            addSubscription(observable,new SubscriberCallBack<>(new ApiCallback<RegisterModel>() {
                @Override
                public void onSuccess(RegisterModel model) {
                    String errorCode = model.getError_code();
                    switch (errorCode){
                        case "0":
                            String userID = "0"+String.valueOf((Integer.parseInt(model.getUserID())&0x7fffffff));
                            SharedPreferencesManager.getInstance().putData(mContext,
                                    SharedPreferencesManager.SP_FILE_GWELL,
                                    SharedPreferencesManager.KEY_RECENTPASS,
                                    pwd);
                            SharedPreferencesManager.getInstance().putData(mContext,
                                    SharedPreferencesManager.SP_FILE_GWELL,
                                    SharedPreferencesManager.KEY_RECENTNAME_EMAIL,
                                    phoneNo);
                            SharedPreferencesManager.getInstance().putData(mContext,
                                    SharedPreferencesManager.SP_FILE_GWELL,
                                    SharedPreferencesManager.KEY_RECENTPASS_NUMBER, userID);
                            registerToServer(userID,"中文","",phoneNo,3+"");
                            break;
                        case "7":
                            mvpView.hideLoading();
                            mvpView.getDataFail("邮箱已被注册");
                            break;
                        case "4":
                            mvpView.hideLoading();
                            mvpView.getDataFail("邮箱格式不符合要求");
                            break;
                        case "10":
                            mvpView.hideLoading();
                            mvpView.getDataFail("两次输入的密码不一致");
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    mvpView.getDataFail(msg);
                }

                @Override
                public void onCompleted() {
                }
            }));
        }else{
            mvpView.getDataFail("邮箱格式不正确");
        }
    }

}
