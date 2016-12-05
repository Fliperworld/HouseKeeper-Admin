package com.smart.cloud.fire.mvp.main.view;

/**
 * Created by Administrator on 2016/9/21.
 */
public interface MainView {
    void exitBy2Click(boolean isExit);
    void showUpdateDialog(String message, String urlStr);
    void hideTitle();
    void showTitle(boolean ifAdd);
}
