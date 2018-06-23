package com.example.test2.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 跳转工具
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class SkipPageUtil {
    public static void SkipUtil(Context context,Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }
}
