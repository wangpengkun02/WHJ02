package com.example.test2.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.OrdersBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/5/17.
 */
public class OrdersAdapter extends RecyclerView.Adapter{
    private Context context;
    private CallBackViewClick callBackViewClick;

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    private List<OrdersBean.DataBean> list=new ArrayList<>();

    public void setData(List<OrdersBean.DataBean> list){
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
        View inflate = LayoutInflater.from(context).inflate(R.layout.orders_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder= (MyViewHolder) holder;

        viewHolder.tv_orders1.setText("标题："+list.get(position).getTitle());
        viewHolder.tv_orders2.setText("时间："+list.get(position).getCreatetime());
        viewHolder.tv_orders3.setText("价钱：￥"+list.get(position).getPrice());
        String[] items={"待支付","已支付","已取消"};
        viewHolder.tv_orders4.setText("状态："+items[list.get(position).getStatus()]);
        callBackViewClick.onViewClick(viewHolder.itemView,list.get(position).getOrderid(),list.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_orders1;
        private TextView tv_orders2;
        private TextView tv_orders3;
        private TextView tv_orders4;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_orders1 = itemView.findViewById(R.id.tv_orders1);
            tv_orders2 = itemView.findViewById(R.id.tv_orders2);
            tv_orders3 = itemView.findViewById(R.id.tv_orders3);
            tv_orders4 = itemView.findViewById(R.id.tv_orders4);
        }
    }

    //接口回调
    public interface CallBackViewClick {
        void onViewClick(View view, int orderId,int status);
    }
}
