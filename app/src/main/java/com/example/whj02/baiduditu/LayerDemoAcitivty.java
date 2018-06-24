package com.example.whj02.baiduditu;

import android.view.KeyEvent;

import com.baidu.mapapi.map.BaiduMap;

public class LayerDemoAcitivty extends BaseActivity {
/**
 * Created by 浮生丶 on 2018/5/25 0025.
 */
	/**
	 *onKeyDown,该方法是更加键盘的按键,完成不同的操作.
	 * @return
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//使用switch.进行判断
		switch (keyCode) {
			// 显示卫星图层
			case KeyEvent.KEYCODE_1:
				//使用baiduMap对象,调用setMapType方法,传入BaiduMap.MAP_TYPE_SATELLITE参数
				map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				//使用baiduMap对象,调用setTrafficEnabled();false是关闭交通层.
				map.setTrafficEnabled(false);
				break;
			// 显示普通图层
			case KeyEvent.KEYCODE_2:
				//使用baiduMap对象,调用setMapType方法,传入BaiduMap.MAP_TYPE_NORMAL参数._
				map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				//使用baiduMap对象,调用setTrafficEnabled();false是关闭交通层.
				map.setTrafficEnabled(false);
				break;
			// 显示交通图
			case KeyEvent.KEYCODE_3:
				//使用baiduMap对象,调用setTrafficEnabled();true是打开交通层.
				map.setTrafficEnabled(true);
				break;

			default:
				break;
		}

		return super.onKeyDown(keyCode, event);
	}
}
