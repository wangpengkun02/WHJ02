package com.example.test2.presenter;

import android.util.Log;

import com.example.test2.model.bean.CartBean;
import com.example.test2.model.bean.CreateOrderBean;
import com.example.test2.model.bean.DefaultAddrBean;
import com.example.test2.model.bean.DeleteCartBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.ICartView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class CartPresenter extends BasePresenter<ICartView>{

    private RetrofitInterface retrofitInterface;

    public CartPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map, final int flag) {
        switch (flag){
            case 1:
                Observable<CartBean> cartBean = retrofitInterface.getCartBean(map);
                cartBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CartBean>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext(CartBean cartBean) {
                                getView().onSuccess(cartBean,flag);
                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.e("myMessage", "onError: "+e.toString());
                            }
                            @Override
                            public void onComplete() { }
                        });
                break;
            case 2:
                Observable<DeleteCartBean> deleteCartBean = retrofitInterface.getDeleteCartBean(map);
                deleteCartBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DeleteCartBean>() {
                            @Override
                            public void accept(DeleteCartBean deleteCartBean) throws Exception {
                                getView().onSuccess(deleteCartBean,flag);
                            }
                        });
                break;
            case 3:
                Observable<CreateOrderBean> createOrderBean = retrofitInterface.getCreateOrderBean(map);
                createOrderBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<CreateOrderBean>() {
                            @Override
                            public void accept(CreateOrderBean createOrderBean) throws Exception {
                                getView().onSuccess(createOrderBean,flag);
                            }
                        });
                break;
        }
    }

    public void defaultAddr(String uid){
        Observable<DefaultAddrBean> defaultAddrBean = retrofitInterface.getDefaultAddrBean(uid);
        defaultAddrBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DefaultAddrBean>() {
                    @Override
                    public void accept(DefaultAddrBean defaultAddrBean) throws Exception {
                        getView().onSuccess(defaultAddrBean,4);
                    }
                });
    }
}
