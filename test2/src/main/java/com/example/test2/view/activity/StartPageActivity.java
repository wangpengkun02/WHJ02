package com.example.test2.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.utils.SkipPageUtil;

public class StartPageActivity extends AppCompatActivity {

//    private TextView tv_start;
    private TextView tv_time;
    private LinearLayout ll_layout;
    private int time=6;
    private ValueAnimator valueAnimator;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time>=0) {
                tv_time.setText(time + "S");
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                //跳转页面
                tiao();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        tv_start = findViewById(R.id.tv_start);
        tv_time = findViewById(R.id.tv_time);
        ll_layout = findViewById(R.id.ll_layout);
        //开始计时
        handler.sendEmptyMessage(0);
        //设置动画
//        setValueAnimator();
//        setObjectAnimator();
        sharedPreferences = getSharedPreferences("isOne", MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
    }

    private void setObjectAnimator() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ll_layout, "translationX", 0f, 500f);
        translationX.setDuration(3000);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(ll_layout, "translationY", 0f, 1000f);
        translationY.setDuration(3000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translationX);
        animatorSet.start();
    }

    private void setValueAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0f, 700f,0f,700f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ll_layout.setTranslationX(value);
                ll_layout.setTranslationY(value*2);
                ll_layout.setScaleX(value/100);
                ll_layout.setScaleY(value/100);
                ll_layout.setRotation(value);
            }
        });
        valueAnimator.setDuration(6000);
        valueAnimator.start();
    }

    public void guo(View view) {
        tiao();
    }
    private void tiao(){
        Log.e("myMessage", "tiao: "+sharedPreferences.getBoolean("isOne",true));
        if (sharedPreferences.getBoolean("isOne",true)){
            SkipPageUtil.SkipUtil(StartPageActivity.this,GuidePageActivity.class);
            handler.removeCallbacksAndMessages(null);
//            valueAnimator.cancel();
            finish();
        }else {
            SkipPageUtil.SkipUtil(StartPageActivity.this,MainActivity.class);
            handler.removeCallbacksAndMessages(null);
//            valueAnimator.cancel();
            finish();
        }
    }
}
