package com.example.test2.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.test2.R;
import com.example.test2.model.bean.HomeBean;
import com.example.test2.model.bean.HomeJiuBean;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.presenter.HomePresenter;
import com.example.test2.view.activity.SearchActivity;
import com.example.test2.view.adapter.HomeJiuAdapter;
import com.example.test2.view.adapter.HomeLieAdapter;
import com.example.test2.view.adapter.HomeLunAdapter;
import com.example.test2.view.interfaces.IHomeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class FragmentHome extends BaseFragment<HomePresenter> implements IHomeView{

    private SwipeRefreshLayout srl_home;
    private ViewPager vp_home_lun;
    private RecyclerView rv_home_jiu;
    private RecyclerView rv_home_lie;
    private EditText et_home;
    private ImageView iv_home_sao;
    private NestedScrollView nsv_home;

    private HomeBean homeBean=new HomeBean();
    private HomeJiuBean homeJiuBean=new HomeJiuBean();
    private int time=0;
    private boolean isLoad=false;
    private HomeLunAdapter lunAdapter;
    private HomeJiuAdapter jiuAdapter;
    private HomeLieAdapter lieAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //设置轮播图数据
                    lunAdapter.setData(homeBean.getData());
                    //设置商品列表数据
                    if (isLoad){
                        lieAdapter.setData(homeBean.getTuijian().getList());
                    }else {
                        lieAdapter.setDataClear(homeBean.getTuijian().getList());
                    }
                    time=vp_home_lun.getCurrentItem();
                    //开始轮播
                    handler.sendEmptyMessage(1);
                    break;
                case 1:
                    time++;
                    vp_home_lun.setCurrentItem(time);
                    handler.sendEmptyMessageDelayed(1,2000);
                    break;
                case 2:
                    //设置九宫格数据
                    jiuAdapter.setData(homeJiuBean.getData());
                    srl_home.setRefreshing(false);//刷新视图消失
                    break;
            }
        }
    };

    @Override
    void initView(View view) {
        srl_home = view.findViewById(R.id.srl_home);
        vp_home_lun = view.findViewById(R.id.vp_home_lun);
        rv_home_jiu = view.findViewById(R.id.rv_home_jiu);
        rv_home_lie = view.findViewById(R.id.rv_home_lie);
        et_home = view.findViewById(R.id.et_home);
        iv_home_sao = view.findViewById(R.id.iv_home_sao);
        nsv_home = view.findViewById(R.id.nsv_home);
//        vp_jiu = view.findViewById(R.id.vp_jiu);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        if (event.isClickHome()){
            Log.e("myMessage" , "首页点击:"+event.isClickHome());
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    void initData() {
        //轮播图适配器
        lunAdapter = new HomeLunAdapter(getActivity());
        vp_home_lun.setAdapter(lunAdapter);
        //九宫格适配器
        jiuAdapter = new HomeJiuAdapter(getActivity());
        rv_home_jiu.setAdapter(jiuAdapter);
        //商品列表适配器
        lieAdapter = new HomeLieAdapter(getActivity());
        rv_home_lie.setAdapter(lieAdapter);
        //九宫格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false);
        rv_home_jiu.setLayoutManager(gridLayoutManager);
        //商品列表布局管理器
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_home_lie.setLayoutManager(staggeredGridLayoutManager);
        EventBus.getDefault().register(this);
        //请求--轮播图+京东秒杀+最底部的为你推荐
        getPresenter().getDataFromServer(1);
        //下拉刷新监听
        srl_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoad=false;
                handler.removeCallbacksAndMessages(null);
                getPresenter().getDataFromServer(1);
            }
        });
        //上拉加载

        //搜索
        et_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getActivity());
            }
        });
        //扫一扫
        iv_home_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(getActivity(), WeChatCaptureActivity.class);
               // startActivity(intent);
            }
        });
    }

    @Override
    HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.fragment_home;
    }
    private boolean one=true;
    @Override
    public void onSuccess(Object success, int i) {
        if (i==1){
            //轮播图+京东秒杀+最底部的为你推荐--数据
            homeBean= (HomeBean) success;
            handler.sendEmptyMessage(0);
            //请求--九宫格
            getPresenter().getDataFromServer(2);
        }else {
            //九宫格--数据
            homeJiuBean= (HomeJiuBean) success;
            handler.sendEmptyMessage(2);
            if (one){
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setSuccessHome(true);
                EventBus.getDefault().postSticky(messageEvent);
                one=false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
