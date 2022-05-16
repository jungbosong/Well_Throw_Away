package com.unity.mynativeapp.tmapAPI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.unity.mynativeapp.R;
import com.unity.mynativeapp.tmapAPI.POJO.Route;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APITestActivity extends AppCompatActivity{

    public void testResponse(View view){
        String TAG = "REST API TEST";
        //test data (t map 지도연결 후 관련데이터 수집)
        String appKey = "l7xx6eef69fa1aad46c19eb598ba67dfc0b8";
        double startX = 126.92365493654832;
        double startY = 37.556770374096615;
        int angle = 1;
        int speed = 60;
        String endPoiId = "334852";
        double endX = 126.92432158129688;
        double endY = 37.55279861528311;
        String passList = "126.92774822,37.55395475";
        String startName = "%EC%B6%9C%EB%B0%9C";
        String endName = "%EB%B3%B8%EC%82%AC";
        int searchOption = 0;
        String resCoordType = "WGS84GEO";

        // Retrofit 인스턴스로 인터페이스 객체 구현
        RetrofitInterface service1 = RetrofitClient.getInterface();
        // 사용할 메소드 선언
        Call<Route> call = service1.getfeatures(appKey, startX, startY, angle, speed, endPoiId,
                endX, endY, passList, startName, endName, searchOption, resCoordType);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response){
                Log.e(TAG, "log test");
                if(response.isSuccessful()){
                    // 정상적으로 통신이 성공 된 경우
                    Route result = response.body();
                    Log.d(TAG, "onResponse: 성공, 결과\n"+ result.toString());
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                // 통신 실패(인터넷 끊김, 예외 발생 등 시스템적인 이유)
                Log.e(TAG, "onFailure: "+t.getMessage());
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitest);
    }
}
