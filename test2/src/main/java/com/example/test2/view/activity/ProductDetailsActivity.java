package com.example.test2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.AddCartBean;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.model.bean.ProductDetailsBean;
import com.example.test2.presenter.ProductDetailsPresenter;
import com.example.test2.view.adapter.ProductDetailsAdapter;
import com.example.test2.view.interfaces.IProductDetailsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class ProductDetailsActivity extends BaseActivity<ProductDetailsPresenter> implements IProductDetailsView, View.OnClickListener {
    private ViewPager vp_product_details;
    private TextView tv_product_details1;
    private TextView tv_product_details2;
    private TextView tv_product_details3;
    private TextView tv_product_details4;
    private ImageView iv_product_details;
    private Button btn_product_details;

    private int pid;
    private boolean ok;
    private String token;
    private String uid;
    private SharedPreferences sharedPreferences;

    private AddCartBean addCartBean=new AddCartBean();
    private ProductDetailsBean productDetailsBean=new ProductDetailsBean();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                setData();
            }else {
                Toast.makeText(ProductDetailsActivity.this, addCartBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    void initView() {
        vp_product_details = findViewById(R.id.vp_product_details);
        tv_product_details1 = findViewById(R.id.tv_product_details1);
        tv_product_details2 = findViewById(R.id.tv_product_details2);
        tv_product_details3 = findViewById(R.id.tv_product_details3);
        tv_product_details4 = findViewById(R.id.tv_product_details4);
        iv_product_details = findViewById(R.id.iv_product_details);
        btn_product_details = findViewById(R.id.btn_product_details);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        getUidAndToken();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        if (event.isLoginOk()){
            Log.e("myMessage" , "isLoginOk:"+event.isLoginOk());
            getUidAndToken();
        }
    }

    private void getUidAndToken() {
        uid=sharedPreferences.getString("uid",null);
        token=sharedPreferences.getString("token",null);
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText("商品详情");
        //请求数据
        pid = getIntent().getIntExtra("pid", 1);
        HashMap<String, String> map = new HashMap<>();
        map.put("source","android");
        map.put("pid",pid+"");
        getPresenter().getDataFromServer(map,1);
    }

    private void setData() {
        String[] images = productDetailsBean.getData().getImages().split("\\|");
        ProductDetailsAdapter adapter = new ProductDetailsAdapter(ProductDetailsActivity.this,images);
        vp_product_details.setAdapter(adapter);
        tv_product_details1.setText(productDetailsBean.getData().getTitle());
        tv_product_details2.setText(productDetailsBean.getData().getSubhead());
        tv_product_details3.setText("原价：￥"+productDetailsBean.getData().getPrice());
        tv_product_details3.setPaintFlags(tv_product_details3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tv_product_details4.setText("优惠价：￥"+productDetailsBean.getData().getBargainPrice());
        //点击事件
        iv_product_details.setOnClickListener(this);
        btn_product_details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_product_details:
                MainActivity.start(this);
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setPage(4);
                EventBus.getDefault().postSticky(messageEvent);
                break;
            case R.id.btn_product_details:
                if (ok){
                    if (!(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token))){
                        HashMap<String, String> map = new HashMap<>();
                        map.put("source","android");
                        map.put("uid",uid);
                        map.put("token",token);
                        map.put("pid",productDetailsBean.getData().getPid()+"");
                        getPresenter().getDataFromServer(map,2);
                    }else {
                        LoginActivity.start(ProductDetailsActivity.this);
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object success, int i) {
        switch (i){
            case 1:
                productDetailsBean = (ProductDetailsBean) success;
                handler.sendEmptyMessage(0);
                ok=true;
                break;
            case 2:
                addCartBean = (AddCartBean) success;
                handler.sendEmptyMessage(1);
                break;
        }
    }

    @Override
    ProductDetailsPresenter initPresenter() {
        return new ProductDetailsPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_product_details;
    }

    public static void start(Context context, int pid) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("pid",pid);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
