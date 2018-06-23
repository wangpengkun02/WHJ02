package com.example.whj02.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whj02.Adapter.MyBannerLoader;
import com.example.whj02.Adapter.TuiJianAdapter;
import com.example.whj02.Bean.EBMessage;
import com.example.whj02.Bean.JrGwcBean;
import com.example.whj02.Bean.XqBean;
import com.example.whj02.R;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class XqActivity extends AppCompatActivity implements View.OnClickListener {
    String a="https://www.zhaoapi.cn/product/getProductDetail?pid=";

         List<String> lblist=new ArrayList<>();
    private TextView xqname;
    private TextView xqprice;
    private Banner xqmb;
    private Button jrgwc;
    private int str;
    private int pid;
    SharedPreferences test;
    private SharedPreferences.Editor edit;
    private boolean pd;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        initview();
        Intent intent = getIntent();
        pid = intent.getIntExtra("pid",1);
        getOKxq();
        test = getSharedPreferences("test", Context.MODE_PRIVATE);
        edit = test.edit();
        uid = test.getInt("uid", 0);
        pd = test.getBoolean("pd", false);
     }

    private void initview() {
        xqname = findViewById(R.id.xqname);
        xqprice = findViewById(R.id.xqprice);
        xqmb = findViewById(R.id.xqmb);
        jrgwc = findViewById(R.id.jrgwc);
        jrgwc.setOnClickListener(this);
    }
    private void getOKxq() {
        String path= a+pid;
        OkHttp3Util_03.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                XqBean xqBean = new Gson().fromJson(string, XqBean.class);
                final XqBean.DataBean data = xqBean.getData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String images = data.getImages();
                        String title = data.getTitle();
                        double price = data.getPrice();
                        xqname.setText(title);
                        xqprice.setText("￥"+price);
                        lunbo(images);

                    }
                });
            }
        });
    }

    private void lunbo(String images) {
        Log.d("test","---"+ images.toString());
        String[] split =images.split("\\|");
        for (int i = 0; i < split.length; i++) {
            lblist.add(split[i]);
        }


        xqmb.setImageLoader(new MyBannerLoader());//添加自定义的图片加载器
        xqmb.setImages(lblist);//设置图片资源
        xqmb.start();//开始轮播
    }

    @Override
    public void onClick(View v) {
              switch (v.getId()){
                  case R.id.jrgwc:
                        if (pd){

                            //https://www.zhaoapi.cn/product/addCart?uid=72&pid=1
                         String tjgwcPath="https://www.zhaoapi.cn/product/addCart?uid="+uid+"&pid="+pid;
                        Log.d("test","------"+ tjgwcPath.toString());
                               getJrgwc(tjgwcPath);
                        }else {
                            AlertDialog.Builder builder=new AlertDialog.Builder(XqActivity.this);
                            builder.setMessage("你未登录是否去登录");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                     Intent intent=new Intent(XqActivity.this,DlActivity.class);
                                     startActivity(intent);
                                }
                            });

                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {



                                }
                            });
                            builder.create().show();
                        }
                      break;
              }
    }

    private void getJrgwc(String path02) {
        OkHttp3Util_03.doGet(path02, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                final JrGwcBean jrGwcBean = new Gson().fromJson(ss, JrGwcBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = jrGwcBean.getMsg();
                        Toast.makeText(XqActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
