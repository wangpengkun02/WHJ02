package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * author:Created by WangZhiQiang on 2018/5/17.
 */
public class ProductDetailsAdapter extends PagerAdapter {
    private Context context;
    String[] images;

    public ProductDetailsAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        if (images.length>0){
            FrescoUtil.setJianJin(images[position],simpleDraweeView);
        }
        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
