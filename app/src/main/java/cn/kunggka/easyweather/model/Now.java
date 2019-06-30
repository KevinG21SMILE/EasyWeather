package cn.kunggka.easyweather.model;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp") public String temperature;
    @SerializedName("cond") public More more;
    public class More{
        public String txt;
    }
    
}
