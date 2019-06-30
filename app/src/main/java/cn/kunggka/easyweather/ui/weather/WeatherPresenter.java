package cn.kunggka.easyweather.ui.weather;

import android.preference.PreferenceManager;

import java.io.IOException;

import cn.kunggka.easyweather.model.Weather;
import cn.kunggka.easyweather.util.ActivityUtil;
import cn.kunggka.easyweather.util.HttpUtil;
import cn.kunggka.easyweather.util.Json2DataUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherPresenter implements WeatherContract.Presenter {
    private WeatherContract.View mView;
    private static String baseUrl = "http://guolin.tech/api/weather?cityid=";
    private static String key = "&key=bc0418b57b2d4918819d3974ac1285d9";
    private static String imgUrl = "http://guolin.tech/api/bing_pic";
    
    public WeatherPresenter(WeatherContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
    
    @Override
    public void start() {
    
    }
    
    
    @Override
    public void requestWeather(String weatherId) {
        String url = baseUrl + weatherId +key;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.requestWeatherFailed(e.toString());
                    }
                });
            }
    
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                Weather weather = Json2DataUtil.handleWeatherResponse(content);
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.requestWeatherSuccessed(weather,content);
                    }
                });
                requestImg();
            }
        });
    }
    
    @Override
    public void requestImg() {
        HttpUtil.sendOkHttpRequest(imgUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.requestImgFailed(e.toString());
                    }
                });
            }
    
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String pic = response.body().string();
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.requestImgSuccessed(pic);
                    }
                });
            }
        });
    }
}
