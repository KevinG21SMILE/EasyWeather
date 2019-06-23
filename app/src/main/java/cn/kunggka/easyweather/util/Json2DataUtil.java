package cn.kunggka.easyweather.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.kunggka.easyweather.db.City;
import cn.kunggka.easyweather.db.Country;
import cn.kunggka.easyweather.db.Province;

public class Json2DataUtil {
    private static String Tag = Json2DataUtil.class.getSimpleName().substring(0,10);
    
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray provinces = new JSONArray(response);
                for (int i = 0; i < provinces.length(); i++) {
                    JSONObject jsonObject = provinces.getJSONObject(i);
                    Province province = new Province();
                    province.setCode(jsonObject.getInt("id"));
                    province.setName(jsonObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.w(Tag, "handleProvinceResponse: exception " + e.toString());
            }
        }
        return false;
    }
    
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray cities = new JSONArray(response);
                for (int i = 0; i < cities.length(); i++) {
                    JSONObject jsonObject = cities.getJSONObject(i);
                    City city = new City();
                    city.setName(jsonObject.getString("name"));
                    city.setCode(jsonObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.w(Tag, "handleCityResponse: exception " + e.toString());
            }
        }
        return false;
    }
    
    public static boolean handleCountryResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray countries = new JSONArray(response);
                for (int i = 0; i < countries.length(); i++) {
                    JSONObject jsonObject = countries.getJSONObject(i);
                    Country country = new Country();
                    country.setName(jsonObject.getString("name"));
                    country.setCityId(cityId);
                    country.setWeatherId(jsonObject.getString("weather_id"));
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.w(Tag, "handleCountryResponse: exception " + e.toString());
            }
        }
        return false;
    }
}
