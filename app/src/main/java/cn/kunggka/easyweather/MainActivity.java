package cn.kunggka.easyweather;

import android.os.Bundle;

import cn.kunggka.easyweather.base.BaseActivity;
import cn.kunggka.easyweather.ui.chooseArea.ChooseAreaFragment;
import cn.kunggka.easyweather.ui.chooseArea.ChooseAreaPresenter;
import cn.kunggka.easyweather.util.ActivityUtil;

public class MainActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChooseAreaFragment fragment = ChooseAreaFragment.newInstance();
        ActivityUtil.addFragment2Activity(getSupportFragmentManager(), fragment,R.id.container);
        new ChooseAreaPresenter(fragment);
    }
    
    @Override
    protected void init() {
    
    }
    
    @Override
    public void showStatusBar() {
    
    }
    
    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }
}
