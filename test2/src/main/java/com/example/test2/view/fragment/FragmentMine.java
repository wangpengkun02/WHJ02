package com.example.test2.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.model.bean.NickNameBean;
import com.example.test2.model.bean.PicturesBean;
import com.example.test2.model.bean.UserInfoBean;
import com.example.test2.presenter.MinePresenter;
import com.example.test2.utils.FrescoUtil;
import com.example.test2.utils.ImageUtil;
import com.example.test2.view.activity.AddrsActivity;
import com.example.test2.view.activity.LoginActivity;
import com.example.test2.view.activity.OrdersActivity;
import com.example.test2.view.interfaces.IMineView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class FragmentMine extends BaseFragment<MinePresenter> implements IMineView, View.OnClickListener {

    private String path= Environment.getExternalStorageDirectory()+"/pictures.png";
    private String uid;
    private String token;
    private SharedPreferences sharedPreferences;
    private LinearLayout ll_mine_login;
    private SimpleDraweeView sdv_mine_icon;
    private TextView tv_mine_nickname;
    private Button btn_mine_logoff;
    private Button btn_mine_nickname;
    private Button btn_mine_pictures;
    private Button btn_mine_orders;
    private Button btn_mine_addrs;

    private UserInfoBean userInfoBean=new UserInfoBean();
    private NickNameBean nickNameBean=new NickNameBean();
    private PicturesBean picturesBean=new PicturesBean();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setData();
                    break;
                case 1:
                    Toast.makeText(getActivity(), nickNameBean.getMsg(), Toast.LENGTH_SHORT).show();
                    getData();
                    break;
                case 2:
                    Toast.makeText(getActivity(), picturesBean.getMsg(), Toast.LENGTH_SHORT).show();
                    getData();
                    break;
            }
        }
    };
    private View view;
    @Override
    void initView(View view) {
        this.view=view;
        ll_mine_login = view.findViewById(R.id.ll_mine_login);
        sdv_mine_icon = view.findViewById(R.id.sdv_mine_icon);
        tv_mine_nickname = view.findViewById(R.id.tv_mine_nickname);
        btn_mine_logoff = view.findViewById(R.id.btn_mine_logoff);
        btn_mine_nickname = view.findViewById(R.id.btn_mine_nickname);
        btn_mine_pictures = view.findViewById(R.id.btn_mine_pictures);
        btn_mine_orders = view.findViewById(R.id.btn_mine_orders);
        btn_mine_addrs = view.findViewById(R.id.btn_mine_addrs);
        ll_mine_login.setOnClickListener(this);
        btn_mine_logoff.setOnClickListener(this);
        btn_mine_nickname.setOnClickListener(this);
        btn_mine_pictures.setOnClickListener(this);
        btn_mine_orders.setOnClickListener(this);
        btn_mine_addrs.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        getUidAndToken();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        if (event.isSuccessCart()||event.isClickMine()){
            Log.e("myMessage" , "isSuccessCart:"+event.isSuccessCart());
            Log.e("myMessage" , "点击我的:"+event.isClickMine());
            getData();
            EventBus.getDefault().removeStickyEvent(event);
        }
        if (event.isLoginOk()){
            Log.e("myMessage" , "isLoginOk:"+event.isLoginOk());
            getData();
        }
    }

    private void getUidAndToken() {
        uid=sharedPreferences.getString("uid",null);
        token=sharedPreferences.getString("token",null);
    }

    private void getData(){
        getUidAndToken();
        if (!(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token))){
//            Log.e("myMessage" , "查询用户信息uid:"+uid+"--token:"+token);
            HashMap<String, String> map = new HashMap<>();
            map.put("uid",uid);
            map.put("token",token);
            getPresenter().getDataFromServer(map,1);
        }else {
            setDefaultData();
        }
    }

    @Override
    void initData() {

    }

    private void setData(){
        if (userInfoBean!=null){
            if (userInfoBean.getData().getNickname()==null){
                tv_mine_nickname.setText(userInfoBean.getData().getUsername());
            }else {
                tv_mine_nickname.setText(userInfoBean.getData().getNickname());
            }
            FrescoUtil.setYuanQuan(userInfoBean.getData().getIcon(),sdv_mine_icon, Color.WHITE,0f);
        }else {
            setDefaultData();
        }
    }

    private void setDefaultData(){
        tv_mine_nickname.setText("登录/注册");
        FrescoUtil.setYuanQuan(null,sdv_mine_icon, Color.WHITE,0f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_mine_login:
                LoginActivity.start(getActivity());
                break;
            case R.id.btn_mine_logoff:
                //清除用户信息
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                getData();
                break;
            case R.id.btn_mine_nickname:
            case R.id.btn_mine_pictures:
            case R.id.btn_mine_orders:
            case R.id.btn_mine_addrs:
                mass(v.getId());
                break;
        }
    }

    private void mass(int id){
        if (TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            switch (id){
                case R.id.btn_mine_nickname:
                    showAlertDialog();
                    break;
                case R.id.btn_mine_pictures:
                    showPopupWindow();
                    break;
                case R.id.btn_mine_orders:
                    OrdersActivity.start(getActivity());
                    break;
                case R.id.btn_mine_addrs:
                    AddrsActivity.start(getActivity());
                    break;
            }
        }
    }

    @Override
    public void onSuccess(Object success,int flag) {
        switch (flag){
            case 1:
                userInfoBean= (UserInfoBean) success;
                handler.sendEmptyMessage(0);
                break;
            case 2:
                nickNameBean= (NickNameBean) success;
                handler.sendEmptyMessage(1);
                break;
            case 3:
                picturesBean= (PicturesBean) success;
                handler.sendEmptyMessage(2);
                break;
        }
    }

    private void showPopupWindow(){
        //窗口布局转换成视图对象
        View contentView = View.inflate(getActivity(), R.layout.pictures_pop_layout, null);
        /**
         * contentView :popup布局转换成的视图对象
         * width:窗口显示的宽度
         * height:窗口显示的高度
         */
        final PopupWindow window = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //如果想要设置点击外部窗口消失,,必须2个方法同时使用
        window.setBackgroundDrawable(new BitmapDrawable());//给窗口设置一个完全透明的背景图片
        window.setOutsideTouchable(true);//设置窗口外部可以触摸

        //窗口里面的控件没有响应解决方案
        window.setFocusable(true);//设置窗口的焦点事件
        window.setTouchable(true);//设置窗口本身可以触摸

        //通过视图对象操控里面的控件
        Button btn_camera_pop = contentView.findViewById(R.id.btn_camera_pop);
        Button btn_photos_pop = contentView.findViewById(R.id.btn_photos_pop);
        Button btn_cancel_pop = contentView.findViewById(R.id.btn_cancel_pop);

        //拍照并裁剪
        btn_camera_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //在Sdcard 中创建文件 存入图片
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, 3);
                window.dismiss();
            }
        });
        //调取相册进行裁剪
        btn_photos_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调取系统的相册  Intent.ACTION_PICK相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                //设置格式
                intent.setType("image/*");
                startActivityForResult(intent, 7);
                window.dismiss();
            }
        });
        //取消
        btn_cancel_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void showAlertDialog(){
        //1.创建构造器对象
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //2.通过构造器对象,,,创建出来一个AlertDialog的对象
        final AlertDialog dialog = builder.create();
        //3.通过AlertDialog的对象,,,去设置自己的布局(视图对象)
        View contentView=View.inflate(getActivity(), R.layout.nickname_dialog_layout, null);
        dialog.setView(contentView);
        //4.可以执行逻辑操作,,,按钮的监听,,,找控件还是必须通过,视图对象去找
        final EditText et_nickname_dialog = contentView.findViewById(R.id.et_nickname_dialog);
        Button btn_cancel_dialog = contentView.findViewById(R.id.btn_cancel_dialog);
        Button btn_confirm_dialog = contentView.findViewById(R.id.btn_confirm_dialog);

        et_nickname_dialog.setText(tv_mine_nickname.getText());
        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_confirm_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("uid",uid);
                map.put("token",token);
                map.put("nickname",et_nickname_dialog.getText()+"");
                getPresenter().getDataFromServer(map,2);
                dialog.dismiss();
            }
        });
        //5.显示对话框
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照并裁剪
        getb(requestCode, resultCode, data);
        //调取相册进行裁剪
        getc(requestCode, resultCode, data);

    }

    private void getb(int requestCode, int resultCode, Intent data) {
        //当拍完照以后点击完成  会执行 onActivityResult 方法 调取裁剪功能
        if (requestCode==3 && resultCode==RESULT_OK) {
            //调取裁剪功能  om.android.camera.action.CROP 裁剪的Action
            Intent intent = new Intent("com.android.camera.action.CROP");
            //得到图片设置类型
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //是否支持裁剪 设置 true 支持  false 不支持
            intent.putExtra("CROP", true);
            //设置比例大小  1:1
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将裁剪好的图片进行返回到Intent中
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 5);
        }
        //点击完裁剪的完成以后会执行的方法
        if (requestCode==5 && resultCode==RESULT_OK) {
            Bitmap bm=data.getParcelableExtra("data");
            ImageUtil.saveBitmap(bm,path);
            getPresenter().uploadPic(uid,path);
        }
    }
    private void getc(int requestCode, int resultCode, Intent data) {
        //得到相册里的图片进行裁剪
        if (requestCode==7 && resultCode==RESULT_OK && null != data) {
            //得到相册图片
            Uri uri=data.getData();
            //裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置图片 以及格式
            intent.setDataAndType(uri, "image/*");
            //是否支持裁剪
            intent.putExtra("crop", true);
            //设置比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //返回
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 9);
        }
        //2.点击裁剪完成
        if (requestCode==9 && resultCode==RESULT_OK && null != data) {
            Bitmap bm=data.getParcelableExtra("data");
            ImageUtil.saveBitmap(bm,path);
            getPresenter().uploadPic(uid,path);
        }
    }

    @Override
    MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
