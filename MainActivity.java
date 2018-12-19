package com.example.asus.gps2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

public class MainActivity extends Activity {

    //定位服务的客户端
    public LocationClient mLocationClient = null;
    private double latitude;
    private double lontitude;
    private String addr;
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;
    private static int LOCATION_COUTNS = 0;
    public BDLocation bdLocation = new BDLocation() ;
    private MyLocationListener myListener = new MyLocationListener();
    private TextView locationInfoTextView = null;
    private Button startButton = null;
    private Button button1 = null;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            if (location.getFloor() != null) {
//                // 当前支持高精度室内定位
//                String buildingID = location.getBuildingID();// 百度内部建筑物ID
//                String buildingName = location.getBuildingName();// 百度内部建筑物缩写
//                String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
//                mLocationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
////            }
            if (location == null) {
                return;
            }
            latitude = location.getLatitude();
            lontitude = location.getLongitude();
            addr = location.getAddrStr();    //获取详细地址信息
            country = location.getCountry();    //获取国家
            province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息
            StringBuffer sb = new StringBuffer(256);
            sb.append("北京时间 : ");
            sb.append(location.getTime());
//            sb.append("\nError code : ");
//            sb.append(location.getLocType());
            sb.append("\n百度经度 : ");
            sb.append(location.getLatitude());
            sb.append("\n百度纬度 : ");
            sb.append(location.getLongitude());
            sb.append("\n定位精度 : ");
            sb.append(location.getRadius());
            if (location.getFloor() != null) {
                // 当前支持高精度室内定位
                String buildingID = location.getBuildingID();// 百度内部建筑物ID
                String buildingName = location.getBuildingName();// 百度内部建筑物缩写
                String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
                //mLocationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
            }

            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nSpeed : ");
                sb.append(location.getSpeed());
                sb.append("\nSatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\n国家 : ");
                sb.append(location.getCountry());
                sb.append("\n省份 : ");
                sb.append(location.getProvince());
                sb.append("\n城市 : ");
                sb.append(location.getCity());
                sb.append("\n县区 : ");
                sb.append(location.getDistrict());
                sb.append("\n街道 : ");
                sb.append(location.getStreet());
                sb.append( "\n周边描述 :" + location.getLocationDescribe());    //获取位置描述信息
                List<Poi> list = location.getPoiList();
                if (list != null) {
                    sb.append( "\n\n发现周边 :\n");
                    for (Poi p : list) {
                        sb.append( " " + p.getName() + "\n");
                    }
                }
                //获取周边POI信息
                //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明)
            }
            LOCATION_COUTNS ++;
            sb.append("\n检查位置更新次数：");
            sb.append(String.valueOf(LOCATION_COUTNS));
            locationInfoTextView.setText(sb.toString());
        };

        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInfoTextView = (TextView) this.findViewById(R.id.textView);
        startButton = (Button) this.findViewById(R.id.button);
        button1 = (Button) this.findViewById(R.id.button1);
        mLocationClient = new LocationClient(getApplicationContext());
        //配置定位SDK各配置参数
        LocationClientOption option = new LocationClientOption();
        //设置定位模式，此为高精度模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置坐标类型，此为百度经纬度坐标系
        option.setCoorType("bd09ll");
        //设置扫描间隔
        option.setScanSpan(1000);
        //是否打开gps
        option.setOpenGps(true);
        option.setLocationNotify(true);
        //设置是否退出定位进程 true:不退出进程； false:退出进程，默认为true
        option.setIgnoreKillProcess(false);
        //设置是否进行异常捕捉 true:不捕捉异常；false:捕捉异常，默认为false
        option.SetIgnoreCacheException(false);
       //设置wifi缓存超时时间阈值，超过该阈值，首次定位将会主动扫描wifi以使得定位精准度提高，定位速度会有所下降，具体延时取决于wifi扫描时间，大约是1-3秒
        option.setWifiCacheTimeOut(5*60*1000);
        //设置是否允许模拟GPS true:允许； false:不允许，默认为false
        option.setEnableSimulateGps(false);

        option.setIsNeedAddress(true);
        //设置 LocationClientOption
        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        // 如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationPoiList(true);
        //可选，是否需要周边POI信息，默认为不需要，即参数为false
        //如果开发者需要获得周边POI信息，此处必须为true
        mLocationClient.setLocOption(option);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLocationClient == null) {
                    return;
                }
                if (mLocationClient.isStarted()) {
                    startButton.setText("Start");
                    mLocationClient.stop();
                }else {
                    startButton.setText("Stop");
                    mLocationClient.start();
                    /*
                     *当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
                     *调用requestLocation( )后，每隔设定的时间，定位SDK就会进行一次定位。
                     *如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
                     *返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
                     *定时定位时，调用一次requestLocation，会定时监听到定位结果。
                     */
                    mLocationClient.requestLocation();
                }
            }
        });

        mLocationClient.registerLocationListener(myListener);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapActivity.class);
                startActivity( intent );
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }
}
