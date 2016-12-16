package com.smart.cloud.fire.mvp.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.hrsst.housekeeper.admin.R;
import com.jakewharton.rxbinding.view.RxView;
import com.smart.cloud.fire.base.ui.MvpActivity;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.global.ShopType;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.utils.T;
import com.smart.cloud.fire.view.XCDropDownListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/27.
 */
public class AddCameraFourthActivity extends MvpActivity<AddCameraFourthPresenter> implements AddCameraFourthView {

    @Bind(R.id.add_repeater_mac)
    EditText addRepeaterMac;
    @Bind(R.id.add_fire_mac)
    EditText addFireMac;
    @Bind(R.id.add_fire_lat)
    EditText addFireLat;
    @Bind(R.id.add_fire_lon)
    EditText addFireLon;
    @Bind(R.id.add_fire_address)
    EditText addFireAddress;
    @Bind(R.id.add_fire_man)
    EditText addFireMan;
    @Bind(R.id.add_fire_man_phone)
    EditText addFireManPhone;
    @Bind(R.id.add_fire_man_two)
    EditText addFireManTwo;
    @Bind(R.id.add_fire_man_phone_two)
    EditText addFireManPhoneTwo;
    @Bind(R.id.add_fire_zjq)
    XCDropDownListView addFireZjq;
    @Bind(R.id.add_fire_type)
    XCDropDownListView addFireType;
    @Bind(R.id.add_fire_dev_btn)
    RelativeLayout addFireDevBtn;
    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.add_camera_name)
    EditText addCameraName;
    @Bind(R.id.add_camera_relative)
    RelativeLayout addCameraRelative;
    private String areaId = "";
    private String shopTypeId = "";
    private String userNumber;
    private String userId;
    private AddCameraFourthPresenter mAddCameraFourthPresenter;
    private String contactId;
    private Context mContext;
    private int privilege;
    private ShopType mShopType;
    private Area mArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fire);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        mContext = this;
        contactId = getIntent().getExtras().getString("contactId");
        userId = SharedPreferencesManager.getInstance().getData(mContext, SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_RECENTNAME);
        addRepeaterMac.setText(contactId);
        privilege = MyApp.app.getPrivilege();
        initView();
        userNumber = SharedPreferencesManager.getInstance().getData(mContext, SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_RECENTPASS_NUMBER);
    }

    private void initView() {
        addCameraRelative.setVisibility(View.VISIBLE);
        addFireZjq.setEditTextHint("区域");
        addFireType.setEditTextHint("类型");
        RxView.clicks(addFireDevBtn).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                addFire();
            }
        });
    }

    private void addFire() {
        if (mShopType != null) {
            shopTypeId = mShopType.getPlaceTypeId();
        }
        if (mArea != null) {
            areaId = mArea.getAreaId();
        }
        String longitude = addFireLon.getText().toString().trim();
        String latitude = addFireLat.getText().toString().trim();
        String smokeMac = addFireMac.getText().toString().trim();
        String address = addFireAddress.getText().toString().trim();
        String cameraName = addCameraName.getText().toString().trim();
        String principal1 = addFireMan.getText().toString().trim();
        String principal2 = addFireManTwo.getText().toString().trim();
        String principal1Phone = addFireManPhone.getText().toString().trim();
        String principal2Phone = addFireManPhoneTwo.getText().toString().trim();
        mAddCameraFourthPresenter.addCamera(userId,contactId,smokeMac,cameraName,address,longitude,
                latitude,principal1,principal1Phone,principal2,principal2Phone,
                areaId,shopTypeId);
    }


    @OnClick({ R.id.location_image, R.id.add_fire_zjq, R.id.add_fire_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_image:
                mAddCameraFourthPresenter.startLocation();
                break;
            case R.id.add_fire_zjq:
                if (addFireZjq.ifShow()) {
                    addFireZjq.closePopWindow();
                } else {
                    mAddCameraFourthPresenter.getPlaceTypeId(userNumber, privilege+"", 2);
                    addFireZjq.setClickable(false);
                    addFireZjq.showLoading();
                }
                break;
            case R.id.add_fire_type:
                if (addFireType.ifShow()) {
                    addFireType.closePopWindow();
                } else {
                    mAddCameraFourthPresenter.getPlaceTypeId(userNumber, privilege+"" , 1);
                    addFireType.setClickable(false);
                    addFireType.showLoading();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected AddCameraFourthPresenter createPresenter() {
        mAddCameraFourthPresenter = new AddCameraFourthPresenter(this);
        return mAddCameraFourthPresenter;
    }

    @Override
    protected void onStart() {
        mAddCameraFourthPresenter.initLocation();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mAddCameraFourthPresenter.stopLocation();
        super.onDestroy();
        if (addFireZjq.ifShow()) {
            addFireZjq.closePopWindow();
        }
        if (addFireType.ifShow()) {
            addFireType.closePopWindow();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void getLocationData(BDLocation location) {
        addFireLon.setText(location.getLongitude() + "");
        addFireAddress.setText(location.getAddrStr());
        addFireLat.setText(location.getLatitude() + "");
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
    public void addCameraResult(String msg) {
        T.showShort(mContext,msg);
        Intent intent = new Intent();
//        intent.setAction(Constants.PUSH_CAMERA_DATA);
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void errorMessage(String msg) {
        T.showShort(mContext,msg);
    }

    @Override
    public void getShopType(ArrayList<Object> shopTypes) {
        addFireType.setItemsData(shopTypes,mAddCameraFourthPresenter);
        addFireType.showPopWindow();
        addFireType.setClickable(true);
        addFireType.closeLoading();
    }

    @Override
    public void getShopTypeFail(String msg) {
        T.showShort(mContext, msg);
        addFireType.setClickable(true);
        addFireType.closeLoading();
    }

    @Override
    public void getAreaType(ArrayList<Object> shopTypes) {
        addFireZjq.setItemsData(shopTypes,mAddCameraFourthPresenter);
        addFireZjq.showPopWindow();
        addFireZjq.setClickable(true);
        addFireZjq.closeLoading();
    }

    @Override
    public void getAreaTypeFail(String msg) {
        T.showShort(mContext, msg);
        addFireZjq.setClickable(true);
        addFireZjq.closeLoading();
    }

    @Override
    public void getChoiceArea(Area area) {
        mArea = area;
    }

    @Override
    public void getChoiceShop(ShopType shopType) {
        mShopType = shopType;
    }

}
