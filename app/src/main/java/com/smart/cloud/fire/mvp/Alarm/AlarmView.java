package com.smart.cloud.fire.mvp.Alarm;

import com.smart.cloud.fire.global.AlarmCameraInfo;

/**
 * Created by Administrator on 2016/9/27.
 */
public interface AlarmView {
    void getAlarmCameraResult(AlarmCameraInfo.CameraBean cameraBean);
    void ackResult();
}
