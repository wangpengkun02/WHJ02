package com.example.test2.presenter;

import com.example.test2.model.bean.OrdersBean;
import com.example.test2.model.bean.UpdateOrderBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IOrdersView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/29.
 */
public class OrdersPresenter extends BasePresenter<IOrdersView>{
    private RetrofitInterface retrofitInterface;

    public OrdersPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }
    public void getDataFromServer(String uid) {
        Observable<OrdersBean> ordersBean = retrofitInterface.getOrdersBean(uid);
        ordersBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrdersBean>() {
                    @Override
                    public void accept(OrdersBean ordersBean) throws Exception {
                        getView().onSuccess(ordersBean,1);
                    }
                });
    }
    public void UpdateOrder(HashMap<String, String> map) {
        Observable<UpdateOrderBean> updateOrderBean = retrofitInterface.getUpdateOrderBean(map);
        updateOrderBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateOrderBean>() {
                    @Override
                    public void accept(UpdateOrderBean updateOrderBean) throws Exception {
                        getView().onSuccess(updateOrderBean,2);
                    }
                });
    }
}
