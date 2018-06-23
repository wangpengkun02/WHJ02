package com.example.test2.view.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;

/**
 * author:Created by WangZhiQiang on 2018/4/13.
 */
public class MyTitleView extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private TextView textView;
    private ImageView imageView;

    public MyTitleView(Context context) {
        this(context,null);
    }

    public MyTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView(context);
    }

    private void initView(final Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView = new TextView(context);
        textView.setText("标题");
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        addView(textView,params);
        textView.setOnClickListener(this);

        LayoutParams params1 = new LayoutParams(100, 100);
        params1.addRule(RelativeLayout.ALIGN_LEFT);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        params1.leftMargin=20;
        imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.icon_back);
        addView(imageView,params1);
    }

    public TextView getTitle(){
        return textView;
    }

    public ImageView getBack(){
        return imageView;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
