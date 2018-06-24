package com.example.whj02.baiduditu;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.example.whj02.R;

/**

 * Created by 浮生丶 on 2018/5/25 0025.

 * function:创建一个BaseActivity类,以后在创建百度地图的Activity时,继承BaseActivity.
 */
public class BaseActivity extends Activity {

    protected MapView mapView;
    /** 地图控制器，用以控制地图的移动、旋转、放大缩小等等 */
    protected BaiduMap map;
    /** 清华大学*/
    protected LatLng qhPos = new LatLng(40.009424,116.332556);
    /** 北京坐标 */
    protected LatLng bjPos = new LatLng(39.997743,116.316176);
    /** 天安门坐标 */
    protected LatLng tamPos = new LatLng(39.915112,116.403963);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        // 获取地图控件引用
        mapView = (MapView) findViewById(R.id.mapView_mv);
        map = mapView.getMap();	// 获取地图控制器

        // 1.	隐藏缩放按钮、比例尺
        mapView.showScaleControl(true);	// 设置比例尺是否显示
        mapView.showZoomControls(true);	// 设置缩放按钮是否显示

        // 2.	获取获取最小（3）、最大缩放级别（20）
        float minZoomLevel = map.getMinZoomLevel();
        float maxZoomLevel = map.getMaxZoomLevel();
        System.out.println("minZoomLevel = " + minZoomLevel + ", maxZoomLevel = " + maxZoomLevel);

        // 3.	设置地图中心点为清华大学
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(qhPos);
        map.setMapStatus(mapStatusUpdate);

        // 4.	设置地图缩放为15
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
        map.setMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

}
