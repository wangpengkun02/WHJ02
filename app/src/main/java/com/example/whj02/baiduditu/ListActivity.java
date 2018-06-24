package com.example.whj02.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Created by 浮生丶 on 2018/5/25 0025.
 */
/**

 * function:创建一个ListActivity,继承ListActivity,让此类成为入口Home,在清单文件进行配置,成为用户的接口
 */
public class ListActivity extends android.app.ListActivity {
    //创建一个内部类的数组, NameAndClass[] datas = {new 一个内部类()}
    NameAndClass[] datas = {
            new NameAndClass("HelloBaiduMap", BaiDuDITuActivity.class),
            new NameAndClass("地图分层",LayerDemoAcitivty.class ),
    };

    /**
     * 复写onCreate   @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new ArrayAdapter对象 泛型是自定义的内部类  1.第一个参数是上下文,2.android.R.layout.simple_list_item_11 3.内部类数组
        ArrayAdapter<NameAndClass> adapter = new ArrayAdapter<NameAndClass>(this, android.R.layout.simple_list_item_1, datas);
        //setListAdapter,直接传入ArrayAdapter对象.
        setListAdapter(adapter);
    }

    /**
     * 复写点击事件onListItemClick
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //进行页面跳转,startActivity,参数是创建个Intent对象,1.上下文 2.跳转类,内部类数组[position].clazz
        startActivity(new Intent(this, datas[position].clazz));
    }

    /**
     *     创建内部类 class NameAndClass {}
     */
    class NameAndClass {
        //定义String name;
        String name;
        //定义Class<?> clazz;
        Class<?> clazz;
        //自动生成构造方法.
        public NameAndClass(String name, Class<?> clazz) {
            super();
            this.name = name;
            this.clazz = clazz;
        }
        //自动生成toString方法.
        @Override
        public String toString() {
            return name;
        }
    }



}
