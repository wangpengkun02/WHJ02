package com.example.test2.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Fresco加载图片工具类
 * author:Created by WangZhiQiang on 2018/5/10.
 */
public class FrescoUtil {
    /**
     * 基础加载图片
     * @param url 图片路径
     * @param simpleDraweeView 控件
     */
    public static void setTu(String url,SimpleDraweeView simpleDraweeView){
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
    }
    /**
     * 渐进式加载图片
     * @param url 图片路径
     * @param simpleDraweeView 控件
     */
    public static void setJianJin(String url, SimpleDraweeView simpleDraweeView){
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }
    /**
     * 圆角图片
     * @param url 图片路径
     * @param simpleDraweeView 控件
     * @param radius 角度
     * @param color 描边线颜色
     * @param width  描边线宽度
     */
    public static void setYuanJiao(String url,SimpleDraweeView simpleDraweeView,float radius,int color,float width){
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0f) {
            roundingParams.setBorder(color, width);//描边线
        }
        roundingParams.setCornersRadius(radius);//总体圆角
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 圆角图片
     * 可控四角角度
     * @param url 图片路径
     * @param simpleDraweeView 控件
     * @param topLeft 左上角
     * @param topRight 右上角
     * @param bottomRight 右下角
     * @param bottomLeft 左下角
     * @param color 描边线颜色
     * @param width 描边线宽度
     */
    public static void setYuanJiao(String url,SimpleDraweeView simpleDraweeView,float topLeft, float topRight, float bottomRight, float bottomLeft,int color,float width){
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0f) {
            roundingParams.setBorder(color, width);//描边线
        }
        roundingParams.setCornersRadii(topLeft,topRight,bottomRight,bottomLeft);//各角不同圆角
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 圆形图片
     * @param url 图片路径
     * @param simpleDraweeView 控件
     * @param color 描边线颜色
     * @param width 描边线宽度
     */
    public static void setYuanQuan(String url,SimpleDraweeView simpleDraweeView,int color,float width){
        if(url==null){
            simpleDraweeView.setImageURI(url);
            return;
        }
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0f) {
            roundingParams.setBorder(color, width);//描边线
        }
        roundingParams.setRoundAsCircle(true);//圆形
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * Gif动态图片
     * @param url 图片路径
     * @param simpleDraweeView 控件
     */
    public static void setDongTu(String url,SimpleDraweeView simpleDraweeView){
        Uri uri = Uri.parse(url);
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)//设置为true将循环播放Gif动画
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller1);
    }
}
