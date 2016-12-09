package com.smart.cloud.fire.mvp.fragment.MapFragment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Camera {

    /**
     * camera : [{"cameraAddress":"????????????????564?","cameraId":"3121164","cameraName":"Opop","cameraPwd":"123456u","ifDealAlarm":0,"latitude":"23.161798","longitude":"113.350428","principal1":"??","principal1Phone":"123456789","principal2":"??","principal2Phone":"123456","time":"1"},{"cameraAddress":"????????????????548?","cameraId":"3121667","cameraName":"???3121667","cameraPwd":"123","ifDealAlarm":1,"latitude":"23.151675","longitude":"113.393272","principal1":"??1","principal1Phone":"13428282520","principal2":"???","principal2Phone":"13428282520","time":"1"},{"cameraAddress":"????????????????564?","cameraId":"3121668","cameraName":"???3121668","cameraPwd":"123","ifDealAlarm":1,"latitude":"23.131798","longitude":"113.350428","principal1":"??","principal1Phone":"123456789","principal2":"??","principal2Phone":"123456","time":"1"},{"cameraAddress":"0","cameraId":"zhenweihao","cameraName":"1","cameraPwd":"3","ifDealAlarm":1,"latitude":"0","longitude":"0","principal1":"0","principal1Phone":"0","principal2":"0","principal2Phone":"0","time":"2016-11-14 18:07:53"}]
     * error : 管理员获取摄像头详细信息成功
     * errorCode : 0
     */

    private String error;
    private int errorCode;
    /**
     * cameraAddress : ????????????????564?
     * cameraId : 3121164
     * cameraName : Opop
     * cameraPwd : 123456u
     * ifDealAlarm : 0
     * latitude : 23.161798
     * longitude : 113.350428
     * principal1 : ??
     * principal1Phone : 123456789
     * principal2 : ??
     * principal2Phone : 123456
     * time : 1
     */

    private List<CameraBean> camera;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<CameraBean> getCamera() {
        return camera;
    }

    public void setCamera(List<CameraBean> camera) {
        this.camera = camera;
    }

    public static class CameraBean implements Serializable {

        /**
         * areaName : 湖南永州区域
         * cameraAddress : 中国广东省广州市天河区黄埔大道西540号
         * cameraId : 3121638
         * cameraName : 呵呵5
         * cameraPwd : 123
         * ifDealAlarm : 0
         * latitude : 23.131675
         * longitude : 113.350911
         * placeType :
         * principal1 : 李四
         * principal1Phone : 12548548545
         * principal2 : 卡油
         * principal2Phone : 12545
         * time : 1
         */

        private String areaName;
        private String cameraAddress;
        private String cameraId;
        private String cameraName;
        private String cameraPwd;
        private int ifDealAlarm;
        private String latitude;
        private String longitude;
        private String placeType;
        private String principal1;
        private String principal1Phone;
        private String principal2;
        private String principal2Phone;
        private String time;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCameraAddress() {
            return cameraAddress;
        }

        public void setCameraAddress(String cameraAddress) {
            this.cameraAddress = cameraAddress;
        }

        public String getCameraId() {
            return cameraId;
        }

        public void setCameraId(String cameraId) {
            this.cameraId = cameraId;
        }

        public String getCameraName() {
            return cameraName;
        }

        public void setCameraName(String cameraName) {
            this.cameraName = cameraName;
        }

        public String getCameraPwd() {
            return cameraPwd;
        }

        public void setCameraPwd(String cameraPwd) {
            this.cameraPwd = cameraPwd;
        }

        public int getIfDealAlarm() {
            return ifDealAlarm;
        }

        public void setIfDealAlarm(int ifDealAlarm) {
            this.ifDealAlarm = ifDealAlarm;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
