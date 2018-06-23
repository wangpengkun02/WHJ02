package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.ClassifyFenBean;
import com.example.test2.utils.FrescoUtil;
import com.example.test2.view.activity.ProductListActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/5/8.
 */
public class ClassifyFenAdapter extends RecyclerView.Adapter {
    private Context context;

    public ClassifyFenAdapter(Context context) {
        this.context = context;
    }

    private List<ClassifyFenBean.DataBean> list =new ArrayList<>();

    public void setData(List<ClassifyFenBean.DataBean> lista){
        list.clear();
        list.addAll(lista);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.classify_fen1_item,parent,false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;

        viewHolder.tv_classify_fen1.setText(list.get(position).getName());

        ClassifyFenItemAdapter classifyFenItemAdapter = new ClassifyFenItemAdapter();
        viewHolder.rv_classify_fen1_item.setAdapter(classifyFenItemAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        viewHolder.rv_classify_fen1_item.setLayoutManager(gridLayoutManager);
        classifyFenItemAdapter.setData(list.get(position).getList());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_classify_fen1;
        private RecyclerView rv_classify_fen1_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_classify_fen1 = itemView.findViewById(R.id.tv_classify_fen1);
            rv_classify_fen1_item = itemView.findViewById(R.id.rv_classify_fen1_item);
        }
    }

    public class ClassifyFenItemAdapter extends RecyclerView.Adapter {
        private List<ClassifyFenBean.DataBean.ListBean> listItem =new ArrayList<>();

        public void setData(List<ClassifyFenBean.DataBean.ListBean> listaa){
            listItem.clear();
            listItem.addAll(listaa);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.classify_fen1_item_item,parent,false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyViewHolder viewHolder= (MyViewHolder) holder;

            FrescoUtil.setJianJin(listItem.get(position).getIcon(),viewHolder.sdv_classify_fen1_item);
            viewHolder.tv_classify_fen1_item.setText(listItem.get(position).getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductListActivity.start(context,listItem.get(position).getPscid());
                }
            });
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private SimpleDraweeView sdv_classify_fen1_item;
            private TextView tv_classify_fen1_item;

            public MyViewHolder(View itemView) {
                super(itemView);
                sdv_classify_fen1_item = itemView.findViewById(R.id.sdv_classify_fen1_item);
                tv_classify_fen1_item = itemView.findViewById(R.id.tv_classify_fen1_item);
            }
        }
    }
}
