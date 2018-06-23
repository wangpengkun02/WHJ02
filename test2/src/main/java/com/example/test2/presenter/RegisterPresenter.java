package com.example.test2.presenter;

import com.example.test2.model.bean.RegisterBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IRegisterView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/18.
 */
public class RegisterPresenter extends BasePresenter<IRegisterView>{
    private RetrofitInterface retrofitInterface;

    public RegisterPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map) {
        Observable<RegisterBean> registerBean = retrofitInterface.getRegisterBean(map);
        registerBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterBean>() {
                    @Override
                    public void accept(RegisterBean registerBean) throws Exception {
                        getView().onSuccess(registerBean);
                    }
                });
    }
}
