package com.example.test2.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.AddAddrBean;
import com.example.test2.model.bean.AddrsBean;
import com.example.test2.model.bean.SetAddrBean;
import com.example.test2.model.bean.UpdateAddrBean;
import com.example.test2.presenter.AddrsPresenter;
import com.example.test2.view.adapter.AddrsAdapter;
import com.example.test2.view.interfaces.IAddrsView;

import java.util.HashMap;

public class AddrsActivity extends BaseActivity<AddrsPresenter> implements IAddrsView{
    private AddrsBean addrsBean=new AddrsBean();
    private SetAddrBean setAddrBean=new SetAddrBean();
    private AddAddrBean addAddrBean=new AddAddrBean();
    private UpdateAddrBean updateAddrBean=new UpdateAddrBean();
    private SharedPreferences sharedPreferences;
    private String uid;

    private RecyclerView rv_addrs;
    private Button btn_addrs_add;
    private AddrsAdapter adapter;

    @Override
    void initView() {
        getParent_title().getTitle().setText("收货地址列表");
        rv_addrs = findViewById(R.id.rv_addrs);
        btn_addrs_add = findViewById(R.id.btn_addrs_add);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
    }

    @Override
    void initData() {
        adapter = new AddrsAdapter(this);
        rv_addrs.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_addrs.setLayoutManager(linearLayoutManager);
        //请求数据
        getPresenter().getDataFromServer(sharedPreferences.getString("uid",null));
        adapter.setOnItemClickListener(new AddrsAdapter.CallBackViewClick() {
            @Override
            public void onViewClick(View view, final AddrsBean.DataBean dataBean) {
                //条目点击设置默认地址
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("uid",uid);
                        map.put("status","1");
                        map.put("addrid",""+dataBean.getAddrid());
                        getPresenter().setAddr(map);
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showAlertDialog(0,dataBean);
                        return false;
                    }
                });
            }
        });
        //点击添加收货地址
        btn_addrs_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(1,null);
            }
        });
    }

    @Override
    public void onSuccess(Object success, int i) {
        switch (i){
            case 1:
                addrsBean= (AddrsBean) success;
                if (addrsBean.getCode().equals("0")){
                    adapter.setData(addrsBean.getData());
                }else {
                    Toast.makeText(AddrsActivity.this, addrsBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                setAddrBean= (SetAddrBean) success;
                Toast.makeText(AddrsActivity.this, setAddrBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (setAddrBean.getCode().equals("0")){
                    getPresenter().getDataFromServer(uid);
                }
                break;
            case 3:
                addAddrBean= (AddAddrBean) success;
                Toast.makeText(AddrsActivity.this, addAddrBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (addAddrBean.getCode().equals("0")){
                    getPresenter().getDataFromServer(uid);
                }
                break;
            case 4:
                updateAddrBean= (UpdateAddrBean) success;
                Toast.makeText(AddrsActivity.this, updateAddrBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (updateAddrBean.getCode().equals("0")){
                    getPresenter().getDataFromServer(uid);
                }
                break;
        }
    }

    private void showAlertDialog(final int flag, final AddrsBean.DataBean dataBean){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddrsActivity.this);
        final AlertDialog dialog = builder.create();
        View contentView=View.inflate(AddrsActivity.this, R.layout.addrs_dialog_layout, null);
        dialog.setView(contentView);
        final EditText et_nickname_dialog1 = contentView.findViewById(R.id.et_addrs_dialog1);
        final EditText et_nickname_dialog2 = contentView.findViewById(R.id.et_addrs_dialog2);
        final EditText et_nickname_dialog3 = contentView.findViewById(R.id.et_addrs_dialog3);
        Button btn_cancel_dialog_addrs = contentView.findViewById(R.id.btn_cancel_dialog_addrs);
        Button btn_confirm_dialog_addrs = contentView.findViewById(R.id.btn_confirm_dialog_addrs);

        if (flag==0){
            et_nickname_dialog1.setText(dataBean.getAddr());
            et_nickname_dialog2.setText(dataBean.getName());
            et_nickname_dialog3.setVisibility(View.GONE);
        }
        btn_cancel_dialog_addrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_confirm_dialog_addrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                if (flag==0){
                    map.put("uid",uid);
                    map.put("addrid",dataBean.getAddrid()+"");
                    map.put("addr",et_nickname_dialog1.getText()+"");
                    map.put("name",et_nickname_dialog2.getText()+"");
                    getPresenter().UpdateAddr(map);
                }else {
                    map.put("uid",uid);
                    map.put("addr",et_nickname_dialog1.getText()+"");
                    map.put("name",et_nickname_dialog2.getText()+"");
                    map.put("mobile",et_nickname_dialog3.getText()+"");
                    getPresenter().addAddr(map);
                }
                dialog.dismiss();
            }
        });
        //5.显示对话框
        dialog.show();
    }

    @Override
    AddrsPresenter initPresenter() {
        return new AddrsPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_addrs;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddrsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
