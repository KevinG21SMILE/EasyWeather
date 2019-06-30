package cn.kunggka.easyweather.ui.chooseArea;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.kunggka.easyweather.MainActivity;
import cn.kunggka.easyweather.R;
import cn.kunggka.easyweather.R2;
import cn.kunggka.easyweather.base.BaseFragment;
import cn.kunggka.easyweather.db.City;
import cn.kunggka.easyweather.db.Country;
import cn.kunggka.easyweather.db.Province;
import cn.kunggka.easyweather.ui.weather.WeatherActivity;
import cn.kunggka.easyweather.util.ConstantUtil;
import cn.kunggka.easyweather.util.LogUtil;

public class ChooseAreaFragment extends BaseFragment implements ChooseAreaContract.View{
    
    private ChooseAreaContract.Presenter mPresenter;
    
    @BindView(R2.id.back_button)
    Button  backButton;
    @BindView(R2.id.title_text)
    TextView titleText;
    @BindView(R2.id.list_view)
    ListView listView;
    
    private final String Tag = LogUtil.getSimpleName(this);
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int currenLevel = ConstantUtil.LEVEL_PROVINCE;
    private List<Province>provinceList;
    private List<City>cityList;
    private List<Country>countryList;
    private ProgressDialog progressDialog;
    private Province selectedProvince;
    private City selectedCity;
    
    
    @Override
    protected int layoutId() {
        return R2.layout.choose_area;
    }
    
    public static ChooseAreaFragment newInstance() {
        return new ChooseAreaFragment();
    }
    
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        queryProvinces();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currenLevel == ConstantUtil.LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities(selectedProvince);
                } else if (currenLevel == ConstantUtil.LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCountries(selectedCity);
                } else if (currenLevel == ConstantUtil.LEVEL_COUNTRY) {
                    String weatherId = countryList.get(position).getWeatherId();
                    if(getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.refreshLayout.setRefreshing(true);
                        activity.mPresenter.requestWeather(weatherId);
                    }
                    
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currenLevel == ConstantUtil.LEVEL_COUNTRY) {
                    queryCities(selectedProvince);
                } else if (currenLevel == ConstantUtil.LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
    }
    
    public void queryProvinces() {
        backButton.setVisibility(View.GONE);
        titleText.setText("中国");
        provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            currenLevel = ConstantUtil.LEVEL_PROVINCE;
        }else{
            mPresenter.qurreyFromServer(ConstantUtil.LEVEL_PROVINCE,null,"");
        }
    }
    
    public void queryCities(Province province) {
        backButton.setVisibility(View.VISIBLE);
        titleText.setText(province.getName());
        cityList = LitePal.where("ProvinceId like ?", "" + province.getId()).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getName());
            }
            adapter.notifyDataSetChanged();
            currenLevel = ConstantUtil.LEVEL_CITY;
        }else{
            LogUtil.d(Tag, "queryCities: province: " + province.getName() + "   code:" + province.getCode());
            mPresenter.qurreyFromServer(ConstantUtil.LEVEL_CITY , province,"/" + province.getCode());
        }
    }
    
    public void queryCountries(City city) {
        backButton.setVisibility(View.VISIBLE);
        titleText.setText(city.getName());
        countryList = LitePal.where("cityId like ?", "" + city.getId()).find(Country.class);
        if (countryList.size() > 0) {
            dataList.clear();
            for (Country country : countryList) {
                dataList.add(country.getName());
            }
            adapter.notifyDataSetChanged();
            currenLevel = ConstantUtil.LEVEL_COUNTRY;
        }else{
            LogUtil.d(Tag, "queryCountries: province: " + city.getName() + "   code:" + city.getCode());
            mPresenter.qurreyFromServer(ConstantUtil.LEVEL_COUNTRY , city,"/" + selectedProvince.getCode() + "/" + city.getCode());
        }
    }
    
    @Override
    public void setPresenter(ChooseAreaContract.Presenter presenter) {
        mPresenter = presenter;
    }
    
    @Override
    public void showLoadingView() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载..");
            progressDialog.setCanceledOnTouchOutside(true);
        }
        progressDialog.show();
    }
    
    @Override
    public void hideLoadingView() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    
    @Override
    public void queryProvincesSuccess() {
        queryProvinces();
    }
    
    @Override
    public void queryCitiesSuccess() {
        queryCities(selectedProvince);
    }
    
    @Override
    public void queryCountriesSuccess() {
        queryCountries(selectedCity);
    }
}
