package com.example.test2.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.ClassifyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/5/8.
 */
public class ClassifyAdapter extends RecyclerView.Adapter {
    private Context context;
    private CallBackViewClick callBackViewClick;

    public ClassifyAdapter(Context context) {
        this.context = context;
    }

    private List<ClassifyBean.DataBean> list =new ArrayList<>();

    public void setData(List<ClassifyBean.DataBean> lista){
        list.clear();
        list.addAll(lista);
        notifyDataSetChanged();
    }

    public void getViewClick(CallBackViewClick callBackViewClick){
        this.callBackViewClick=callBackViewClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.classify_fen_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;
        viewHolder.tv_classify_fen.setText(list.get(position).getName());
        viewHolder.tv_classify_fen.setTextColor(list.get(position).getColor());
        if (callBackViewClick!=null){
            callBackViewClick.onViewClick(viewHolder.tv_classify_fen,position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_classify_fen;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_classify_fen = itemView.findViewById(R.id.tv_classify_fen);
        }
    }

    //接口回调
    public interface CallBackViewClick {
        void onViewClick(TextView view, int position);
    }
}
