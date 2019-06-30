package cn.kunggka.easyweather.model;

import com.google.gson.annotations.SerializedName;

public class Basic {
    public String city;
    public String id;
    public Update update;
    public class Update{
        @SerializedName("loc") public String updateTime;
        
    }
}
