package com.example.whj02.baiduditu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.whj02.R;

/**
 * Created by 浮生丶 on 2018/5/25 0025.
 */

public class BaiDuDITuActivity extends AppCompatActivity implements View.OnClickListener {
    //主要是用Map对象来改变坐标用的.清华大学坐标,创建LatLng(40.009424, 116.332556);
    LatLng xh=new LatLng(39.997743,116.316176);
    //北京大学坐标.   (39.997743, 116.316176);
    LatLng HC=new LatLng(40.046835,116.308195);
    //天安门坐标     (39.915112, 116.403963)
    //40.046835,116.308195

    private MapView mapView_mv;

    private BaiduMap baiduMap;

    private Button zoomOut_bt;
    private Button zoomIn_bt;
    private Button setCompassEnabled_bt;
    private Button rotate_bt;
    private Button overlook_bt;
    private Button newLatLng_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.bai_du_di_tu);
        //控件初始化
        initView();
        //运行后自动隐藏百度的logo
        View child = mapView_mv.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        //1.0,使用MapVIew对象,控制是否显示MapVIew上面的按钮
        //设置不显示缩放控件    该方法的参数是boolen,false是不显示缩放控件
        mapView_mv.showScaleControl(false);
        //设置不显示比例尺控件    该方法的参数是boolen,false是不显示比例尺控件
        mapView_mv.showZoomControls(false);

        //2.获取获取最小（3）、最大缩放级别（21）
        //获取最小的级别,map对象.getMinZoomLevel();
        float minZoomLevel = baiduMap.getMinZoomLevel();
        System.out.println("地图最小的级别minZoomLevel:  "+minZoomLevel);
        //获取最大的级别,map对象.getMaxZoomLevel();
        float maxZoomLevel = baiduMap.getMaxZoomLevel();
        System.out.println("地图最大的级别maxZoomLevel:  "+maxZoomLevel);

        //3.进入地图,默认的中心点是天安门,改变地图中心点为新华大学.
        //使用MapStatusUpdateFactory工厂调用newLatLng方法,参数还是一个LatLng对象(其参数,先是维度,后是经度)
        MapStatusUpdate newLatLng = MapStatusUpdateFactory.newLatLng(xh);
        //BaiduMap对象调用setMapStatus方法往里面穿参数MapStatusUpdate对象.
        baiduMap.setMapStatus(newLatLng);

        //4.设置地图缩放为15,使用MapStatusUpdateFactory工厂调用zoomTo,得到MapStatusUpdate对象.
        MapStatusUpdate zoomTo = MapStatusUpdateFactory.zoomTo(15);
        //BaiduMap对象调用setMapStatus方法往里面穿参数(MapStatusUpdate)对象.
        baiduMap.setMapStatus(zoomTo);

    }

    /*
     * 5.通过点击事件,更新地图状态(就是让地图,放大缩小,旋转,俯仰,移动,隐藏指南针)
     * @param
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoomOut_bt:
                //缩小,使用MapStatusUpdateFactory工厂调用zoomOut方法,得到MapStatusUpdate
                MapStatusUpdate zoomOut = MapStatusUpdateFactory.zoomOut();
                //在用map对象.setMapStatus,把MapStatusUpdate对象传入.
                baiduMap.setMapStatus(zoomOut);
                break;
            case R.id.zoomIn_bt:
                //放大,使用MapStatusUpdateFactory工厂调用zoomIn,得到MapStatusUpdate
                MapStatusUpdate zoomIn = MapStatusUpdateFactory.zoomIn();
                //在用map对象.setMapStatus,把MapStatusUpdate对象传入.
                baiduMap.setMapStatus(zoomIn);
                break;
            case R.id.rotate_bt:
                //旋转（0 ~ 360），每次在原来的基础上再旋转30度
                // 获取当前地图的状态,map对象.getMapStatus,直接获取到MapStatus
                MapStatus mapStatus = baiduMap.getMapStatus();
                //然后MapStatus.rotate就可以拿到原来的旋转度数.
                float rotate = mapStatus.rotate;


                //通过new MapStatus.Builder得到MapStatus.Builder对象
                MapStatus.Builder builder = new MapStatus.Builder();
                //用MapStatus.Builder对象.rotate(原来旋转的度数+30);
                builder.rotate(rotate + 30);
                //用MapStatus.Builder对象.build,得到MapStatus对象
                MapStatus build = builder.build();
                //用使用MapStatusUpdateFactory工厂调用newMapStatus,把MapStatus对象传入.得到MapStatusUpdate
                MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(build);
                //map对象.setMapStatus,传入MapStatusUpdate对象
                baiduMap.setMapStatus(newMapStatus);

                break;
            case R.id.overlook_bt:
                //俯仰（0 ~ -45），每次在原来的基础上再俯仰-5度
                //获取当前地图的状态,map对象.getMapStatus,直接获取到MapStatus
                MapStatus mapStatus1 = baiduMap.getMapStatus();
                //然后MapStatus.overlook就可以拿到原来的俯仰度数.
                float overlook = mapStatus1.overlook;

                //通过new MapStatus.Builder得到MapStatus.Builder对象
                MapStatus.Builder builder1 = new MapStatus.Builder();
                //用MapStatus.Builder对象.overlook(原来俯仰的度数-5);
                builder1.overlook(overlook - 10);
                //用MapStatus.Builder对象.build,得到MapStatus对象
                MapStatus build1 = builder1.build();
                //用使用MapStatusUpdateFactory工厂调用newMapStatus,把MapStatus对象传入.得到MapStatusUpdate
                MapStatusUpdate newMapStatusOverlook = MapStatusUpdateFactory.newMapStatus(build1);
                ////map对象.setMapStatus,传入MapStatusUpdate对象
                baiduMap.setMapStatus(newMapStatusOverlook);
                break;
            case R.id.newLatLng_bt:
                //移动,用使用MapStatusUpdateFactory工厂调用newLatLng(LatLng参数);
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(HC);
                //map对象.animateMapStatus,第一个参数是mapStatusUpdate对象,第二个参数是动画执行多少秒.
                baiduMap.animateMapStatus(mapStatusUpdate,2000);
                break;
            case R.id.setCompassEnabled_bt:
                //.获取地图Ui控制器：隐藏指南针,Map对象.getUiSettings.setCompassEnabled, false是不显示指南针
                UiSettings uiSettings = baiduMap.getUiSettings();
                uiSettings.setCompassEnabled(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView_mv.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView_mv.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView_mv.onPause();
    }


    /**
     * 控件的初始化
     */
    private void initView() {
        mapView_mv = (MapView) findViewById(R.id.mapView_mv);
        //获取对MapVIew的控制类BaiduMap,MapView对象.getMap
        baiduMap = mapView_mv.getMap();

        zoomOut_bt = (Button) findViewById(R.id.zoomOut_bt);
        zoomIn_bt = (Button) findViewById(R.id.zoomIn_bt);
        rotate_bt = (Button) findViewById(R.id.rotate_bt);
        overlook_bt = (Button) findViewById(R.id.overlook_bt);
        newLatLng_bt = (Button) findViewById(R.id.newLatLng_bt);
        setCompassEnabled_bt = (Button) findViewById(R.id.setCompassEnabled_bt);

        zoomOut_bt.setOnClickListener(this);
        zoomIn_bt.setOnClickListener(this);
        rotate_bt.setOnClickListener(this);
        overlook_bt.setOnClickListener(this);
        newLatLng_bt.setOnClickListener(this);
        setCompassEnabled_bt.setOnClickListener(this);
    }

   /*
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MapStatusUpdate mapStatusUpdate = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                // 1)	缩小
                mapStatusUpdate = MapStatusUpdateFactory.zoomOut();
                break;
            case KeyEvent.KEYCODE_2:
                // 2)	放大
                mapStatusUpdate = MapStatusUpdateFactory.zoomIn();
                break;
            case KeyEvent.KEYCODE_3:
                // 获取当前地图的状态
                MapStatus currentMapStatus = baiduMap.getMapStatus();
                float rotate = currentMapStatus.rotate;
                System.out.println("rotate = " + rotate);

                // 3)	旋转（0 ~ 360），每次在原来的基础上再旋转30度
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.rotate(rotate + 30);
                MapStatus mapStatus = builder.build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                break;
            case KeyEvent.KEYCODE_4:
                // 4)	俯仰（0 ~ -45），每次在原来的基础上再俯仰-5度
                currentMapStatus = baiduMap.getMapStatus();
                float overlook = currentMapStatus.overlook;
                System.out.println("overlook = " + overlook);

                builder = new MapStatus.Builder();
                builder.overlook(overlook  -5);
                mapStatus = builder.build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                break;
            case KeyEvent.KEYCODE_5:
                // 5)	移动
                mapStatusUpdate = MapStatusUpdateFactory.newLatLng(HC);
                baiduMap.animateMapStatus(mapStatusUpdate, 4000);
                return true;
            case KeyEvent.KEYCODE_6:
                // 6.	获取地图Ui控制器：隐藏指南针
                baiduMap.getUiSettings().setCompassEnabled(false);	// 不显示指南针
                return true;
        }
        // 5.	更新地图状态
        baiduMap.setMapStatus(mapStatusUpdate);
        return super.onKeyDown(keyCode, event);
    }*/

}
