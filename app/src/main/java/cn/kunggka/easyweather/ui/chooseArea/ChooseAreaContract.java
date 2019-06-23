package cn.kunggka.easyweather.ui.chooseArea;

import cn.kunggka.easyweather.base.BasePresenter;
import cn.kunggka.easyweather.base.BaseView;
import cn.kunggka.easyweather.db.City;
import cn.kunggka.easyweather.db.Province;

public interface ChooseAreaContract {
    interface View extends BaseView<Presenter>{
        void queryProvincesSuccess();
        void queryCitiesSuccess();
        void queryCountriesSuccess();
    }
    
    interface Presenter extends BasePresenter{
        void qurreyFromServer(int type, Object object,String extraUrl);
    }
}
