package com.example.test2.presenter;

import com.example.test2.model.bean.LoginBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.ILoginView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/18.
 */
public class LoginPresenter extends BasePresenter<ILoginView>{
    private RetrofitInterface retrofitInterface;

    public LoginPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map) {
        Observable<LoginBean> loginBean = retrofitInterface.getLoginBean(map);
        loginBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        getView().onSuccess(loginBean);
                    }
                });
    }
}
