package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/27
 * 功能：
 */
public class ProgressBarTwo extends View{

    private Paint mPaint;
    private int mPaintForColor;
    private float mBorderStrokeWidth = 5,textWidth;
    private int measureWidth;
    private float progressWidth;
    private float progress;
    private int baseLineY=50;

    public ProgressBarTwo(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaintForColor= getResources().getColor(R.color.colorAccent);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        textWidth=mPaint.measureText("100%");
    }

    public void setProgerss(float progerss){
        this.progress=progerss;
        progressWidth=(measureWidth-textWidth)*progerss;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth=MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mBorderStrokeWidth);
        mPaint.setColor(mPaintForColor);
        canvas.drawLine(0,baseLineY,progressWidth,baseLineY,mPaint);

        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        float textHeigth=getTextHeigth();
        canvas.drawText((int)(progress*100)+"%",progressWidth,baseLineY+textHeigth/4,mPaint);
    }

    private float getTextHeigth(){
        Paint.FontMetricsInt fm=mPaint.getFontMetricsInt();
        int top=baseLineY+fm.top;
        int bottom=baseLineY+fm.bottom;
        int height=bottom-top;
        return height;
    }
}
