package cn.kunggka.easyweather.ui.chooseArea;

import cn.kunggka.easyweather.base.BasePresenter;
import cn.kunggka.easyweather.base.BaseView;

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
