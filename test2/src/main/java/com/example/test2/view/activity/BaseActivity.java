package com.example.test2.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.test2.R;
import com.example.test2.presenter.BasePresenter;
import com.example.test2.view.customview.MyTitleView;
import com.example.test2.view.interfaces.IBaseView;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    private MyTitleView parent_title;
    private FrameLayout child_view;
    private P p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initParentView();

        View view = View.inflate(this, setChildContentView(), null);
        child_view.addView(view);

        initView();

        p=initPresenter();
        if (p != null) {
            p.attachView(this);
        }else {
            try {
                throw new Exception("少年 prenter 没有设置 请在您的Activity 创建 presenter");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initData();
    }

    public MyTitleView getParent_title(){
        return parent_title;
    }

    private void initParentView() {
        parent_title = findViewById(R.id.parent_title);
        child_view = findViewById(R.id.child_view);
        parent_title.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public P getPresenter() {
        return p;
    }

    abstract void initView();
    abstract void initData();
    abstract P initPresenter();
    abstract int setChildContentView();
}
