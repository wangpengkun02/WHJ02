package com.example.test2.presenter;

import com.example.test2.model.bean.ClassifyBean;
import com.example.test2.model.bean.ClassifyFenBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IClassifyView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class ClassifyPresenter extends BasePresenter<IClassifyView>{

    private RetrofitInterface retrofitInterface;

    public ClassifyPresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer() {
        Observable<ClassifyBean> classifyBean = retrofitInterface.getClassifyBean();
        classifyBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClassifyBean>() {
                    @Override
                    public void accept(ClassifyBean classifyBean) throws Exception {
                        getView().onSuccess(classifyBean,1);
                    }
                });
    }
    public void getFenDataFromServer(String cid) {
        Observable<ClassifyFenBean> classifyFenBean = retrofitInterface.getClassifyFenBean(cid);
        classifyFenBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClassifyFenBean>() {
                    @Override
                    public void accept(ClassifyFenBean classifyFenBean) throws Exception {
                        getView().onSuccess(classifyFenBean,2);
                    }
                });
    }
}
