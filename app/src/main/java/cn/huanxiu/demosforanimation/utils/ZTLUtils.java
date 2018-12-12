package cn.huanxiu.demosforanimation.utils;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import cn.huanxiu.demosforanimation.R;


/**
 * 作者：liujinlong
 * 时间：2018/9/5
 * 功能：透明状态栏工具类
 */
public class ZTLUtils {

    Activity activity;

    public ZTLUtils(Activity activity) {
        this.activity = activity;
    }


    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            this.activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemStatusManager tintManager = new SystemStatusManager(this.activity);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
            this.activity.getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

}
