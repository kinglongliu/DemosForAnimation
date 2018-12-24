package cn.huanxiu.demosforanimation.shadow.customview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：liujinlong
 * 时间：2018/12/24
 * 功能：
 */
public class BlurMaskFilterView extends View {

    private Paint mPaint;
    public BlurMaskFilterView(Context context, AttributeSet attrs){
        super(context,attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint=new Paint();
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.INNER));
        canvas.drawCircle(100,100,50,mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(250,100,50,mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.NORMAL));
        canvas.drawCircle(400,100,50,mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.OUTER));
        canvas.drawCircle(550,100,50,mPaint);
    }
}
