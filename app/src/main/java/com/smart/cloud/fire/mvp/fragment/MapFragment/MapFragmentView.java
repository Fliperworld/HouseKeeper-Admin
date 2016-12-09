package com.smart.cloud.fire.mvp.fragment.MapFragment;

import com.smart.cloud.fire.data.CameraData;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.Contact;
import com.smart.cloud.fire.global.ShopType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface MapFragmentView {
    void getDataSuccess(List<CameraData.CameraBean> cameraBeen);

    void getDataFail(String msg);

    void showLoading();

    void hideLoading();

    void getShopType(ArrayList<Object> shopTypes);

    void getShopTypeFail(String msg);

    void getAreaType(ArrayList<Object> shopTypes);

    void getAreaTypeFail(String msg);

    void dealAlarmMsgSuccess();

    void openCamera(Contact contact);

    void getChoiceArea(Area area);

    void getChoiceShop(ShopType shopType);
}
