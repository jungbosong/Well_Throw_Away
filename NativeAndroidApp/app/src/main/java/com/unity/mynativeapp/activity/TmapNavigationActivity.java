package com.unity.mynativeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import com.unity.mynativeapp.POJO.Feature;
import com.unity.mynativeapp.POJO.Route;
import com.unity.mynativeapp.R;
import com.unity.mynativeapp.Unity.UnityViewActivity;

import java.util.ArrayList;
import java.util.List;

public class TmapNavigationActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {
    boolean isUnityLoaded = false;
    TMapView tMapView = null;       // T Map View
    TMapGpsManager tMapGPS = null;  // T Map GPS
    String api_key = "l7xx9a6a0f893c67471099e573946a28c3c7";    // 발급받은 TMAP API Key
    Route route;
    String latitudes, longitudes, name;
    TextView textView;

    //protected UnityPlayer mUnityPlayer;
    //Integer pointCount = 0;

    double latitude, longitude;             // 현재위치 위도, 경도 임시저장
    double end_latitude, end_longitude;     // 쓰레기통 위도, 경도 임시저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap_navigation);

        tMapView = new TMapView(this);  // T Map View
        tMapView.setSKTMapApiKey(api_key);      // API Key
        Intent intent = getIntent();
        route = (Route)intent.getSerializableExtra("Route");
        latitude = (double) intent.getSerializableExtra("start_lat");
        longitude = (double) intent.getSerializableExtra("start_lon");
        end_latitude = (double) intent.getSerializableExtra("end_lat");
        end_longitude = (double) intent.getSerializableExtra("end_lon");
        name = (String)intent.getSerializableExtra("name");

        // Initial Setting
        tMapView.setZoomLevel(17);
        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setCompassMode(true);
        tMapView.setSightVisible(true);

        LinearLayout tmapPathView = (LinearLayout)findViewById(R.id.tmapPathView);
        tmapPathView.addView(tMapView);

        // 첫 중심위치 지정
        tMapView.setCenterPoint(longitude, latitude);
        tMapView.setLocationPoint(longitude, latitude);

        // GPS using T Map
        tMapGPS = new TMapGpsManager(this);

        // Initial Setting
        tMapGPS.setMinTime(1000);
        tMapGPS.setMinDistance(10);
        tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER);
        tMapGPS.OpenGps();

        // 지도 중심 위치 지정
        tMapView.setTrackingMode(true);

        // 쓰레기통 위치 마커 생성
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_pin);
        TMapMarkerItem markerItem = new TMapMarkerItem();
        markerItem.setIcon(bitmap);                 // bitmap를 Marker icon으로 사용
        markerItem.setPosition(0.5f, 1.0f);  // Marker img의 position
        markerItem.setTMapPoint(new TMapPoint(end_latitude, end_longitude));         // Marker의 위치
        tMapView.addMarkerItem("trashcan", markerItem);

        textView = findViewById(R.id.trashcanname);
        textView.setText(name);

        // 경로 그리기
        drawPath(route);

        // mUnityPlayer = new UnityPlayer(this);
        /*
        int glesMode = mUnityPlayer.getSettings().getInt("gles_mode", 1);
        boolean tureColor888 = false;
        mUnityPlayer.init(glesMode, tureColor888);
        */
    }

    public String makeLatitudes(String latitudes, double latitude)
    {
        return latitudes + "," + latitude;
    }

    public String makeLongitude(String longitudes, double longitude)
    {
        return longitudes + "," + longitude;
    }

    // 경로 그리는 함수
    public void drawPath(Route route)
    {
        ArrayList<TMapPoint> alTMapPoint = new ArrayList<TMapPoint>();

        // 경로 데이터 얻어오기
        //List<Map<String, Object>> features = route.getTypeFeatures("Point");
        List<Feature> features = route.getFeatures();
        int featureCount = features.size();
        for(int i = 0; i < featureCount; i++)
        {
            double latitude, longitude;
            ArrayList coordinates;

            //Log.e("Load Route Test","featureGeometryType: " + features.get(i).getGeometry().getType() + "\n");

            if(features.get(i).getGeometry().getType().equals("Point"))
            {
                //pointCount++;

                coordinates = features.get(i).getGeometry().getCoordinates();
                longitude = Double.parseDouble(coordinates.get(0).toString());
                latitude = Double.parseDouble(coordinates.get(1).toString());

                latitudes = makeLatitudes(latitudes, latitude);
                longitudes = makeLongitude(longitudes, longitude);

                Log.e("Load Route Test", "Point Count: " + features.get(i).getProperties().getPointIndex() + "\n");
                Log.e("Load Route Test","coordinates: " + coordinates.get(0).toString() + "\t" + coordinates.get(1).toString() + "\n");
                Log.e("Load Route Test","latitude: " + latitude + "\t" + "longitude: " + longitude + "\n");
                alTMapPoint.add( new TMapPoint(latitude, longitude));
            }
        }

        TMapPolyLine tMapPolyLine = new TMapPolyLine();
        tMapPolyLine.setLineColor(Color.RED);
        tMapPolyLine.setLineWidth(5);
        Log.e("Load Route Test", "alTMapPoint.size: " + alTMapPoint.size() + "\n");
        for(int i=0; i<alTMapPoint.size(); i++) {
            tMapPolyLine.addLinePoint( alTMapPoint.get(i) );
            Log.e("Load Route Test","alTMapPoint: " + alTMapPoint.get(i) + "\n");
        }
        tMapView.addTMapPolyLine("Line1", tMapPolyLine);

    }

    public void clickLoadUnity(View v) {
        isUnityLoaded = true;

        //mUnityPlayer.UnitySendMessage("RouteManager", "PrintPointCount", pointCount.toString());

        Intent intent = new Intent(this, UnityViewActivity.class);
        intent.putExtra("Route", route);
        intent.putExtra("Latitudes", latitudes);
        intent.putExtra("Longitudes", longitudes);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) isUnityLoaded = false;
    }
    public void unloadUnity(Boolean doShowToast) {
        if(isUnityLoaded) {
            Intent intent = new Intent(this, UnityViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("doQuit", true);
            startActivity(intent);
            isUnityLoaded = false;
        }
        else if(doShowToast) showToast("Show Unity First");
    }

    public void btnUnloadUnity(View v) {
        unloadUnity(true);
    }

    public void showToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onLocationChange(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        tMapView.setLocationPoint(longitude, latitude);                 // 지도 현재위치 지정
        tMapView.setCenterPoint(longitude, latitude, true);   // 지도 중심좌표 이동
    }
}