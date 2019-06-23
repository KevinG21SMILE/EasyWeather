package cn.kunggka.easyweather.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    
    
    private Unbinder unbinder;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        setContentView(layoutId());
    }
    
    public abstract int layoutId();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
