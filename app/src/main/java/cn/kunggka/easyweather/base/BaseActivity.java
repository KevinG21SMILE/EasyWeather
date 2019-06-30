package cn.kunggka.easyweather.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    
    
    private Unbinder unbinder;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar();
        View view = LayoutInflater.from(this).inflate(layoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        setContentView(view);
        init();
    }
    
    public void showStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow()
                    .setStatusBarColor(Color.TRANSPARENT);
        }
    }
    
    protected abstract void init();
    
    public abstract int layoutId();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
