package com.smart.cloud.fire.global;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class CameraMap {

    /**
     * camera : [{"cameraId":"3121164","ifDealAlarm":0,"latitude":"23.161798","longitude":"113.350428"},{"cameraId":"3121667","ifDealAlarm":1,"latitude":"23.151675","longitude":"113.393272"},{"cameraId":"3121668","ifDealAlarm":1,"latitude":"23.131798","longitude":"113.350428"},{"cameraId":"zhenweihao","ifDealAlarm":1,"latitude":"0","longitude":"0"}]
     * error : 管理员获取摄像头成功
     * errorCode : 0
     */

    private String error;
    private int errorCode;
    /**
     * cameraId : 3121164
     * ifDealAlarm : 0
     * latitude : 23.161798
     * longitude : 113.350428
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
        private String cameraId;
        private int ifDealAlarm;
        private String latitude;
        private String longitude;

        public String getCameraId() {
            return cameraId;
        }

        public void setCameraId(String cameraId) {
            this.cameraId = cameraId;
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
    }
}
