package cn.huanxiu.demosforanimation.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.utils.CommonUtil;
import cn.huanxiu.demosforanimation.utils.ZTLUtils;

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCommonSetting();
        initView();
        new ZTLUtils(this).setTranslucentStatus();
    }

    public void initCommonSetting() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#000000"));
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
    }

    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 返回当前界面布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

}
