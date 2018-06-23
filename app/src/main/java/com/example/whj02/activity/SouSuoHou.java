package com.example.whj02.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.whj02.Adapter.SsAdapter;
import com.example.whj02.Bean.SsBean;
import com.example.whj02.MainActivity;
import com.example.whj02.R;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SouSuoHou extends AppCompatActivity  {

    private EditText mDlgssk;
    private ImageView mDlgimg;
    private XRecyclerView mSsrlv;
    private int i=0;
    private String searchContent;
    int TYPRONE=1;
    private FloatingActionButton floating_btn_main;
    private boolean isRefresh = true;
    int page=1;
    final List<SsBean.DataBean> datas=new ArrayList<>();
    private SsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo_hou);
        initView();
        Intent intent = getIntent();
        searchContent = intent.getStringExtra("searchContent");
        mDlgssk.setText(searchContent);
        initsx();
         getbian();
        getssk(searchContent,TYPRONE,page);
    }

    private void getbian() {
        floating_btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSsrlv.smoothScrollToPosition(0);
            }
        });
        mDlgimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i%2==0){
                    mSsrlv.setLayoutManager(new LinearLayoutManager(SouSuoHou.this,LinearLayoutManager.VERTICAL,false));
                    mDlgimg.setImageResource(R.drawable.kind_liner);
                   int TYPRONE = 1;
                }else {
                    mSsrlv.setLayoutManager(new GridLayoutManager(SouSuoHou.this,2,GridLayoutManager.VERTICAL,false));
                    mDlgimg.setImageResource(R.drawable.kind_grid);
                  int TYPRONE = 2;

                }


                /*ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(mDlgimg,"rotation",0f,180f);
                objectAnimator.setDuration(500);
                objectAnimator.start();*/
                getssk(searchContent,TYPRONE,page);
            }
        });

        mDlgssk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Intent intent=new Intent(SouSuoHou.this,sousuo.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    private void getssk(String searchContent, final int TYPRONE, final int page) {
       String path=" https://www.zhaoapi.cn/product/searchProducts?keywords="+searchContent+"&page="+page;
        OkHttp3Util_03.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                SsBean ssBean = new Gson().fromJson(string, SsBean.class);
                final List<SsBean.DataBean> data = ssBean.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh){
                            if (page==1){
                                datas.clear();
                            }

                            datas.addAll(data);

                        }else{
                            datas.addAll(data);
                            Log.d("test","-----0"+datas.size());

                        }
                        setAdapter();

                    }
                });

            }
        });
    }

    private void setAdapter() {
        //如果适配器==空才重新new一个适配器进行加载
        if (adapter==null){
            adapter = new SsAdapter(SouSuoHou.this,datas,  TYPRONE);
            mSsrlv.setAdapter(adapter);
        }else {
//不等于空的话刷新适配器
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mDlgssk = (EditText) findViewById(R.id.dlgssk);

        mDlgimg = (ImageView) findViewById(R.id.dlgimg);

        mSsrlv =  findViewById(R.id.ssrlv);
       floating_btn_main = findViewById(R.id.floating_btn_main);
    }

    private void initsx() {

        mSsrlv.setItemAnimator(new DefaultItemAnimator());
        mSsrlv.setPullRefreshEnabled(true);
        mSsrlv.setLoadingMoreEnabled(true);
        mSsrlv. setLayoutManager(new LinearLayoutManager(SouSuoHou.this, OrientationHelper.VERTICAL,false));
        mSsrlv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mSsrlv.refreshComplete();
                isRefresh = true;
                page=1;
                getssk(searchContent,TYPRONE,page);
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                Log.d("test","-----0"+isRefresh);
                page++;

                Log.d("test","-----0"+page);
                getssk(searchContent,TYPRONE,page);
                mSsrlv.loadMoreComplete();
            }
        });
    }
}
