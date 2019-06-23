package cn.kunggka.easyweather.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cn.kunggka.easyweather.EasyWeather;

import static org.litepal.Operator.getHandler;

public class ActivityUtil {
    
    public static void addFragment2Activity(FragmentManager fragmentManager, Fragment fragment, int layoutId) {
        if(fragment.isAdded()){
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutId, fragment);
        fragmentTransaction.commit();
    }
    /**
     * 判断当前是否运行在主线程
     *
     * @return
     */
    public static boolean isRunOnUiThread() {
        return EasyWeather.getMainTread() == android.os.Process.myTid();
    }
    
    /**
     * 保证当前的操作运行在UI主线程
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isRunOnUiThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }
}
