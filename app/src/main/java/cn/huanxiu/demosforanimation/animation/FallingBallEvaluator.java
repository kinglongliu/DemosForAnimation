package cn.huanxiu.demosforanimation.animation;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 作者：liujinlong
 * 时间：2018/12/19
 * 功能：
 */
public class FallingBallEvaluator implements TypeEvaluator<Point> {
    private Point point=new Point();

    @Override
    public Point evaluate(float v, Point startValue, Point endValue) {
        point.x=(int)(startValue.x+v*(endValue.x-startValue.x));

        if(v*2<=1){
            point.y=(int)(startValue.y+v*2*(endValue.y-startValue.y));
        } else {
            point.y=endValue.y;
        }
        return point;
    }
}
