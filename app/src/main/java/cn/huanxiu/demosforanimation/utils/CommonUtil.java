package cn.huanxiu.demosforanimation.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：
 */
public class CommonUtil {

    /**
     * @param context 动态设置状态栏透明 实现沉浸式
     */
    public static void StatusBarBgTransparent(Activity context) {
        //android7.0通知栏灰色问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(context.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {

            }
        }
        //透明状态栏
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
