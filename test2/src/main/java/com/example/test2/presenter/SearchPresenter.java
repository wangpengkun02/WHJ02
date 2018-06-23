package com.example.test2.presenter;

import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IRegisterView;

import java.util.HashMap;

/**
 * author:Created by WangZhiQiang on 2018/5/23.
 */
public class SearchPresenter extends BasePresenter<IRegisterView>{
    private RetrofitInterface retrofitInterface;

    public SearchPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map) {

    }
}
