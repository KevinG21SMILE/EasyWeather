package cn.kunggka.easyweather.ui.weather;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.kunggka.easyweather.R;
import cn.kunggka.easyweather.base.BaseActivity;
import cn.kunggka.easyweather.model.DayForcast;
import cn.kunggka.easyweather.model.Weather;
import cn.kunggka.easyweather.ui.chooseArea.ChooseAreaFragment;
import cn.kunggka.easyweather.ui.chooseArea.ChooseAreaPresenter;
import cn.kunggka.easyweather.util.Json2DataUtil;
import cn.kunggka.easyweather.util.ToastUtil;

public class WeatherActivity extends BaseActivity implements WeatherContract.View {
    public WeatherContract.Presenter mPresenter;
    
    @BindView(R.id.weather_layout)
    ScrollView scrollView;
    
    @BindView(R.id.title_city)
    TextView titleCity;
    
    @BindView(R.id.update_time_text)
    TextView updateTime;
    
    @BindView(R.id.degree_text)
    TextView degreeText;
    
    @BindView(R.id.txt_text)
    TextView txtText;
    
    @BindView(R.id.aqi_text)
    TextView aqiText;
    
    @BindView(R.id.pm25_text)
    TextView pm25Text;
    
    @BindView(R.id.comfort_text)
    TextView comfortText;
    
    @BindView(R.id.carwash_text)
    TextView carwashText;
    
    @BindView(R.id.sport_text)
    TextView sportText;
    
    @BindView(R.id.forecast_layout)
    LinearLayout forecastLayout;
    
    @BindView(R.id.pic_img)
    ImageView picImg;
    
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout refreshLayout;
    
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    
    @BindView(R.id.nav_btn)
    Button navBtn;
    
    private SharedPreferences sharedPreferences;
    private String weatherId;
    private ChooseAreaFragment chooseAreaFragment;
    
    
    @Override
    protected void init() {
        mPresenter = new WeatherPresenter(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (chooseAreaFragment == null) {
            chooseAreaFragment = ChooseAreaFragment.newInstance();
            new ChooseAreaPresenter(chooseAreaFragment);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, chooseAreaFragment);
            transaction.commit();
        }
        /*String weatherString = sharedPreferences.getString("weather", null);
        if (weatherString != null) {
            Weather weather = Json2DataUtil.handleWeatherResponse(weatherString);
            weatherId = weather.basic.id;
            showWeatherInfo(weather);
        }else{*/
            weatherId = getIntent().getStringExtra("weather_id");
            scrollView.setVisibility(View.INVISIBLE);
            mPresenter.requestWeather(weatherId);
        //}
        String bingPic = sharedPreferences.getString("bing_pic", null);
        if (bingPic == null) {
            mPresenter.requestImg();
        }else{
            Glide.with(this).load(bingPic).into(picImg);
        }
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.showShortToast(WeatherActivity.this,weatherId);
                mPresenter.requestWeather(weatherId);
            }
        });
        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    
    @Override
    public int layoutId() {
        return R.layout.activity_weather;
    }
    
    
    @Override
    public void requestWeatherSuccessed(Weather weather,String content) {
        if (weather != null && "ok".equals(weather.status)) {
            /*if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            }
            sharedPreferences
                    .edit()
                    .putString("weather",content)
                    .apply();*/
            refreshLayout.setRefreshing(false);
            showWeatherInfo(weather);
            weatherId = weather.basic.id;
        }else{
            requestWeatherFailed("获取天气信息失败");
        }
        
    }
    
    @Override
    public void requestWeatherFailed(String msg) {
        ToastUtil.showShortToast(this,msg);
        refreshLayout.setRefreshing(false);
    }
    
    @Override
    public void requestImgSuccessed(String url) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        }
        sharedPreferences
                .edit()
                .putString("bing_pic", url)
                .apply();
        Glide.with(this).load(url).into(picImg);
    }
    
    @Override
    public void requestImgFailed(String msd) {
        ToastUtil.showShortToast(this,msd);
    }
    
    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
    
    }
    
    @Override
    public void showLoadingView() {
    
    }
    
    @Override
    public void hideLoadingView() {
    
    }
    
    public void showWeatherInfo(Weather weather) {
        titleCity.setText(weather.basic.city);
        updateTime.setText(weather.basic.update.updateTime.split(" ")[1]);
        degreeText.setText(weather.now.temperature + "℃");
        txtText.setText(weather.now.more.txt);
        forecastLayout.removeAllViews();
        for (DayForcast dayForcast : weather.dayForcastList ) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_forecast, null);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(dayForcast.date);
            infoText.setText(dayForcast.more.txt);
            maxText.setText(dayForcast.temperature.max);
            minText.setText(dayForcast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.aqiCity.aqi);
            pm25Text.setText(weather.aqi.aqiCity.pm25);
        }
        comfortText.setText("舒适度: " + weather.suggestion.comfort.txt);
        carwashText.setText("洗车指数: " + weather.suggestion.carWash.txt);
        sportText.setText("运动建议: " + weather.suggestion.sport.txt);
        scrollView.setVisibility(View.VISIBLE);
    }
}
