package com.smart.cloud.fire.mvp.defenceList;

import com.smart.cloud.fire.data.Defence;

/**
 * Created by Administrator on 2016/12/6.
 */
public interface DefenceListView {
    void showLoading();
    void hideLoading();
    void errorMessage(String msg);
    void getDefenceArea(Defence defence);
    void getDefenceResult(String msg,Defence defence);
    void refresh(Defence defence);
    void deleteDefenceResult(Defence.DefenceBean defenceBean);
    void modifyDefenceNameResult(Defence.DefenceBean defenceBean);
}
