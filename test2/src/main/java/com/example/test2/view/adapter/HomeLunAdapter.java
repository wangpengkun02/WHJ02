package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.model.bean.HomeBean;
import com.example.test2.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图适配器
 * author:Created by WangZhiQiang on 2018/4/27.
 */
public class HomeLunAdapter extends PagerAdapter {
    private Context context;
    public HomeLunAdapter(Context context) {
        this.context = context;
    }

    private List<HomeBean.DataBean> list=new ArrayList<>();

    public void setData(List<HomeBean.DataBean> lista){
        list.clear();
        list.addAll(lista);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        if (list.size()>0){
            FrescoUtil.setJianJin(list.get(position % list.size()).getIcon(),simpleDraweeView);
        }
        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
