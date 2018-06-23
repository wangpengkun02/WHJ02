package com.example.whj02.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whj02.Adapter.MyGwcAdapter;
import com.example.whj02.Bean.DwcBean;
import com.example.whj02.R;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class GouWuCheFragment extends Fragment   {
    SharedPreferences test;
    private SharedPreferences.Editor edit;
    private boolean pd;
    private int uid;
    private View view;
    private ImageView mBack;
    /**
     * 编辑
     */
    private TextView mEdit;
    private LinearLayout mTopBar;
    private RecyclerView mGwcrlv;
    /**
     * 全选
     */
    private CheckBox mAllChekbox;
    /**
     * 合计:
     */
    private TextView mTvTotalPrice;
    /**
     * 去结算(0)
     */
    private TextView mTvGoToPay;
    boolean isselect=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gou_wu_che, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mBack = (ImageView) view.findViewById(R.id.back);
        mEdit = (TextView) view.findViewById(R.id.edit);
        mTopBar = (LinearLayout) view.findViewById(R.id.top_bar);
        mGwcrlv = (RecyclerView) view.findViewById(R.id.gwcrlv);

        mAllChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        mTvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        mTvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        jiesuan();
        mGwcrlv.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        test = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        edit = test.edit();
        uid = test.getInt("uid", 0);
        pd = test.getBoolean("pd", false);
        if (pd) {
            Log.d("test", "-----" + uid);
            String gwcpath = "https://www.zhaoapi.cn/product/getCarts?uid=" + uid;
            getgwc(gwcpath);
        } else {
            Toast.makeText(getContext(), "你还未登录,请登录后查看", Toast.LENGTH_SHORT).show();
        }

    }

    private void jiesuan() {
        mTvGoToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getgwc(String gwcpath) {
        OkHttp3Util_03.doGet(gwcpath, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final DwcBean dwcBean = new Gson().fromJson(string, DwcBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final List<DwcBean.DataBean> data = dwcBean.getData();
                        final MyGwcAdapter adapter = new MyGwcAdapter(getContext(), data, uid);
                        mGwcrlv.setAdapter(adapter);
                        adapter.setOnzong(new MyGwcAdapter.onzong() {
                            @Override
                            public void onshangjia() {
                                boolean flag = true;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if (listBean.getSelected() == 0) {
                                            flag = false;
                                        }
                                    }
                                }
                                mAllChekbox.setChecked(flag);
                            }

                            @Override
                            public void onshangpin() {
                                boolean flag = true;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if (listBean.getSelected() == 0) {
                                            flag = false;
                                        }

                                    }
                                }
                                mAllChekbox.setChecked(flag);
                            }

                        });
                        mAllChekbox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                float price = 0;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        price += listBean.getPrice() * listBean.getNum();
                                        if (mAllChekbox.isChecked()) {

                                            listBean.setSelected(1);
                                        } else {
                                            listBean.setSelected(0);
                                        }
                                    }
                                }
                                mTvTotalPrice.setText(price + "");
                                adapter.notifyDataSetChanged();

                            }
                        });
                        adapter.setGetSumprice(new MyGwcAdapter.getSumprice() {
                            @Override
                            public void onSumprice() {
                                float price=0;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if(listBean.getSelected()==1)
                                        {
                                            price+=listBean.getPrice()*listBean.getNum();
                                        }

                                    }
                                }
                                mTvTotalPrice.setText(price+"");
                            }
                        });
                    }
                });
            }
        });
    }
}



