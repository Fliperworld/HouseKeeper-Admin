package com.smart.cloud.fire.mvp.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hrsst.housekeeper.admin.R;
import com.jakewharton.rxbinding.view.RxView;
import com.smart.cloud.fire.base.ui.MvpActivity;
import com.smart.cloud.fire.mvp.login.LoginActivity;
import com.smart.cloud.fire.mvp.login.SplashActivity;
import com.smart.cloud.fire.mvp.register.presenter.RegisterPresenter;
import com.smart.cloud.fire.mvp.register.view.RegisterView;
import com.smart.cloud.fire.utils.T;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class RegisterEmailActivity extends MvpActivity<RegisterPresenter> implements RegisterView {

    @Bind(R.id.register_phone_tv)
    TextView registerPhoneTv;
    @Bind(R.id.register_email_user)
    EditText registerEmailUser;
    @Bind(R.id.register_email_pwd)
    EditText registerEmailPwd;
    @Bind(R.id.register_email_comfire_pwd)
    EditText registerEmailComfirePwd;
    @Bind(R.id.register_btn_email)
    Button registerBtnEmail;
    @Bind(R.id.register_email_old_user_tv)
    TextView registerEmailOldUserTv;
    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.nick_name)
    EditText nickName;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        ButterKnife.bind(this);
        mContext = this;
        action();
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    private void action() {
        RxView.clicks(registerEmailOldUserTv).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到登录界面
                        Intent intent1 = new Intent(mContext, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                });
        RxView.clicks(registerPhoneTv).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到邮箱界面
                        Intent intent1 = new Intent(mContext, RegisterPhoneActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                });
        RxView.clicks(registerBtnEmail).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String phoneNO = registerEmailUser.getText().toString().trim();
                        String pwd = registerEmailPwd.getText().toString().trim();
                        String rePwd = registerEmailComfirePwd.getText().toString().trim();
                        String name = nickName.getText().toString().trim();
                        mvpPresenter.registerEmail(phoneNO, pwd, rePwd, mContext,name);
                    }
                });
    }

    @Override
    public void getDataFail(String msg) {
        T.showShort(mContext, msg);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void register() {
        T.showShort(mContext, "注册成功,正在登陆");
        Intent login = new Intent(mContext, SplashActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    public void getMesageSuccess() {

    }

}
