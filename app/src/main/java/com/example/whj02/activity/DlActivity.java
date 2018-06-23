package com.example.whj02.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whj02.Bean.DlBean;
import com.example.whj02.Bean.EBMessage;
import com.example.whj02.R;
import com.example.whj02.model.Feikong;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DlActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 输入手机号
     */
    private EditText mName;
    /**
     * 输入密码
     */
    private EditText mPwd;
    /**
     * 登录
     */
    private Button mDiandl;
    /**
     * 注册
     */
    private Button mDianzc;
    private SharedPreferences.Editor edit;
String Pathdl="https://www.zhaoapi.cn/user/login?";
    private SharedPreferences text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl);
        initView();



    }

    private void initView() {
        mName = (EditText) findViewById(R.id.dlname);
        mPwd = (EditText) findViewById(R.id.dlpwd);
        mDiandl = (Button) findViewById(R.id.diandl);
        mDiandl.setOnClickListener(this);
        mDianzc = (Button) findViewById(R.id.dianzc);
        mDianzc.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
         String   ydlname = mName.getText().toString().trim();
         String   ydlpwd = mPwd.getText().toString().trim();
        boolean feikongpd = new Feikong().feikongpd(ydlname, ydlpwd);
        switch (v.getId()) {
            case R.id.diandl:
                if (feikongpd){
                      yandl(ydlname,ydlpwd);
                }else{
                    Toast.makeText(DlActivity.this,"登录的时候密码或者账号不能为空",Toast.LENGTH_SHORT).show();
                } ;
                break;
            case R.id.dianzc:
                Intent intent=new Intent(DlActivity.this,ZcActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void yandl(String ydlname, String ydlpwd) {
        Map<String,String> map=new HashMap<String, String>();
        map.put("mobile",ydlname);
        map.put("password",ydlpwd);
        OkHttp3Util_03.doPost(Pathdl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData = response.body().string();
              final  DlBean dlBean = new Gson().fromJson(jsonData, DlBean.class);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               String code = dlBean.getCode();
                               if (code.equals("0")){
                                   EventBus.getDefault().post(new EBMessage(dlBean.getData().getUsername()));//请求成功后将数据剧通过EventBus传到我的页面
                                   text = getSharedPreferences("test", MODE_PRIVATE);
                                   edit = text.edit();
                                   edit.putBoolean("pd",true);
                                       edit.putString("name1", dlBean.getData().getUsername());

                                   edit.putInt("uid",dlBean.getData().getUid());
                                       edit.commit();
                                       finish();
                               }else{
                                   Toast.makeText(DlActivity.this, "登录失败账号或密码错误", Toast.LENGTH_LONG).show();
                               }
                           }
                       });
            }
        });
    }
  /*  private void getzc(String ydlname, String ydlpwd) {

    }*/
}
