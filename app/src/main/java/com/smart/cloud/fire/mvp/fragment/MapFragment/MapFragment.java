package com.smart.cloud.fire.mvp.fragment.MapFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.MyOverlayManager;
import com.hrsst.housekeeper.R;
import com.smart.cloud.fire.base.ui.MvpFragment;
import com.smart.cloud.fire.data.CameraData;
import com.smart.cloud.fire.global.Area;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.Contact;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.global.ShopType;
import com.smart.cloud.fire.ui.ApMonitorActivity;
import com.smart.cloud.fire.utils.SharedPreferencesManager;
import com.smart.cloud.fire.utils.T;
import com.smart.cloud.fire.view.XCDropDownListViewMapSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/21.
 */
public class MapFragment extends MvpFragment<MapFragmentPresenter> implements MapFragmentView {

    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.lin1)
    LinearLayout lin1;
    @Bind(R.id.area_condition)
    XCDropDownListViewMapSearch areaCondition;
    @Bind(R.id.shop_type_condition)
    XCDropDownListViewMapSearch shopTypeCondition;
    private BaiduMap mBaiduMap;
    private Context mContext;
    private String userID;
    private int privilege;
    private ShopType mShopType;
    private Area mArea;
    private String areaId = "";
    private String shopTypeId = "";
    private MapFragmentPresenter mMapFragmentPresenter;
    public boolean isHideAdd = true;
    private Animation animation_out, animation_in;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container,
                false);
        ButterKnife.bind(this, view);
        mBaiduMap = mMapView.getMap();// 获得MapView
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        userID = SharedPreferencesManager.getInstance().getData(mContext,
                SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_RECENTNAME);
        privilege = MyApp.app.getPrivilege();
        mvpPresenter.getAllCamera(userID, privilege + "");
        animation_out = AnimationUtils.loadAnimation(mContext,
                R.anim.scale_amplify);
        animation_in = AnimationUtils.loadAnimation(mContext,
                R.anim.scale_narrow);
    }

    @Override
    protected MapFragmentPresenter createPresenter() {
        mMapFragmentPresenter = new MapFragmentPresenter(MapFragment.this);
        return mMapFragmentPresenter;
    }

    @Override
    public String getFragmentName() {
        return "Map";
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
        if (shopTypeCondition != null) {
            if (shopTypeCondition.ifShow()) {
                shopTypeCondition.closePopWindow();
            }
        }
        if (areaCondition != null) {
            if (areaCondition.ifShow()) {
                areaCondition.closePopWindow();
            }
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        mMapView.setVisibility(View.VISIBLE);
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.setVisibility(View.INVISIBLE);
        mMapView.onPause();
        super.onPause();
    }

    private MyOverlayManager mMyOverlayManager;

    @Override
    public void getDataSuccess(List<CameraData.CameraBean> cameraBeanList) {
        mBaiduMap.clear();
        List<BitmapDescriptor> viewList = initMark();
        if (mMyOverlayManager == null) {
            mMyOverlayManager = new MyOverlayManager();
        }
        mMyOverlayManager.init(mBaiduMap, cameraBeanList, mMapFragmentPresenter, viewList);
        mMyOverlayManager.removeFromMap();
        mBaiduMap.setOnMarkerClickListener(mMyOverlayManager);
        mMyOverlayManager.addToMap();
        mMyOverlayManager.zoomToSpan();
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMyOverlayManager.zoomToSpan();
            }
        });
    }

    private List<BitmapDescriptor> initMark() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.image_test, null);
        View view2 = LayoutInflater.from(mContext).inflate(
                R.layout.image_test2, null);
        BitmapDescriptor cameraImage = BitmapDescriptorFactory
                .fromView(view);
        BitmapDescriptor cameraImage2 = BitmapDescriptorFactory
                .fromView(view2);
        List<BitmapDescriptor> listView = new ArrayList<>();
        listView.add(cameraImage);
        listView.add(cameraImage2);
        return listView;
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
    public void getShopType(ArrayList<Object> shopTypes) {
        shopTypeCondition.setItemsData(shopTypes, mMapFragmentPresenter);
        shopTypeCondition.showPopWindow();
        shopTypeCondition.setClickable(true);
        shopTypeCondition.closeLoading();
    }

    @Override
    public void getShopTypeFail(String msg) {
        T.showShort(mContext, msg);
        shopTypeCondition.setClickable(true);
        shopTypeCondition.closeLoading();
    }

    @Override
    public void getAreaType(ArrayList<Object> shopTypes) {
        areaCondition.setItemsData(shopTypes, mMapFragmentPresenter);
        areaCondition.showPopWindow();
        areaCondition.setClickable(true);
        areaCondition.closeLoading();
    }

    @Override
    public void getAreaTypeFail(String msg) {
        T.showShort(mContext, msg);
        areaCondition.setClickable(true);
        areaCondition.closeLoading();
    }

    @Override
    public void dealAlarmMsgSuccess() {
        mvpPresenter.getAllCamera(userID, privilege + "");
    }

    @Override
    public void openCamera(Contact contact) {
        Intent monitor = new Intent();
        monitor.setClass(mContext, ApMonitorActivity.class);
        monitor.putExtra("contact", contact);
        monitor.putExtra("connectType", ConstantValues.ConnectType.P2PCONNECT);
        monitor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(monitor);
    }

    @Override
    public void getChoiceArea(Area area) {
    }

    @Override
    public void getChoiceShop(ShopType shopType) {
    }

    @OnClick({ R.id.area_condition, R.id.shop_type_condition})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_type_condition:
                if (shopTypeCondition.ifShow()) {
                    shopTypeCondition.closePopWindow();
                } else {
                    mvpPresenter.getPlaceTypeId(userID, privilege + "", 1);
                    shopTypeCondition.setClickable(false);
                    shopTypeCondition.showLoading();
                }
                break;
            case R.id.area_condition:
                if (areaCondition.ifShow()) {
                    areaCondition.closePopWindow();
                } else {
                    mvpPresenter.getPlaceTypeId(userID, privilege + "", 2);
                    areaCondition.setClickable(false);
                    areaCondition.showLoading();
                }
                break;
            default:
                break;
        }
    }


}
