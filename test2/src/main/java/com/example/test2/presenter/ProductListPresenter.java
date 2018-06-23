package com.example.test2.presenter;

import com.example.test2.model.bean.ProductListBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IProductListView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/16.
 */
public class ProductListPresenter extends BasePresenter<IProductListView>{
    private RetrofitInterface retrofitInterface;

    public ProductListPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(String pscid) {
        Observable<ProductListBean> productListBean = retrofitInterface.getProductListBean(pscid);
        productListBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductListBean>() {
                    @Override
                    public void accept(ProductListBean productListBean) throws Exception {
                        getView().onSuccess(productListBean);
                    }
                });
    }
}
