package com.example.whj02.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whj02.Bean.Back;
import com.example.whj02.R;
import com.example.whj02.model.Feikong;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZcActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 输入手机号
     */
    private EditText mZcname;
    /**
     * 输入密码
     */
    private EditText mZcpwd;
    /**
     * 立即注册
     */
    private Button mLjzc;
    String Pathzc="https://www.zhaoapi.cn/user/reg?";
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);
        initView();

    }

    private void initView() {
        mZcname = (EditText) findViewById(R.id.zcname);
        mZcpwd = (EditText) findViewById(R.id.zcpwd);
        mLjzc = (Button) findViewById(R.id.ljzc);
        mLjzc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String yzcname = mZcname.getText().toString().trim();
        String yzcpwd = mZcpwd.getText().toString().trim();
        switch (v.getId()) {

            case R.id.ljzc:

                boolean feikongpd = new Feikong().feikongpd(yzcname, yzcpwd);

                if (feikongpd){

                    getzc(yzcname,yzcpwd);
                }else{
                    Toast.makeText(ZcActivity.this,"注册密码或者账号不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void getzc(String yzcname, String yzcpwd) {
        map = new HashMap<String, String>();
        map.put("mobile",yzcname);
        map.put("password",yzcpwd);

                 OkHttp3Util_03.doPost(Pathzc, map, new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(Call call, final Response response) throws IOException {
                         final String s = response.body().string();
                         final Back fan = new Gson().fromJson(s, Back.class);
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 String msg = fan.getMsg();
                                 tan(msg);
                             }

                         });
                     }

                 });
    }

    private void tan(String msg) {
        AlertDialog.Builder builder=new AlertDialog.Builder(  ZcActivity.this);
        builder.setMessage(msg+"是否跳转到登录界面");
        builder.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                  Intent  intent = new Intent(ZcActivity.this,DlActivity.class);
                ZcActivity.this.startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


}
