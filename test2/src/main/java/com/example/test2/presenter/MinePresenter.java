package com.example.test2.presenter;

import android.util.Log;

import com.example.test2.model.bean.NickNameBean;
import com.example.test2.model.bean.PicturesBean;
import com.example.test2.model.bean.UserInfoBean;
import com.example.test2.model.callback.RetrofitInterface;
import com.example.test2.model.http.RetrofitUtil;
import com.example.test2.view.interfaces.IMineView;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class MinePresenter extends BasePresenter<IMineView>{

    private RetrofitInterface retrofitInterface;

    public MinePresenter() {
        retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
    }

    public void getDataFromServer(HashMap<String, String> map, final int flag) {
        switch (flag){
            case 1:
                Observable<UserInfoBean> userInfoBean = retrofitInterface.getUserInfoBean(map);
                userInfoBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<UserInfoBean>() {
                            @Override
                            public void accept(UserInfoBean userInfoBean) throws Exception {
                                getView().onSuccess(userInfoBean,flag);
                            }
                        });
                break;
            case 2:
                Observable<NickNameBean> nickNameBean = retrofitInterface.getNickNameBean(map);
                nickNameBean.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<NickNameBean>() {
                            @Override
                            public void accept(NickNameBean nickNameBean) throws Exception {
                                getView().onSuccess(nickNameBean,flag);
                            }
                        });
                break;
        }
    }

    public void uploadPic(String uid,String path) {
        File file = new File(path);

        RequestBody uidBody = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Observable<PicturesBean> picturesBean = retrofitInterface.getPicturesBean(uidBody,filePart);
        picturesBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PicturesBean>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(PicturesBean picturesBean) {
                        getView().onSuccess(picturesBean,3);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("myMessage", e.toString());
                    }
                    @Override
                    public void onComplete() { }
                });
    }

}
