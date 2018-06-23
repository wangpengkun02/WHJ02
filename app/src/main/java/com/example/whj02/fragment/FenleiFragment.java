package com.example.whj02.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whj02.Adapter.LeftAdapter;
import com.example.whj02.Adapter.RightAdapter;
import com.example.whj02.Bean.JiuGongGeBean;
import com.example.whj02.Bean.RightBean;
import com.example.whj02.MainActivity;
import com.example.whj02.R;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class FenleiFragment extends Fragment {

    private View view;
    private RecyclerView mLeftfen;
    private RecyclerView mRightfen;
    private  String left="https://www.zhaoapi.cn/product/getCatagory";
    String    rightqian = "https://www.zhaoapi.cn/product/getProductCatagory?cid=";
    LeftAdapter leftadapter;
     RightAdapter  rightAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fen_lei, container, false);
        mLeftfen = (RecyclerView) view.findViewById(R.id.leftfen);
        mRightfen = (RecyclerView) view.findViewById(R.id.rightfen);

        getok03();
        getok04(rightqian+1);
        return view;
    }

    private void getok04(String right) {
        OkHttp3Util_03.doGet(right, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call,  Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String string = response.body().string();
                    final RightBean rightBean = new Gson().fromJson(string, RightBean.class);
                    Log.d("TAG", "或" + rightBean);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override

                        public void run() {

                            List<RightBean.DataBean> righttuidata = rightBean.getData();

                            RightTui(righttuidata);
                        }
                    });
                }
            }
        });
    }

    private void RightTui(List<RightBean.DataBean> righttuidata) {
        mRightfen.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false));
        rightAdapter = new RightAdapter (getActivity(),righttuidata);
        mRightfen.setAdapter(rightAdapter);
    }

    private void getok03() {

            OkHttp3Util_03.doGet(left, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call,  Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String string = response.body().string();
                        final   JiuGongGeBean jiuGongGeBean = new Gson().fromJson(string, JiuGongGeBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override

                            public void run() {
                                List<JiuGongGeBean.DataBean> jiugongdata = jiuGongGeBean.getData();
                                jiugongge(jiugongdata);
                            }
                        });
                    }
                }
            });

    }

    private void jiugongge(final List<JiuGongGeBean.DataBean> jiugongdata) {
        mLeftfen.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false));
        leftadapter = new LeftAdapter(getActivity(),jiugongdata);

        mLeftfen.setAdapter(leftadapter);
        leftadapter.SetOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int cid = jiugongdata.get(position).getCid();
                String    right = rightqian+cid;
                getok04(right);

            }
        });
    }



}
