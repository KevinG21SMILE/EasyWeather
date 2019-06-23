package cn.kunggka.easyweather;

import android.app.Application;

import org.litepal.LitePal;

public class EasyWeather extends Application {
    private static EasyWeather easyWeather;
    private  static int mainThread;
    
    @Override
    public void onCreate() {
        super.onCreate();
        easyWeather = this;
        mainThread = android.os.Process.myTid();
        LitePal.initialize(this);
    }
    
    public static synchronized EasyWeather getInstance(){
        return easyWeather;
    }
    
    public static int getMainTread() {
        return mainThread;
    }
    
}
