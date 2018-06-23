package com.example.test2.presenter;

import com.example.test2.model.bean.AddAddrBean;
import com.example.test2.model.bean.AddrsBean;
import com.example.test2.model.bean.SetAddrBean;
import com.example.test2.model.bean.UpdateAddrBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IAddrsView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/29.
 */
public class AddrsPresenter extends BasePresenter<IAddrsView>{
    private RetrofitInterface retrofitInterface;

    public AddrsPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }
    public void getDataFromServer(String uid) {
        Observable<AddrsBean> ordersBean = retrofitInterface.getAddrsBean(uid);
        ordersBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddrsBean>() {
                    @Override
                    public void accept(AddrsBean addrsBean) throws Exception {
                        getView().onSuccess(addrsBean,1);
                    }
                });
    }
    public void setAddr(HashMap<String, String> map) {
        Observable<SetAddrBean> setAddrBean = retrofitInterface.getSetAddrBean(map);
        setAddrBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SetAddrBean>() {
                    @Override
                    public void accept(SetAddrBean setAddrBean) throws Exception {
                        getView().onSuccess(setAddrBean,2);
                    }
                });
    }
    public void addAddr(HashMap<String, String> map) {
        Observable<AddAddrBean> addAddrBean = retrofitInterface.getAddAddrBean(map);
        addAddrBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddAddrBean>() {
                    @Override
                    public void accept(AddAddrBean addAddrBean) throws Exception {
                        getView().onSuccess(addAddrBean,3);
                    }
                });
    }
    public void UpdateAddr(HashMap<String, String> map) {
        Observable<UpdateAddrBean> updateAddrBean = retrofitInterface.getUpdateAddrBean(map);
        updateAddrBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateAddrBean>() {
                    @Override
                    public void accept(UpdateAddrBean updateAddrBean) throws Exception {
                        getView().onSuccess(updateAddrBean,4);
                    }
                });
    }
}
