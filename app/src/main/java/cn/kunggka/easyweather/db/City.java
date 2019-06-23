package cn.kunggka.easyweather.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {
    private int id;
    private String name;
    private int code;
    private int ProvinceId;
    
    
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
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getProvinceId() {
        return ProvinceId;
    }
    
    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

}
