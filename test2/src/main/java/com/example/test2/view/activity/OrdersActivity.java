package com.example.test2.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.OrdersBean;
import com.example.test2.model.bean.UpdateOrderBean;
import com.example.test2.presenter.OrdersPresenter;
import com.example.test2.view.adapter.OrdersAdapter;
import com.example.test2.view.interfaces.IOrdersView;

import java.util.HashMap;

public class OrdersActivity extends BaseActivity<OrdersPresenter> implements IOrdersView{

    private OrdersBean ordersBean=new OrdersBean();
    private UpdateOrderBean updateOrderBean=new UpdateOrderBean();
    private SharedPreferences sharedPreferences;

    private RecyclerView rv_orders;
    private OrdersAdapter ordersAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                ordersAdapter.setData(ordersBean.getData());
            }else if (msg.what==1){
                Toast.makeText(OrdersActivity.this, updateOrderBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (updateOrderBean.getCode().equals("0")){
                    getPresenter().getDataFromServer(sharedPreferences.getString("uid",null));
                }
            }
        }
    };

    @Override
    void initView() {
        getParent_title().getTitle().setText("订单列表");
        rv_orders = findViewById(R.id.rv_orders);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
    }

    @Override
    void initData() {
        ordersAdapter = new OrdersAdapter(this);
        rv_orders.setAdapter(ordersAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrdersActivity.this);
        rv_orders.setLayoutManager(linearLayoutManager);
        //请求数据
        getPresenter().getDataFromServer(sharedPreferences.getString("uid",null));
        //条目点击更改订单状态
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.CallBackViewClick() {
            @Override
            public void onViewClick(View view, final int orderId, final int status) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialog(orderId,status);
                    }
                });
            }
        });
    }
    private int i;
    private void showAlertDialog(final int orderId,int status) {
        i=status;
        AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
        builder.setTitle("请选择订单状态");//标题
        String[] items={"待支付","已支付","已取消"};
        builder.setSingleChoiceItems(items, status, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i=which;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap<String, String> map = new HashMap<>();
                map.put("uid",sharedPreferences.getString("uid",null));
                map.put("status",""+i);
                map.put("orderId",""+orderId);
                getPresenter().UpdateOrder(map);
            }
        });
        builder.show();
    }

    @Override
    public void onSuccess(Object success, int i) {
        switch (i){
            case 1:
                ordersBean= (OrdersBean) success;
                if (ordersBean.getCode().equals("0")){
                    handler.sendEmptyMessage(0);
                }else {
                    Toast.makeText(OrdersActivity.this, ordersBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                updateOrderBean= (UpdateOrderBean) success;
                handler.sendEmptyMessage(1);
                break;
        }

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OrdersActivity.class);
        context.startActivity(intent);
    }

    @Override
    OrdersPresenter initPresenter() {
        return new OrdersPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_orders;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
