package com.smart.cloud.fire.pushmessage;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/25.
 */
public class PushAlarmMsg implements Serializable{

    /**
     * address : 808
     * alarmTime : 2016-10-28 17:56:50:223
     * alarmType : 202
     * areaId : 测试区
     * camera : null
     * deviceType : 2
     * ifDealAlarm : 0
     * latitude : 23.131620
     * longitude : 113.350284
     * mac : 1D3E1730
     * name : 燃气
     * placeAddress : 广东省广州市天河区黄埔大道西550号
     * placeType : 住宅
     * principal1 :
     * principal1Phone :
     * principal2 :
     * principal2Phone :
     */

    private String address;
    private String alarmTime;
    private int alarmType;
    private String areaId;
    private Object camera;
    private int deviceType;
    private int ifDealAlarm;
    private double latitude;
    private double longitude;
    private String mac;
    private String name;
    private String placeAddress;
    private String placeType;
    private String principal1;
    private String principal1Phone;
    private String principal2;
    private String principal2Phone;
    private int alarmFamily;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Object getCamera() {
        return camera;
    }

    public void setCamera(Object camera) {
        this.camera = camera;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getIfDealAlarm() {
        return ifDealAlarm;
    }

    public void setIfDealAlarm(int ifDealAlarm) {
        this.ifDealAlarm = ifDealAlarm;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPrincipal1() {
        return principal1;
    }

    public void setPrincipal1(String principal1) {
        this.principal1 = principal1;
    }

    public String getPrincipal1Phone() {
        return principal1Phone;
    }

    public void setPrincipal1Phone(String principal1Phone) {
        this.principal1Phone = principal1Phone;
    }

    public String getPrincipal2() {
        return principal2;
    }

    public void setPrincipal2(String principal2) {
        this.principal2 = principal2;
    }

    public String getPrincipal2Phone() {
        return principal2Phone;
    }

    public void setPrincipal2Phone(String principal2Phone) {
        this.principal2Phone = principal2Phone;
    }

    public int getAlarmFamily() {
        return alarmFamily;
    }

    public void setAlarmFamily(int alarmFamily) {
        this.alarmFamily = alarmFamily;
    }
}
