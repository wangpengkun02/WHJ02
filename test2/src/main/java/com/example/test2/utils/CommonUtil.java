package com.example.test2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:Created by WangZhiQiang on 2018/4/19.
 */
public class CommonUtil {
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    public static boolean isPassNO(String pass){
        Pattern p = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,16}$");
        Matcher m = p.matcher(pass);
        System.out.println(m.matches()+"---");
        return m.matches();
    }
}
