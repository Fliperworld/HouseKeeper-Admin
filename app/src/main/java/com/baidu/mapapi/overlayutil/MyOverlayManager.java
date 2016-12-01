package com.baidu.mapapi.overlayutil;

/**
 * Created by Administrator on 2016/7/28.
 */

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.smart.cloud.fire.global.CameraMap;
import com.smart.cloud.fire.mvp.fragment.MapFragment.MapFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyOverlayManager extends OverlayManager {
    private List<CameraMap.CameraBean> cameraBeanList;
    private MapFragmentPresenter mMapFragmentPresenter;
    private List<BitmapDescriptor> viewList;

    public  MyOverlayManager(){
    }

    public void init(BaiduMap baiduMap, List<CameraMap.CameraBean> cameraBeanList, MapFragmentPresenter mMapFragmentPresenter, List<BitmapDescriptor> viewList){
        initBaiduMap(baiduMap);
        this.cameraBeanList = cameraBeanList;
        this.mMapFragmentPresenter = mMapFragmentPresenter;
        this.viewList = viewList;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle bundle = marker.getExtraInfo();
        mMapFragmentPresenter.getClickDev(bundle);
        return true;
    }

    @Override
    public boolean onPolylineClick(Polyline arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        // TODO Auto-generated method stub
        List<OverlayOptions> overlayOptionses = new ArrayList<>();
        if(cameraBeanList!=null&&cameraBeanList.size()>0){
            ArrayList<BitmapDescriptor> giflist2 = new ArrayList<>();
            giflist2.add(viewList.get(0));
            giflist2.add(viewList.get(1));
            for (CameraMap.CameraBean cameraBean : cameraBeanList) {
                int alarmState = cameraBean.getIfDealAlarm();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mNormalSmoke",cameraBean);
                double latitude = Double.parseDouble(cameraBean.getLatitude());
                double longitude = Double.parseDouble(cameraBean.getLongitude());
                LatLng latLng = new LatLng(latitude, longitude);
                if(alarmState==0){
                    overlayOptionses.add(new MarkerOptions().position(latLng).icons(giflist2).extraInfo(bundle)
                            .zIndex(0).period(10)
                            .animateType(MarkerOptions.MarkerAnimateType.drop));
                }else{
                    overlayOptionses.add(new MarkerOptions().position(latLng).icon(viewList.get(0)).extraInfo(bundle)
                            .zIndex(0).draggable(true).perspective(true)
                            .animateType(MarkerOptions.MarkerAnimateType.drop));
                }
            }
        }
        return overlayOptionses;
    }


}

