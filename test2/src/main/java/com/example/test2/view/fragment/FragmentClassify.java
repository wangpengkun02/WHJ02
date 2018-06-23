package com.example.test2.view.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.ClassifyBean;
import com.example.test2.model.bean.ClassifyFenBean;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.presenter.ClassifyPresenter;
import com.example.test2.view.adapter.ClassifyAdapter;
import com.example.test2.view.adapter.ClassifyFenAdapter;
import com.example.test2.view.interfaces.IClassifyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class FragmentClassify extends BaseFragment<ClassifyPresenter> implements IClassifyView{

    private ClassifyBean classifyBean=new ClassifyBean();
    private ClassifyFenBean classifyFenBean=new ClassifyFenBean();
    private RecyclerView rv_classify_fen;
    private RecyclerView rv_classify_fen1;
    private ClassifyAdapter classifyAdapter;
    private ClassifyFenAdapter classifyFenAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    classifyAdapter.setData(classifyBean.getData());
                    break;
                case 1:
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setSuccessAll(true);
                    EventBus.getDefault().postSticky(messageEvent);
                    classifyFenAdapter.setData(classifyFenBean.getData());
                    break;
            }
        }
    };


    @Override
    void initView(View view) {
        rv_classify_fen = view.findViewById(R.id.rv_classify_fen);
        rv_classify_fen1 = view.findViewById(R.id.rv_classify_fen1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        if (event.isSuccessHome()){
            Log.e("myMessage" , "isSuccessHome:"+event.isSuccessHome());
            getPresenter().getDataFromServer();
            //移除粘性事件
            EventBus.getDefault().removeStickyEvent(event);
        }
        if (event.isClickClassify()){
            Log.e("myMessage" , "分类点击:"+event.isClickClassify());
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    void initData() {
        //设置适配器
        classifyAdapter = new ClassifyAdapter(getActivity());
        rv_classify_fen.setAdapter(classifyAdapter);
        classifyFenAdapter = new ClassifyFenAdapter(getActivity());
        rv_classify_fen1.setAdapter(classifyFenAdapter);
        EventBus.getDefault().register(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_classify_fen.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_classify_fen1.setLayoutManager(linearLayoutManager1);
        //点击分类
        classifyAdapter.getViewClick(new ClassifyAdapter.CallBackViewClick() {
            @Override
            public void onViewClick(final TextView view, final int position) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //切换按钮状态
                        for (int i = 0; i < classifyBean.getData().size(); i++) {
                            classifyBean.getData().get(i).setColor(Color.BLACK);
                        }
                        classifyBean.getData().get(position).setColor(Color.RED);
                        //根据点击的一级分类列表 请求二级分类列表数据
                        getPresenter().getFenDataFromServer(classifyBean.getData().get(position).getCid()+"");
                        classifyAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    ClassifyPresenter initPresenter() {
        return new ClassifyPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.fragment_classify;
    }
    private boolean one=true;
    @Override
    public void onSuccess(Object success, int flag) {
        switch (flag){
            case 1:
                classifyBean= (ClassifyBean) success;
                //初始化按钮状态
                for (int i = 0; i < classifyBean.getData().size(); i++) {
                    if (i==0){
                        classifyBean.getData().get(i).setColor(Color.RED);
                        continue;
                    }
                    classifyBean.getData().get(i).setColor(Color.BLACK);
                }
                //刷新视图
                handler.sendEmptyMessage(0);
                //请求二级分类列表
                getPresenter().getFenDataFromServer(classifyBean.getData().get(0).getCid()+"");
                break;
            case 2:
                //二级分类列表数据
                classifyFenBean = (ClassifyFenBean) success;
                handler.sendEmptyMessage(1);
                if (one){
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setSuccessClassify(true);
                    EventBus.getDefault().postSticky(messageEvent);
                    one=false;
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
