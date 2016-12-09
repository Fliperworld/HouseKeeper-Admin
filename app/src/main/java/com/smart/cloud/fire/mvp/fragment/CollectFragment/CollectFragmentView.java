package com.smart.cloud.fire.mvp.fragment.CollectFragment;

import com.smart.cloud.fire.global.AlarmMsg;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.ShopType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface CollectFragmentView {
    void getDataSuccess(List<AlarmMsg.AlarmBean> alarmBeanList,boolean actionType);

    void getDataFail(String msg);

    void showLoading();

    void hideLoading();

    void dealAlarmMsgSuccess();

    void getShopType(ArrayList<Object> shopTypes);

    void getShopTypeFail(String msg);

    void getAreaType(ArrayList<Object> shopTypes);

    void getAreaTypeFail(String msg);

    void getChoiceArea(Area area);

    void getChoiceShop(ShopType shopType);
}
