package com.example.test2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.presenter.SearchPresenter;
import com.example.test2.view.customview.MyStreamView;
import com.example.test2.view.interfaces.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter> implements ISearchView{

    private EditText et_search;
    private ImageView iv_search_sou;
    private MyStreamView msv_search;

    @Override
    void initView() {
        et_search = findViewById(R.id.et_search);
        iv_search_sou = findViewById(R.id.iv_search_sou);
        msv_search = findViewById(R.id.msv_search);
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText("搜索");
        List<String> list=new ArrayList<>();
        list.add("衣服");
        list.add("饮料");
        list.add("奥顺利开工");
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(list.get(i));
            textView.setPadding(15,3,15,3);
            textView.setBackgroundColor(0xffdedede);
            textView.setTextSize(25);
            msv_search.addView(textView);
        }
        msv_search.invalidate();
        iv_search_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(SearchActivity.this);
                textView.setText(et_search.getText());
                textView.setPadding(15,3,15,3);
                textView.setBackgroundColor(0xffdedede);
                textView.setTextSize(25);
                msv_search.addView(textView);
                msv_search.invalidate();
            }
        });
    }

    public void qing(View view) {
        msv_search.removeAllViews();
//        msv_search.invalidate();
    }

    @Override
    SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_search;
    }

    @Override
    public void onSuccess(Object success) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
