package cn.kunggka.easyweather.util;

import android.util.Log;

public class LogUtil {
    private final static String TAG_PREFIX = "k_";
    private static boolean isDebug = true;
    
    public static void d(String tag, String msg) {
        if(isDebug){
            Log.d(TAG_PREFIX + tag, msg);
        }
    }
    
    public static void w(String tag, String msg) {
        if(isDebug){
            Log.w(TAG_PREFIX + tag, msg);
        }
    }
    
    public static String getSimpleName(Object object) {
        return object.getClass().getSimpleName().substring(0,10);
    }
}
