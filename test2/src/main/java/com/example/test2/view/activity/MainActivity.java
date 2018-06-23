package com.example.test2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.test2.R;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.presenter.MainPresenter;
import com.example.test2.view.fragment.FragmentCart;
import com.example.test2.view.fragment.FragmentClassify;
import com.example.test2.view.fragment.FragmentDiscover;
import com.example.test2.view.fragment.FragmentHome;
import com.example.test2.view.fragment.FragmentMine;
import com.example.test2.view.interfaces.IMainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, View.OnClickListener {

    private LinearLayout ll_home;
    private ImageView iv_home;
    private LinearLayout ll_classify;
    private ImageView iv_classify;
    private LinearLayout ll_discover;
    private ImageView iv_discover;
    private LinearLayout ll_cart;
    private ImageView iv_cart;
    private LinearLayout ll_mine;
    private ImageView iv_mine;
    private FragmentHome fragmentHome;
    private FragmentClassify fragmentClassify;
    private FragmentDiscover fragmentDiscover;
    private FragmentCart fragmentCart;
    private FragmentMine fragmentMine;
    private FragmentManager supportFragmentManager;
    private int page=0;
    private SharedPreferences sharedPreferences;

    @Override
    MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_main;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    void initView() {
        ll_home = findViewById(R.id.ll_home);
        iv_home = findViewById(R.id.iv_home);
        ll_classify = findViewById(R.id.ll_classify);
        iv_classify = findViewById(R.id.iv_classify);
        ll_discover = findViewById(R.id.ll_discover);
        iv_discover = findViewById(R.id.iv_discover);
        ll_cart = findViewById(R.id.ll_cart);
        iv_cart = findViewById(R.id.iv_cart);
        ll_mine = findViewById(R.id.ll_mine);
        iv_mine = findViewById(R.id.iv_mine);
        initFragment();
        initFragmentAdd();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        EventBus.getDefault().register(this);
        setImageViewState(iv_home,R.drawable.ac1);
        setOnClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        switch (page){
            case 4:
                ll_cart.performClick();
                page=0;
                break;
            case 5:
                ll_mine.performClick();
                page=0;
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        page=event.getPage();
        if (event.getPage()!=0){
            Log.e("myMessage" , "MainActivity切换到页面:"+event.getPage());
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText("京东商城");
    }

    @Override
    public void onClick(View v) {
        MessageEvent messageEvent = new MessageEvent();
        switch (v.getId()){
            case R.id.ll_home:
                getParent_title().getTitle().setText("京东商城");
                setImageViewState(iv_home,R.drawable.ac1);
                hideFragment(fragmentHome,fragmentClassify,fragmentDiscover,fragmentCart,fragmentMine);

                messageEvent.setClickHome(true);
                EventBus.getDefault().postSticky(messageEvent);
                break;
            case R.id.ll_classify:
                getParent_title().getTitle().setText("分类");
                setImageViewState(iv_classify,R.drawable.abx);
                hideFragment(fragmentClassify,fragmentHome,fragmentDiscover,fragmentCart,fragmentMine);

                messageEvent.setClickClassify(true);
                EventBus.getDefault().postSticky(messageEvent);
                break;
            case R.id.ll_discover:
                getParent_title().getTitle().setText("发现");
                setImageViewState(iv_discover,R.drawable.abz);
                hideFragment(fragmentDiscover,fragmentHome,fragmentClassify,fragmentCart,fragmentMine);

//                messageEvent.setClickDiscover(true);
//                EventBus.getDefault().postSticky(messageEvent);
                break;
            case R.id.ll_cart:
                getParent_title().getTitle().setText("购物车");
                setImageViewState(iv_cart,R.drawable.abv);
                hideFragment(fragmentCart,fragmentHome,fragmentClassify,fragmentDiscover,fragmentMine);

                messageEvent.setClickCart(true);
                EventBus.getDefault().postSticky(messageEvent);
                break;
            case R.id.ll_mine:
                getParent_title().getTitle().setText("我的");
                setImageViewState(iv_mine,R.drawable.ac3);
                hideFragment(fragmentMine,fragmentClassify,fragmentDiscover,fragmentCart,fragmentHome);

                messageEvent.setClickMine(true);
                EventBus.getDefault().postSticky(messageEvent);
                break;
        }
    }

    private void setImageViewState(ImageView iv,int id) {
        iv_home.setImageResource(R.drawable.ac0);
        iv_classify.setImageResource(R.drawable.abw);
        iv_discover.setImageResource(R.drawable.aby);
        iv_cart.setImageResource(R.drawable.abu);
        iv_mine.setImageResource(R.drawable.ac2);

        iv.setImageResource(id);
    }

    private void hideFragment(Fragment fragmentShow,Fragment...fragmentHide) {
        supportFragmentManager.beginTransaction().show(fragmentShow).hide(fragmentHide[0]).hide(fragmentHide[1]).hide(fragmentHide[2]).hide(fragmentHide[3]).commit();
    }

    @Override
    public void onSuccess(String success) {

    }

    @Override
    public void onError(String error) {

    }

    private void initFragment() {
        fragmentHome = new FragmentHome();
        fragmentClassify = new FragmentClassify();
        fragmentDiscover = new FragmentDiscover();
        fragmentCart = new FragmentCart();
        fragmentMine = new FragmentMine();
    }

    private void initFragmentAdd() {
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl,fragmentHome).commit();
        supportFragmentManager.beginTransaction().add(R.id.fl,fragmentClassify).hide(fragmentClassify).commit();
        supportFragmentManager.beginTransaction().add(R.id.fl,fragmentDiscover).hide(fragmentDiscover).commit();
        supportFragmentManager.beginTransaction().add(R.id.fl,fragmentCart).hide(fragmentCart).commit();
        supportFragmentManager.beginTransaction().add(R.id.fl,fragmentMine).hide(fragmentMine).commit();
    }

    private void setOnClick() {
        ll_home.setOnClickListener(this);
        ll_classify.setOnClickListener(this);
        ll_discover.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
