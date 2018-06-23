package com.example.test2.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.utils.SkipPageUtil;
import com.example.test2.view.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity {

    private ViewPager vp_guide_page;
    private TextView tv_guide_page;
    private List<Integer> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        vp_guide_page = findViewById(R.id.vp_guide_page);
        tv_guide_page = findViewById(R.id.tv_guide_page);

        sharedPreferences = getSharedPreferences("isOne", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isOne",false);
        editor.commit();

        list = new ArrayList<Integer>();
        list.add(R.drawable.guide_page1);
        list.add(R.drawable.guide_page2);
        list.add(R.drawable.guide_page3);
        list.add(R.drawable.guide_page4);
        list.add(R.drawable.guide_page5);

        GuidePageAdapter guidePageAdapter = new GuidePageAdapter(GuidePageActivity.this);
        guidePageAdapter.setData(list);
        vp_guide_page.setAdapter(guidePageAdapter);

        vp_guide_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                if (position==4){
                    tv_guide_page.setVisibility(View.VISIBLE);
                }else {
                    tv_guide_page.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        tv_guide_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GuidePageActivity.this, "太转贷首页", Toast.LENGTH_SHORT).show();
                SkipPageUtil.SkipUtil(GuidePageActivity.this,MainActivity.class);
                finish();
            }
        });
    }
}
