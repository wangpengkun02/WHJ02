package com.example.whj02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.whj02.activity.ShowYeActivity;
import com.example.whj02.fragment.ShowyeFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    /**
     * 3s后跳转
     */
    private TextView mMiao;
    private Timer timer;
    private TimerTask task;
    private int times  = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void tiaoZhuan(View view) {
        Intent intent = new Intent(MainActivity.this, ShowYeActivity.class);

        startActivity(intent);
       finish();
        timer.cancel();
    }

    private void initView() {
        mMiao = (TextView) findViewById(R.id.miao);
        //需要计时器
        timer = new Timer();

        task = new TimerTask() {

            @Override
            public void run() {
                // 具体操作的内容
                runOnUiThread(new Runnable() {
                    //写在这个run方法中的代码会运行在主线程中
                    public void run() {
                        times --;
                        if(times>0){
                            mMiao.setText(times+"s后跳转");
                        }else {
                            Intent intent = new Intent(MainActivity.this, ShowYeActivity.class);

                            startActivity(intent);
                            finish();
                            timer.cancel();

                        }

                    }
                });
            }
        };
        timer.schedule(task , 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
