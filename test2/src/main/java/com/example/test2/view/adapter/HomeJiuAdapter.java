package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.HomeJiuBean;
import com.example.test2.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格适配器
 * author:Created by WangZhiQiang on 2018/4/21.
 */
public class HomeJiuAdapter extends RecyclerView.Adapter {
    private Context context;

    public HomeJiuAdapter(Context context) {
        this.context = context;
    }

    private List<HomeJiuBean.DataBean> list=new ArrayList<>();

    public void setData(List<HomeJiuBean.DataBean> results_list){
        list.clear();
        list.addAll(results_list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.home_jiu_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;

        FrescoUtil.setJianJin(list.get(position).getIcon(),viewHolder.sdv_home_jiu);
        viewHolder.tv_home_jiu.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView sdv_home_jiu;
        private TextView tv_home_jiu;

        public MyViewHolder(View itemView) {
            super(itemView);
            sdv_home_jiu = itemView.findViewById(R.id.sdv_home_jiu);
            tv_home_jiu = itemView.findViewById(R.id.tv_home_jiu);
        }
    }
}
