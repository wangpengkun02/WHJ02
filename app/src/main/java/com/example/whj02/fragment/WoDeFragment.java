package com.example.whj02.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whj02.Bean.DlBean;
import com.example.whj02.Bean.EBMessage;
import com.example.whj02.R;
import com.example.whj02.activity.DlActivity;
import com.example.whj02.baiduditu.BaiDuDITuActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class WoDeFragment extends Fragment implements View.OnClickListener {


    private ImageView mTx;

    /**
     * 注销
     */
    private ImageView mZhuxiao;
    /**
     * 修改名称
     */
    private ImageView mXiugainc;
    /**
     * 上传头像
     */
    private ImageView mSctx;
    /**
     * 收货地址
     */
    private ImageView mDizhi;
    /**
     * 订单列表
     */
    private ImageView mDingdan;
    private SharedPreferences test;
    private ImageView Mylocation;
    private boolean pd;
    private SharedPreferences.Editor edit;
    private EventBus eventBus;
    private TextView dl;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wo_de, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //接收手机号
        test = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        edit = test.edit();
        pd = test.getBoolean("pd", false);

        if(pd == true){//当登录成功时
            String username = test.getString("name1", "登录/注册");
            dl.setText(username);//将值赋值给文本框
        }else{
            dl.setText("登录/注册");
        }
    }

    private void initView(View view) {
        dl = view.findViewById(R.id.dl);
        dl.setOnClickListener(this);
        mZhuxiao = (ImageView) view.findViewById(R.id.zhuxiao);
        mZhuxiao.setOnClickListener(this);
        mXiugainc = (ImageView) view.findViewById(R.id.xiugainc);
        mXiugainc.setOnClickListener(this);
        mSctx = (ImageView) view.findViewById(R.id.sctx);
        mSctx.setOnClickListener(this);
        mDizhi = (ImageView) view.findViewById(R.id.dizhi);
        mDizhi.setOnClickListener(this);
        mDingdan = (ImageView) view.findViewById(R.id.dingdan);
        mDingdan.setOnClickListener(this);
        Mylocation = (ImageView) view.findViewById(R.id.Mylocation);
   Mylocation.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Toast.makeText(getContext(),"-----------",Toast.LENGTH_SHORT).show();
           Intent intent1=new Intent(getContext(), BaiDuDITuActivity.class);
           startActivity(intent1);
       }
   });
        EventBus.getDefault().register(this);
      /*  eventBus = EventBus.getDefault();//得到EventBus实例
        eventBus.register(getContext());//注册EventBus*/

    }
    //注解
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EBMessage ebMessage){
        String str=ebMessage.message;
        Log.i("aaaaqa",str);
        dl.setText(str);
    }

  /*  //接收传过来的参数         让他运行到主线程中
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusData(EBMessage msage) {
        //接收发送过来的消息
        if(msage.message != null){
            String message = msage.message;
            if(message!=null){//非空判断
                Gson gson = new Gson();//解析通过EventBus传过来的数据
                DlBean dlBean = gson.fromJson(message, DlBean.class);
                dl.setText(dlBean.getData().getUsername());//将值赋值给文本框

                //将值赋值给文本框
            }


        }
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dl:
                Intent intent=new Intent(getActivity(),DlActivity.class);
                startActivity(intent);
                break;
            case R.id.zhuxiao:
                zhuxiao();
                break;
            case R.id.xiugainc:
                break;
            case R.id.sctx:
                break;
            case R.id.dizhi:
                break;

        }
    }

    private void zhuxiao() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage("确定要注销吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edit.clear();//清空
                edit.commit();//提交
                dl.setText("登录/注册");

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.create().show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
