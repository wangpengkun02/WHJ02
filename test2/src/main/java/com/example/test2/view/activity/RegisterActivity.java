package com.example.test2.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.RegisterBean;
import com.example.test2.presenter.RegisterPresenter;
import com.example.test2.utils.CommonUtil;
import com.example.test2.view.interfaces.IRegisterView;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView, View.OnClickListener {
    private EditText et_register_name;
    private EditText et_register_pass;
    private EditText et_register_pass_sure;
    private Button btn_register;

    private RegisterBean registerBean=new RegisterBean();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(RegisterActivity.this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerBean.getCode().equals("0")){
                        Intent intent = getIntent();
                        intent.putExtra("mobile",et_register_name.getText().toString());
                        intent.putExtra("password",et_register_pass.getText().toString());
                        setResult(2,intent);
                        finish();
                    }
                    et_register_name.setEnabled(true);
                    et_register_pass.setEnabled(true);
                    btn_register.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    void initView() {
        et_register_name = findViewById(R.id.et_register_name);
        et_register_pass = findViewById(R.id.et_register_pass);
        et_register_pass_sure = findViewById(R.id.et_register_pass_sure);
        btn_register = findViewById(R.id.btn_register);
        //点击监听
        btn_register.setOnClickListener(this);
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText("注册");
    }

    @Override
    public void onSuccess(Object success) {
        registerBean = (RegisterBean) success;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                String phoneNum = et_register_name.getText().toString();
                String pass = et_register_pass.getText().toString();
                String pass_sure = et_register_pass_sure.getText().toString();
                if(!CommonUtil.isMobileNO(phoneNum)) {
                    Toast.makeText(RegisterActivity.this,"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(!CommonUtil.isPassNO(pass)) {
//                    Toast.makeText(RegisterActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (!pass.equals(pass_sure)) {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getPresenter() != null) {
                    et_register_name.setEnabled(false);
                    et_register_pass.setEnabled(false);
                    btn_register.setEnabled(false);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("mobile",phoneNum);
                    map.put("password",pass);
                    getPresenter().getDataFromServer(map);
                }
                break;
        }
    }

    @Override
    RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
