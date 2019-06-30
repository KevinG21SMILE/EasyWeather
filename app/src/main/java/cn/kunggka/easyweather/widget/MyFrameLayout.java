package cn.kunggka.easyweather.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import cn.kunggka.easyweather.util.ToastUtil;

public class MyFrameLayout extends FrameLayout {
    public Context mContext;
    public MyFrameLayout( Context context) {
        super(context);
    }
    
    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onChildViewAdded(View parent, View child) {
                requestApplyInsets();
                setFitsSystemWindows(true);
                ToastUtil.showShortToast(context,"requestApplyInsets");
            }
    
            @Override
            public void onChildViewRemoved(View parent, View child) {
        
            }
        });
    }
    
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchApplyWindowInsets(insets);
        }
        ToastUtil.showShortToast(mContext,"onApplyWindowInsets");
        return insets;
    }
}
