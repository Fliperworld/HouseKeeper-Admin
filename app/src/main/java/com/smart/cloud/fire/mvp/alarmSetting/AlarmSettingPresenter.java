package com.smart.cloud.fire.mvp.alarmSetting;

import com.smart.cloud.fire.base.presenter.BasePresenter;

/**
 * Created by Administrator on 2016/12/6.
 */
public class AlarmSettingPresenter extends BasePresenter<AlarmSettingView>{

    public AlarmSettingPresenter (AlarmSettingActivity alarmSettingActivity){
        attachView(alarmSettingActivity);
    }
}
