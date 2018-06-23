package com.example.whj02.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whj02.Adapter.MyBannerLoader;
import com.example.whj02.Adapter.JiuGongGeAdapter;
import com.example.whj02.Adapter.TuiJianAdapter;
import com.example.whj02.Bean.EBMessage;
import com.example.whj02.Bean.JiuGongGeBean;
import com.example.whj02.Bean.ShouYeBean;
import com.example.whj02.MainActivity;
import com.example.whj02.R;
import com.example.whj02.activity.XqActivity;
import com.example.whj02.activity.sousuo;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;
import com.google.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class ShowyeFragment extends Fragment {

    private String path = "https://www.zhaoapi.cn/ad/getAd";
   private  String path02="https://www.zhaoapi.cn/product/getCatagory";
    private Banner mybanner;
    JiuGongGeAdapter adapter;
    TuiJianAdapter tuiJianAdapter;
 int j=0;
    private RecyclerView rlv;
    private RecyclerView rlv02;
    private EditText ssk;
    ArrayList<String> lists = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    private TextView ss;
    private TextView sys;
    private final static int REQ_CODE = 1028;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_showye, container, false);
        mybanner = view.findViewById(R.id.mybanner);
        rlv = view.findViewById(R.id.rlv);
        rlv02 = view.findViewById(R.id.rlv02);
        ssk = view.findViewById(R.id.ssk);
        ss = view.findViewById(R.id.ss);
        sys = view.findViewById(R.id.sys);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getOk();
        getOk02();
        sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,REQ_CODE);
            }
        });
          ssk.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {

                      switch (event.getAction()){
                          case MotionEvent.ACTION_DOWN:
                              Intent intent=new Intent(getContext(),sousuo.class);
                              startActivity(intent);
                              break;
                      }
                  return false;
              }
          });

    }

    private void getOk02() {
        OkHttp3Util_03.doGet(path02, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call,  Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String string = response.body().string();
                    final JiuGongGeBean jiuGongGeBean = new Gson().fromJson(string, JiuGongGeBean.class);
                    Log.d("test","------"+ jiuGongGeBean.toString());
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

    private void getOk() {

        OkHttp3Util_03.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call,  Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String string = response.body().string();
                    final ShouYeBean shouYeBean = new Gson().fromJson(string, ShouYeBean.class);
                    final List<ShouYeBean.DataBean>    lunbodata = shouYeBean.getData();
                    final List<ShouYeBean.TuijianBean.ListBean> tuijians = shouYeBean.getTuijian().getList();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lunbo(lunbodata);
                            tui(tuijians);
                        }
                    });
                }
            }
        });
    }

    private void tui(final List<ShouYeBean.TuijianBean.ListBean> tuijians) {
        rlv02.setLayoutManager(new GridLayoutManager (getActivity(),2,GridLayoutManager.VERTICAL,false));
        tuiJianAdapter = new TuiJianAdapter(getActivity(),tuijians);

        rlv02.setAdapter(tuiJianAdapter);
        tuiJianAdapter.SetOnItemClickListener(new TuiJianAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int pid = tuijians.get(position).getPid();
                Intent intent=new Intent(getActivity(),XqActivity.class);
                intent.putExtra("pid",pid);

                getActivity().startActivity(intent);
            }
        });
    }
    private void jiugongge(List<JiuGongGeBean.DataBean> jiugongdata) {
        rlv.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false));
              adapter = new JiuGongGeAdapter(getActivity(),jiugongdata);
        rlv.setAdapter(adapter);
    }

    private void lunbo(List<ShouYeBean.DataBean> lunbodata) {

        for (int i = 0; i < lunbodata.size(); i++) {
            if (i>=titles.size()){
                String icon = lunbodata.get(i).getIcon();
                String title = lunbodata.get(i).getTitle();
                titles.add(title);
                lists.add(icon);
            }
        }
        ///轮播
        mybanner.setImageLoader(new MyBannerLoader());
        mybanner.setImages(lists);
        Log.d("TAG", "或" + titles);
        mybanner.setBannerTitles(titles);
        mybanner.setDelayTime(1500);
        mybanner.setIndicatorGravity(BannerConfig.CENTER);
        mybanner.setBannerStyle (BannerConfig.NUM_INDICATOR_TITLE);
        mybanner.start();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {

            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);
             Log.d("test","扫描结果为：" + result);
        }
    }
}