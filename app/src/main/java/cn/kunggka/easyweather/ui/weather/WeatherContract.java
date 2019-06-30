package cn.kunggka.easyweather.ui.weather;

import cn.kunggka.easyweather.base.BasePresenter;
import cn.kunggka.easyweather.base.BaseView;
import cn.kunggka.easyweather.model.Weather;

public interface WeatherContract {
    interface View extends BaseView<WeatherContract.Presenter>{
        void requestWeatherSuccessed(Weather weather, String content);
    
        void requestWeatherFailed(String msg);
    
        void requestImgSuccessed(String url);
    
        void requestImgFailed(String msd);
    }
    
    
    interface Presenter extends BasePresenter{
        void requestWeather(String weatherId);
    
        void requestImg();
    }
}
