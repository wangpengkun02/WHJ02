package com.example.test2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.presenter.BasePresenter;
import com.example.test2.view.interfaces.IBaseView;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    private P p;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setChildContentView(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        p=initPresenter();
        if (p != null) {
            p.attachView(this);
        }else {
            try {
                throw new Exception("少年 prenter 没有设置 请在您的Activity 创建 presenter");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initData();
    }

    public P getPresenter() {
        return p;
    }

    abstract void initView(View view);
    abstract void initData();
    abstract P initPresenter();
    abstract int setChildContentView();
}
