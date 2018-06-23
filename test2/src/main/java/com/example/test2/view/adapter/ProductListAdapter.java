package com.example.test2.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.ProductListBean;
import com.example.test2.utils.FrescoUtil;
import com.example.test2.view.activity.ProductDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/5/17.
 */
public class ProductListAdapter extends RecyclerView.Adapter{
    private Context context;

    public ProductListAdapter(Context context) {
        this.context = context;
    }

    private List<ProductListBean.DataBean> list=new ArrayList<>();

    public void setData(List<ProductListBean.DataBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;
        String images = list.get(position).getImages();
        String s=images;
        int i = images.indexOf("|");
        if (i!=-1){
            s = images.substring(0, i);
        }
        FrescoUtil.setJianJin(s,viewHolder.sdv_product_list_item);
        viewHolder.tv_product_list_item1.setText(list.get(position).getTitle());
        viewHolder.tv_product_list_item2.setText("原价：￥"+list.get(position).getPrice());
        viewHolder.tv_product_list_item2.setPaintFlags(viewHolder.tv_product_list_item2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tv_product_list_item3.setText("优惠价：￥"+list.get(position).getBargainPrice());
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

        private SimpleDraweeView sdv_product_list_item;
        private TextView tv_product_list_item1;
        private TextView tv_product_list_item2;
        private TextView tv_product_list_item3;

        public MyViewHolder(View itemView) {
            super(itemView);
            sdv_product_list_item = itemView.findViewById(R.id.sdv_product_list_item);
            tv_product_list_item1 = itemView.findViewById(R.id.tv_product_list_item1);
            tv_product_list_item2 = itemView.findViewById(R.id.tv_product_list_item2);
            tv_product_list_item3 = itemView.findViewById(R.id.tv_product_list_item3);
        }
    }
}
