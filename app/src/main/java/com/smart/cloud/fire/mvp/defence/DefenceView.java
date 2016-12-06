package com.smart.cloud.fire.mvp.defence;

/**
 * Created by Administrator on 2016/12/6.
 */
public interface DefenceView {
    void showLoading();
    void hideLoading();
    void studyErrorResult(String msg);
    void studySuccessResult(String msg);
}
