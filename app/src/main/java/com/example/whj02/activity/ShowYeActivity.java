package com.example.whj02.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.whj02.R;
import com.example.whj02.fragment.FaxianFragment;
import com.example.whj02.fragment.FenleiFragment;
import com.example.whj02.fragment.GouWuCheFragment;
import com.example.whj02.fragment.ShowyeFragment;
import com.example.whj02.fragment.WoDeFragment;

public class ShowYeActivity extends AppCompatActivity   {

    private FrameLayout mFl;
    private RadioButton mShouye;
    private RadioButton mFenlei;
    private RadioButton mFaxian;
    private RadioButton mGouwuche;
    private RadioButton mWode;
    private ShowyeFragment fragment01;
    private FenleiFragment fragment02;
    private FaxianFragment fragment03;
    private GouWuCheFragment fragment04;
    private WoDeFragment fragment05;
     private RadioGroup rg;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ye);
        initView();

    }

    private void initView() {
        FragmentTransaction   transaction;
        mFl = (FrameLayout) findViewById(R.id.fl);
        rg = findViewById(R.id.rg);
        mShouye = (RadioButton) findViewById(R.id.shouye);

        mFenlei = (RadioButton) findViewById(R.id.fenlei);

        mFaxian = (RadioButton) findViewById(R.id.faxian);

        mGouwuche = (RadioButton) findViewById(R.id.gouwuche);

        mWode = (RadioButton) findViewById(R.id.wode);

        fragment01 = new ShowyeFragment();
        fragment02 = new FenleiFragment();
        fragment03 = new FaxianFragment();
        fragment04 = new GouWuCheFragment();
        fragment05 = new WoDeFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl,fragment01).commit();


       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId) {

                   case R.id.shouye:

                       fragmentManager.beginTransaction().replace(R.id.fl,fragment01).commit();

                       break;
                   case R.id.fenlei:
                       fragmentManager.beginTransaction().replace(R.id.fl,fragment02).commit();

                       break;
                   case R.id.faxian:
                       fragmentManager.beginTransaction().replace(R.id.fl,fragment03).commit();

                       break;
                   case R.id.gouwuche:
                       fragmentManager.beginTransaction().replace(R.id.fl,fragment04).commit();

                       break;
                   case R.id.wode:

                       fragmentManager.beginTransaction().replace(R.id.fl,fragment05).commit();

                       break;
               }
           }

       });
    }



}
