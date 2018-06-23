package com.example.test2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.test2.R;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.model.bean.ProductListBean;
import com.example.test2.presenter.ProductListPresenter;
import com.example.test2.view.adapter.ProductListAdapter;
import com.example.test2.view.customview.MyTitleView;
import com.example.test2.view.interfaces.IProductListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ProductListActivity extends BaseActivity<ProductListPresenter> implements IProductListView{

    private int pscid;
    private boolean flag;
    private RecyclerView rv_product_list;
    private EditText et_product_list;
    private ProductListBean productListBean=new ProductListBean();
    private ProductListAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                adapter.setData(productListBean.getData());
            }
        }
    };

    @Override
    void initView() {
        rv_product_list = findViewById(R.id.rv_product_list);
        et_product_list = findViewById(R.id.et_product_list);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
//        Log.e("ClassifyEventBus" , event.isSuccessHome()+"");
//        if (event.isSuccessAll()){
//            HashMap<String, String> map = new HashMap<>();
//            map.put("cid",classifyBean.getData().get(position).getCid()+"");
//            getPresenter().getDataFromServer(urlString);
//            //移除粘性事件
//            EventBus.getDefault().removeStickyEvent(event);
//        }
    }

    @Override
    void initData() {
        MyTitleView myTitleView = getParent_title();
        myTitleView.getTitle().setText("商品列表");
        //添加列表 图标
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        params1.rightMargin=20;
        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.kind_grid);
        myTitleView.addView(imageView,params1);
        myTitleView.invalidate();
        //适配器
        adapter = new ProductListAdapter(ProductListActivity.this);
        rv_product_list.setAdapter(adapter);
        //布局管理器
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductListActivity.this, 2);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductListActivity.this);
        rv_product_list.setLayoutManager(linearLayoutManager);
        //请求数据
        pscid = getIntent().getIntExtra("pscid", 1);
        getPresenter().getDataFromServer(pscid+"");
        //点击切换视图展示方式
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    rv_product_list.setLayoutManager(linearLayoutManager);
                    imageView.setImageResource(R.drawable.kind_grid);
                }else {
                    rv_product_list.setLayoutManager(gridLayoutManager);
                    imageView.setImageResource(R.drawable.kind_liner);
                }
                flag =!flag;
            }
        });
        et_product_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(ProductListActivity.this);
            }
        });
    }

    @Override
    ProductListPresenter initPresenter() {
        return new ProductListPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_product_list;
    }

    @Override
    public void onSuccess(Object success) {
        productListBean = (ProductListBean) success;
        handler.sendEmptyMessage(0);
    }

    public static void start(Context context,int pscid) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("pscid",pscid);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
