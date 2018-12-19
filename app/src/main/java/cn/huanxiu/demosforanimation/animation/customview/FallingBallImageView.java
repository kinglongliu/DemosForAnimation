package cn.huanxiu.demosforanimation.animation.customview;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 作者：liujinlong
 * 时间：2018/12/19
 * 功能：
 */
public class FallingBallImageView extends ImageView {

    public FallingBallImageView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void setFallingPos(Point pos){
        layout(pos.x,pos.y,pos.x+getWidth(),pos.y+getHeight());
    }
}
