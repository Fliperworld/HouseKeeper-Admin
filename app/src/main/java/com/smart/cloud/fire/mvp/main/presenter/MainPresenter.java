package com.smart.cloud.fire.mvp.main.presenter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hrsst.housekeeper.admin.R;
import com.smart.cloud.fire.base.presenter.BasePresenter;
import com.smart.cloud.fire.base.ui.BaseFragment;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.UpdateXml;
import com.smart.cloud.fire.global.VersionXml;
import com.smart.cloud.fire.mvp.fragment.CallAlarmFragment.CallAlarmFragment;
import com.smart.cloud.fire.mvp.fragment.CollectFragment.CollectFragment;
import com.smart.cloud.fire.mvp.fragment.ConfireFireFragment.ConfireFireFragment;
import com.smart.cloud.fire.mvp.fragment.MapFragment.MapFragment;
import com.smart.cloud.fire.mvp.fragment.SettingFragment.SettingFragment;
import com.smart.cloud.fire.mvp.fragment.ShopInfoFragment.ShopInfoFragment;
import com.smart.cloud.fire.mvp.main.view.MainView;
import com.smart.cloud.fire.rxjava.ApiCallback;
import com.smart.cloud.fire.rxjava.SubscriberCallBack;
import com.smart.cloud.fire.utils.CompareVersion;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.utils.T;
import com.smart.cloud.fire.view.MyRadioButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/9/21.
 */
public class MainPresenter extends BasePresenter<MainView> {
    private ConfireFireFragment mHomeFragment;
    private ShopInfoFragment mCategoryFragment;
    private CollectFragment mCollectFragment;
    private SettingFragment mSettingFragment;
    private MapFragment mMapFragment;
    private CallAlarmFragment mCallAlarmFragment;
    private FragmentTransaction mTransaction;
    private FragmentManager manager;
    private int clickNum = 0;
    private int clickNumBefore = 0;
    private boolean autoCheck;

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void initWidget(RadioGroup radioGroup, List<MyRadioButton> myRadioButton, int privilege, Activity mContext,FrameLayout otherFrameLayout,FrameLayout mainContent){
        manager = mContext.getFragmentManager();
        mainContent.setVisibility(View.VISIBLE);
        otherFrameLayout.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams linearParams2 =(LinearLayout.LayoutParams) radioGroup.getLayoutParams(); //取控件textView当前的布局参数
            int h1 = linearParams2.height;
            linearParams2.height = h1/2+15;// 控件的高强制设成20
            radioGroup.setLayoutParams(linearParams2);
            for(int i=0;i<5;i++){
                RadioButton mRadioButton = myRadioButton.get(i);
                switch (i){
                    case 0:
                        mRadioButton.setVisibility(View.VISIBLE);
                        mRadioButton.setChecked(true);
                        break;
                    case 1:
                        break;
                    case 2:
                        mRadioButton.setVisibility(View.GONE);
                        break;
                    case 3:
                        mRadioButton.setVisibility(View.GONE);
                        break;
                }
            }
            clickNumBefore=0;
            mMapFragment = new MapFragment();
            mTransaction = manager.beginTransaction();
            mTransaction.replace(R.id.main_content, mMapFragment, "mAgencyMapFragment").commit();
            mvpView.showTitle(true);
    }

    public void replaceFragment(int checkedId, FrameLayout otherFrameLayout,FrameLayout mainContent){
        switch (checkedId) {
            case R.id.radio_comment1:
                clickNum = 0;
                mainContent.setVisibility(View.VISIBLE);
                otherFrameLayout.setVisibility(View.INVISIBLE);
                if (mMapFragment == null) {
                    mMapFragment = new MapFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mMapFragment, R.id.main_content, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                mvpView.showTitle(true);
                break;
            case R.id.radio_letter:
                clickNum = 2;
                mainContent.setVisibility(View.INVISIBLE);
                otherFrameLayout.setVisibility(View.VISIBLE);
                if (mCategoryFragment == null) {
                    mCategoryFragment = new ShopInfoFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mCategoryFragment, R.id.otherFrameLayout, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                mvpView.showTitle(false);
                break;
            case R.id.radio_reference:
                clickNum = 4;
                mainContent.setVisibility(View.INVISIBLE);
                otherFrameLayout.setVisibility(View.VISIBLE);
                if (mCollectFragment == null) {
                    mCollectFragment = new CollectFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mCollectFragment, R.id.otherFrameLayout, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                mvpView.hideTitle();
                break;
            case R.id.radio_comment:
                clickNum = 3;
                mainContent.setVisibility(View.VISIBLE);
                otherFrameLayout.setVisibility(View.INVISIBLE);
                if (mMapFragment == null) {
                    mMapFragment = new MapFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mMapFragment, R.id.main_content, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                break;
            case R.id.radio_home:
                clickNum = 1;
                mainContent.setVisibility(View.INVISIBLE);
                otherFrameLayout.setVisibility(View.VISIBLE);
                if (mHomeFragment == null) {
                    mHomeFragment = new ConfireFireFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mHomeFragment, R.id.otherFrameLayout, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                break;
            case R.id.radio_more:
                clickNum = 5;
                mainContent.setVisibility(View.INVISIBLE);
                otherFrameLayout.setVisibility(View.VISIBLE);
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mSettingFragment, R.id.otherFrameLayout, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                mvpView.hideTitle();
                break;
            case R.id.call_alarm:
                clickNum = 6;
                mainContent.setVisibility(View.INVISIBLE);
                otherFrameLayout.setVisibility(View.VISIBLE);
                if (mCallAlarmFragment == null) {
                    mCallAlarmFragment = new CallAlarmFragment();
                }
                if (clickNumBefore != clickNum) {
                    changeFragment(manager, mTransaction, mCallAlarmFragment, R.id.otherFrameLayout, clickNumBefore, clickNum);
                    clickNumBefore = clickNum;
                }
                break;
            default:
                break;
        }
    }

    private void changeFragment(FragmentManager mFragmentManager, FragmentTransaction mFragmentTransaction, BaseFragment mBaseFragment, int id, int be, int after){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(id, mBaseFragment, ConstantValues.fragmentStr[after]);
        Fragment fragment = mFragmentManager.findFragmentByTag(ConstantValues.fragmentStr[be]);
        if (fragment != null) {
            mFragmentTransaction.remove(fragment);    //释放百度地图 Fragment
        }
        mFragmentTransaction.commit();
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public void exitBy2Click(Context mContext) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            T.showShort(mContext,"再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            mvpView.exitBy2Click(isExit);
        }
    }

    private void checkVersion(final String localVersion){
        Observable<VersionXml> observable =  apiStoresUpdate.checkVersion();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberCallBack<>(new ApiCallback<VersionXml>() {
                    @Override
                    public void onSuccess(VersionXml model) {
                        UpdateXml mUpdateXml = model.getList().get(0);
                        String serverCode = mUpdateXml.getVersionName();
                        int result;
                        try {
                            result = CompareVersion.compareVersion1(serverCode,localVersion);
                            if (result==1) {
                                mvpView.showUpdateDialog(mUpdateXml.getMsg(),mUpdateXml.getUrl());
                            }
                            if(result<1&&autoCheck==false){
                                mvpView.showUpdateDialog(mUpdateXml.getMsg(),null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

    private String getLocalVersion(Context mContext){
        String localVersion = null;
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            localVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public void updateVersion(Context mContext,boolean autoCheck){
        this.autoCheck = autoCheck;
        long last_check_update_time = SharedPreferencesManager.getInstance().getLastAutoCheckUpdateTime(mContext);
        long now_time = System.currentTimeMillis();
        if (((now_time - last_check_update_time) > 1000 * 60 * 60 * 12&&autoCheck==true)||autoCheck==false) {
            SharedPreferencesManager.getInstance().putLastAutoCheckUpdateTime(now_time, mContext);
            String localVersion = getLocalVersion(mContext);
            checkVersion(localVersion);
        }
    }
}
