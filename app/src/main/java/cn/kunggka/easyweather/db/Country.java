package cn.kunggka.easyweather.db;

import org.litepal.crud.LitePalSupport;

public class Country extends LitePalSupport {
    private int id;
    private String name;
    private int cityId;
    private String weatherId;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCityId() {
        return cityId;
    }
    
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    public String getWeatherId() {
        return weatherId;
    }
    
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
