package com.example.test2.presenter;

import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IMainView;

import java.util.HashMap;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class MainPresenter extends BasePresenter<IMainView>{

    private RetrofitInterface retrofitInterface;

    public MainPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(String path,HashMap<String, String> map) {

    }
}
