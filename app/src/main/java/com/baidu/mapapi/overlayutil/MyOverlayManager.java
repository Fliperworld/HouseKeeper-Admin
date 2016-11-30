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
import com.smart.cloud.fire.mvp.fragment.MapFragment.Camera;
import com.smart.cloud.fire.mvp.fragment.MapFragment.MapFragmentPresenter;
import com.smart.cloud.fire.mvp.fragment.MapFragment.Smoke;

import java.util.ArrayList;
import java.util.List;

public class MyOverlayManager extends OverlayManager {
    private static List<Smoke> mapNormalSmoke;
    private MapFragmentPresenter mMapFragmentPresenter;
    private List<BitmapDescriptor> viewList;

    public  MyOverlayManager(){
    }

    public void init(BaiduMap baiduMap,List<Smoke> mapNormalSmoke, MapFragmentPresenter mMapFragmentPresenter,List<BitmapDescriptor> viewList){
        initBaiduMap(baiduMap);
        this.mapNormalSmoke = mapNormalSmoke;
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
        if(mapNormalSmoke!=null&&mapNormalSmoke.size()>0){
            ArrayList<BitmapDescriptor> giflist = new ArrayList<>();
            giflist.add(viewList.get(2));
            giflist.add(viewList.get(1));
            ArrayList<BitmapDescriptor> giflistRQ = new ArrayList<>();
            giflistRQ.add(viewList.get(0));
            giflistRQ.add(viewList.get(1));
            ArrayList<BitmapDescriptor> giflist2 = new ArrayList<>();
            giflist2.add(viewList.get(3));
            giflist2.add(viewList.get(4));
            for (Smoke smoke : mapNormalSmoke) {
                Camera mCamera = smoke.getCamera();
                int alarmState = smoke.getIfDealAlarm();
                if(mCamera!=null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mNormalSmoke",mCamera);
                    double latitude = Double.parseDouble(mCamera.getLatitude());
                    double longitude = Double.parseDouble(mCamera.getLongitude());
                    LatLng latLng = new LatLng(latitude, longitude);
                    if(alarmState==0){
                        overlayOptionses.add(new MarkerOptions().position(latLng).icons(giflist2).extraInfo(bundle)
                                .zIndex(0).period(10)
                                .animateType(MarkerOptions.MarkerAnimateType.drop));
                    }else{
                        overlayOptionses.add(new MarkerOptions().position(latLng).icon(viewList.get(3)).extraInfo(bundle)
                                .zIndex(0).draggable(true).perspective(true)
                                .animateType(MarkerOptions.MarkerAnimateType.drop));
                    }

                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mNormalSmoke",smoke);
                    double latitude = Double.parseDouble(smoke.getLatitude());
                    double longitude = Double.parseDouble(smoke.getLongitude());
                    LatLng l = new LatLng(latitude, longitude);
                    int devType = smoke.getDeviceType();
                    if(devType==1){
                        if(alarmState==0){
                            overlayOptionses.add(new MarkerOptions().position(l).icons(giflist).extraInfo(bundle)
                                    .zIndex(0).period(10)
                                    .animateType(MarkerOptions.MarkerAnimateType.drop));
                        }else{
                            overlayOptionses.add(new MarkerOptions().position(l).icon(viewList.get(0)).extraInfo(bundle)
                                    .zIndex(0).draggable(true).perspective(true)
                                    .animateType(MarkerOptions.MarkerAnimateType.drop));
                        }
                    }else{
                        if(alarmState==0){
                            overlayOptionses.add(new MarkerOptions().position(l).icons(giflistRQ).extraInfo(bundle)
                                    .zIndex(0).period(10)
                                    .animateType(MarkerOptions.MarkerAnimateType.drop));
                        }else{
                            overlayOptionses.add(new MarkerOptions().position(l).icon(viewList.get(2)).extraInfo(bundle)
                                    .zIndex(0).draggable(true).perspective(true)
                                    .animateType(MarkerOptions.MarkerAnimateType.drop));
                        }
                    }

                }
            }
        }
        return overlayOptionses;
    }


}

