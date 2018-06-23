package com.example.test2.view.activity;

import com.example.test2.R;
import com.example.test2.presenter.SettingPresenter;
import com.example.test2.view.interfaces.ISettingView;

public class SettingActivity extends BaseActivity<SettingPresenter> implements ISettingView{


    @Override
    void initView() {

    }

    @Override
    void initData() {

    }

    @Override
    public void onSuccess(Object success, int flag) {

    }

    @Override
    SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
