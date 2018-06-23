package com.example.test2.presenter;

import com.example.test2.model.bean.HomeBean;
import com.example.test2.model.bean.HomeJiuBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IHomeView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class HomePresenter extends BasePresenter<IHomeView>{

    private RetrofitInterface retrofitInterface;

    public HomePresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(final int i) {
        switch (i){
            case 1:
                Observable<HomeBean> homeBean = retrofitInterface.getHomeBean();
                homeBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HomeBean>() {
                            @Override
                            public void accept(HomeBean homeBean) throws Exception {
                                getView().onSuccess(homeBean,i);
                            }
                        });
                break;
            case 2:
                Observable<HomeJiuBean> homeJiuBean = retrofitInterface.getHomeJiuBean();
                homeJiuBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<HomeJiuBean>() {
                            @Override
                            public void accept(HomeJiuBean homeJiuBean) throws Exception {
                                getView().onSuccess(homeJiuBean,i);
                            }
                        });
                break;
        }
    }
}
