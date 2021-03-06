package com.unity.mynativeapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;
import com.unity.mynativeapp.R;

import java.util.ArrayList;

public class SetLocationActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback{

    String api_key ="l7xx9a6a0f893c67471099e573946a28c3c7";
    TMapView tMapView = null;
    TMapGpsManager tMapGPS = null;
    TMapMarkerItem markerItem = null;
    Button set_button;
    FloatingActionButton here_button;

    String name;
    boolean check;
    double latitude, longitude;
    double set_latitude, set_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(api_key);

        // Initial Setting
        tMapView.setZoomLevel(17);
        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        //tMapView.setTrackingMode(false);

        // T Map View Using Linear Layout
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutSetTmap);
        linearLayoutTmap.addView(tMapView);
        set_button = (Button) findViewById(R.id.set_btn);
        here_button = (FloatingActionButton) findViewById(R.id.locationHere);

        // Request For GPS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // GPS using T Map
        tMapGPS = new TMapGpsManager(this);

        // Initial Setting
        tMapGPS.setMinDistance(1000);
        tMapGPS.setMinDistance(10);
        tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER);
        tMapGPS.OpenGps();

        // ?????? ?????? ?????? ??????
        tMapView.setTrackingMode(true);

        name = getIntent().getStringExtra("name");
        check = getIntent().getBooleanExtra("check", false);

        // ?????? ?????? ?????? - ?????? ????????? ??????????????? ?????? ?????? (FragmentTrashcanAdapter)
        if(check==true){
            // ????????? name ??????
            name = getIntent().getStringExtra("name");
            // ???????????? ????????? ?????? ??????
            double t_lat = getIntent().getDoubleExtra("latitude", 0.0);
            double t_lon = getIntent().getDoubleExtra("longitude", 0.0);
            tMapView.setCenterPoint(t_lon, t_lat);
            addMarker(new TMapPoint(t_lat, t_lon));
        }

        // ???????????? ?????? ??? ?????? ??????
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapClickPoint, PointF pointF) {
                addMarker(tMapClickPoint);
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapClickPoint, PointF pointF) {
                return false;
            }
        });

        // ?????? ?????? ??? ???????????? ??????, ???????????? ??????
        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ???????????? ??????
                Intent result_intent = new Intent();
                result_intent.putExtra("name", name);
                result_intent.putExtra("latitude", set_latitude);
                result_intent.putExtra("longitude", set_longitude);
                // ?????? activity(fragment) ??????
                setResult(Activity.RESULT_OK, result_intent);   //?????? ??????
                finish();   // ???????????? ??????
            }
        });

        // ???????????? ?????? ?????? ??? ?????? ???????????? ?????????
        here_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ???????????? ??????
                tMapView.setCenterPoint(longitude, latitude, true);
            }
        });

    }

    @Override
    public void onLocationChange(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        tMapView.setLocationPoint(longitude, latitude);
        tMapView.setCenterPoint(longitude, latitude);
    }

    // ????????? ?????? ??????
    public void addMarker(TMapPoint point){
        tMapView.removeAllMarkerItem();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_pin);
        markerItem = new TMapMarkerItem();
        markerItem.setIcon(bitmap);                 // bitmap??? Marker icon?????? ??????
        markerItem.setPosition(0.5f, 1.0f);  // Marker img??? position
        markerItem.setTMapPoint(point);             // Marker ?????? ??????
        tMapView.addMarkerItem("set_marker", markerItem);   // ????????? ?????? ??????

        // ????????? ?????? ??????
        set_latitude = point.getLatitude();
        set_longitude =  point.getLongitude();
    }
}