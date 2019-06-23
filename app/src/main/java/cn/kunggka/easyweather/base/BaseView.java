package cn.kunggka.easyweather.base;

public interface BaseView <T extends BasePresenter>{
    void setPresenter(T presenter);
    void showLoadingView();
    void hideLoadingView();
}
