package cn.kunggka.easyweather.ui.chooseArea;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.IOException;

import cn.kunggka.easyweather.EasyWeather;
import cn.kunggka.easyweather.db.City;
import cn.kunggka.easyweather.db.Province;
import cn.kunggka.easyweather.util.ActivityUtil;
import cn.kunggka.easyweather.util.ConstantUtil;
import cn.kunggka.easyweather.util.HttpUtil;
import cn.kunggka.easyweather.util.Json2DataUtil;
import cn.kunggka.easyweather.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaPresenter implements ChooseAreaContract.Presenter {
    
    private ChooseAreaContract.View view = null;
    private final String url = "http://guolin.tech/api/china";
    
    public ChooseAreaPresenter(ChooseAreaContract.View view){
        this.view = view;
        this.view.setPresenter(this);
    }
    
    @Override
    public void start() {
        view.showLoadingView();
    }
    
    public void qurreyFromServer(int type, Object object, String extraUrl) {
        view.showLoadingView();
        HttpUtil.sendOkHttpRequest(url + extraUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                view.hideLoadingView();
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShortToast(EasyWeather.getInstance(),"加载失败");
                    }
                });
            }
    
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                view.hideLoadingView();
                String data = response.body().string();
                ActivityUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (type){
                            case ConstantUtil.LEVEL_PROVINCE :
                                if(Json2DataUtil.handleProvinceResponse(data)){
                                    view.queryProvincesSuccess();
                                }
                                break;
                            case ConstantUtil.LEVEL_CITY :
                                if(Json2DataUtil.handleCityResponse(data,((Province)object).getId())){
                                    view.queryCitiesSuccess();
                                }
                                break;
                            case ConstantUtil.LEVEL_COUNTRY :
                                if(Json2DataUtil.handleCountryResponse(data,((City)object).getId())){
                                    view.queryCountriesSuccess();
                                }
                                break;
                        }
                    }
                });
                
            }
        });
    }
    
}
