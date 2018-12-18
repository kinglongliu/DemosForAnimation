package cn.huanxiu.demosforanimation.animation;

import android.animation.TimeInterpolator;

/**
 * 作者：liujinlong
 * 时间：2018/12/18
 * 功能：
 */
public class MyInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float v) {
        return 1-v;
    }
}
