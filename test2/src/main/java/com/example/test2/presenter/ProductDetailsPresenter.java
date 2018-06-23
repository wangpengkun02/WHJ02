package com.example.test2.presenter;

import com.example.test2.model.bean.AddCartBean;
import com.example.test2.model.bean.ProductDetailsBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IProductDetailsView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/5/16.
 */
public class ProductDetailsPresenter extends BasePresenter<IProductDetailsView>{
    private RetrofitInterface retrofitInterface;

    public ProductDetailsPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map, final int i) {
        switch (i){
            case 1:
                Observable<ProductDetailsBean> productDetailsBean = retrofitInterface.getProductDetailsBean(map);
                productDetailsBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ProductDetailsBean>() {
                            @Override
                            public void accept(ProductDetailsBean productDetailsBean) throws Exception {
                                getView().onSuccess(productDetailsBean,i);
                            }
                        });
                break;
            case 2:
                Observable<AddCartBean> addCartBean = retrofitInterface.getAddCartBean(map);
                addCartBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AddCartBean>() {
                            @Override
                            public void accept(AddCartBean addCartBean) throws Exception {
                                getView().onSuccess(addCartBean,i);
                            }
                        });
                break;
        }
    }
}
