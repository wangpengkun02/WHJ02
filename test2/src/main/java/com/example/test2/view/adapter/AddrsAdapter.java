package com.example.test2.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.AddrsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/5/17.
 */
public class AddrsAdapter extends RecyclerView.Adapter{
    private Context context;
    private CallBackViewClick callBackViewClick;

    public AddrsAdapter(Context context) {
        this.context = context;
    }

    private List<AddrsBean.DataBean> list=new ArrayList<>();

    public void setData(List<AddrsBean.DataBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(CallBackViewClick callBackViewClick){
        this.callBackViewClick=callBackViewClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.addrs_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;

        viewHolder.tv_addrs_item1.setText("收货地址："+list.get(position).getAddr());
        viewHolder.tv_addrs_item2.setText("收货人姓名："+list.get(position).getName());
        viewHolder.tv_addrs_item3.setText("收货人电话："+list.get(position).getMobile());
        boolean[] isChecked={false,true};
        viewHolder.rb_addrs_item.setChecked(isChecked[list.get(position).getStatus()]);
        callBackViewClick.onViewClick(viewHolder.itemView,list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_addrs_item1;
        private TextView tv_addrs_item2;
        private TextView tv_addrs_item3;
        private RadioButton rb_addrs_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_addrs_item1 = itemView.findViewById(R.id.tv_addrs_item1);
            tv_addrs_item2 = itemView.findViewById(R.id.tv_addrs_item2);
            tv_addrs_item3 = itemView.findViewById(R.id.tv_addrs_item3);
            rb_addrs_item = itemView.findViewById(R.id.rb_addrs_item);
        }
    }

    //接口回调
    public interface CallBackViewClick {
        void onViewClick(View view, AddrsBean.DataBean dataBean);
    }
}
