package com.example.test2.view.fragment;

import android.view.View;

import com.example.test2.R;
import com.example.test2.presenter.DiscoverPresenter;
import com.example.test2.view.interfaces.IDiscoverView;

/**
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class FragmentDiscover extends BaseFragment<DiscoverPresenter> implements IDiscoverView{

    @Override
    void initView(View view) {

    }

    @Override
    void initData() {

    }

    @Override
    DiscoverPresenter initPresenter() {
        return new DiscoverPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onSuccess(String success) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
