package cn.kunggka.easyweather.model;

import com.google.gson.annotations.SerializedName;

public class DayForcast {
    public String date;
    @SerializedName("cond") public More more;
    public class More{
        @SerializedName("txt_d") public String txt;
    }
    @SerializedName("tmp") public Temperature temperature;
    public class Temperature{
        public String max;
        public String min;
    }
}
