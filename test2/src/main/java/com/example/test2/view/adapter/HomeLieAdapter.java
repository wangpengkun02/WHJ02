package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.HomeBean;
import com.example.test2.utils.FrescoUtil;
import com.example.test2.view.activity.ProductDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表适配器
 * author:Created by WangZhiQiang on 2018/4/21.
 */
public class HomeLieAdapter extends RecyclerView.Adapter {
    private Context context;

    public HomeLieAdapter(Context context) {
        this.context = context;
    }

    private List<HomeBean.TuijianBean.ListBean> list=new ArrayList<>();

    public void setDataClear(List<HomeBean.TuijianBean.ListBean> results_list){
        list.clear();
        list.addAll(results_list);
        notifyDataSetChanged();
    }

    public void setData(List<HomeBean.TuijianBean.ListBean> results_list){
        list.addAll(results_list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.home_lie_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;
        String images = list.get(position).getImages();
        String s=images;
        int i = images.indexOf("|");
        if (i!=-1){
            s = images.substring(0, i);
        }

        FrescoUtil.setJianJin(s,viewHolder.sdv_home_lie);
        viewHolder.tv_home_lie.setText(list.get(position).getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsActivity.start(context,list.get(position).getPid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView sdv_home_lie;
        private TextView tv_home_lie;

        public MyViewHolder(View itemView) {
            super(itemView);
            sdv_home_lie = itemView.findViewById(R.id.sdv_home_lie);
            tv_home_lie = itemView.findViewById(R.id.tv_home_lie);
        }
    }
}
