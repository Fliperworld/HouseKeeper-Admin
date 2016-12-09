package com.smart.cloud.fire.mvp.Alarm;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ReceiveCallAlarmBean implements Serializable{

    /**
     * address : 中国广东省广州市天河区黄埔大道西540号
     * alarmSerialNumber : 2016-12-07 17:26:19:079--05501386
     * alarmTime : 2016-12-07 17:26:19
     * areaName : 湖南永州区域
     * callerId : 05501386
     * callerName : iOS
     * cameraId : 3121638
     * info : 112
     * latitude : 23.131675
     * longitude : 113.350911
     */

    private String address;
    private String alarmSerialNumber;
    private String alarmTime;
    private String areaName;
    private String callerId;
    private String callerName;
    private String cameraId;
    private String info;
    private String latitude;
    private String longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlarmSerialNumber() {
        return alarmSerialNumber;
    }

    public void setAlarmSerialNumber(String alarmSerialNumber) {
        this.alarmSerialNumber = alarmSerialNumber;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
