package com.example.asus.gps2;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.MapView;


public class MapActivity extends Activity {

    private MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mMapView != null)
            mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if(mMapView != null)
            mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mMapView != null)
            mMapView.onPause();
    }

}
