package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者：liujinlong
 * 时间：2018/12/27
 * 功能：
 */
public class ProgressBar extends View{

    public ProgressBar(Context context, AttributeSet attrs){
        super(context,attrs);
        Log.d("ProgressBar","jkdjdjd--");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        Log.d("ProgressBar","measureWidth--"+measureWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
