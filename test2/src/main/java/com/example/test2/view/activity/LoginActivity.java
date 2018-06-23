package com.example.test2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.LoginBean;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.presenter.LoginPresenter;
import com.example.test2.utils.CommonUtil;
import com.example.test2.view.interfaces.ILoginView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, View.OnClickListener, View.OnLongClickListener {

    private EditText et_login_name;
    private EditText et_login_pass;
    private Button btn_login;
    private Button btn_login_register;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private LoginBean loginBean=new LoginBean();

    private void setPreferences() {
        editor.putString("mobile", et_login_name.getText().toString());
        editor.putString("password", et_login_pass.getText().toString());
        editor.putString("appkey", loginBean.getData().getAppkey());
        editor.putString("appsecret", loginBean.getData().getAppsecret());
        editor.putString("createtime", loginBean.getData().getCreatetime());
        editor.putString("token", loginBean.getData().getToken());
        editor.putString("uid", loginBean.getData().getUid()+"");
        editor.putString("username", loginBean.getData().getUsername());
        editor.commit();
    }

    @Override
    void initView() {
        et_login_name = findViewById(R.id.et_login_name);
        et_login_pass = findViewById(R.id.et_login_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_login_register = findViewById(R.id.btn_login_register);
        //点击监听
        btn_login.setOnClickListener(this);
        btn_login.setOnLongClickListener(this);
        btn_login_register.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText("登录");
        et_login_name.setText(sharedPreferences.getString("mobile", null));
    }

    @Override
    public void onSuccess(Object success) {
        loginBean = (LoginBean) success;
        Toast.makeText(LoginActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        //  code":"0"    登陆成功
        if (loginBean.getCode().equals("0")){
            setPreferences();
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setLoginOk(true);
            EventBus.getDefault().postSticky(messageEvent);
            finish();
        }
        et_login_name.setEnabled(true);
        et_login_pass.setEnabled(true);
        btn_login.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //判断账号密码    是否符合规格
                String name = et_login_name.getText().toString();
                String pass = et_login_pass.getText().toString();
                if(!CommonUtil.isMobileNO(name)) {
                    Toast.makeText(LoginActivity.this,"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(!CommonUtil.isPassNO(pass)) {
//                    Toast.makeText(LoginActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //发送账号密码验证登录
                if(getPresenter() != null) {
                    et_login_name.setEnabled(false);
                    et_login_pass.setEnabled(false);
                    btn_login.setEnabled(false);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("mobile",name);
                    map.put("password",pass);
                    getPresenter().getDataFromServer(map);
                }
                break;
            case R.id.btn_login_register:
                //跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            et_login_name.setText(data.getStringExtra("mobile"));
            et_login_pass.setText(data.getStringExtra("password"));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "长按启动第三方登录", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
