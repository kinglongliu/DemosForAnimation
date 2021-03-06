package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/27
 * 功能：
 */
public class ProgressBar extends View{

    private Paint mPaint;
    private int mPaintBackColor,mPaintForColor,mPaintTextBackColor;
    private float mBorderStrokeWidth = 5;
    private int measureWidth;
    private float progressWidth;
    private float progress;
    private int baseLineY=50;

    public ProgressBar(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaintBackColor = getResources().getColor(R.color.grey);
        mPaintForColor= getResources().getColor(R.color.colorAccent);
        mPaintTextBackColor=getResources().getColor(R.color.white);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setProgerss(float progerss){
        this.progress=progerss;
        progressWidth=measureWidth*progerss;
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
        mPaint.setColor(mPaintBackColor);
        canvas.drawLine(0,baseLineY,measureWidth,baseLineY,mPaint);

        mPaint.setColor(mPaintForColor);
        canvas.drawLine(0,baseLineY,progressWidth,baseLineY,mPaint);

        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        float textWidth=mPaint.measureText((int)(progress*100)+"%");
        float textHeigth=getTextHeigth();
        float rectWidth=mPaint.measureText("100%");
        if(textWidth+progressWidth<measureWidth){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPaintTextBackColor);
            canvas.drawRect(new RectF(progressWidth,baseLineY-textHeigth/2,progressWidth+rectWidth,baseLineY+textHeigth/2),mPaint);

            mPaint.setColor(mPaintForColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawText((int)(progress*100)+"%",progressWidth+(rectWidth-textWidth)/2,baseLineY+textHeigth/4,mPaint);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPaintTextBackColor);
            canvas.drawRect(new RectF(measureWidth-rectWidth,baseLineY-textHeigth/2,measureWidth,baseLineY+textHeigth/2),mPaint);

            mPaint.setColor(mPaintForColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawText((int)(progress*100)+"%",measureWidth-rectWidth+(rectWidth-textWidth)/2,baseLineY+textHeigth/4,mPaint);
        }
    }

    private float getTextHeigth(){
        Paint.FontMetricsInt fm=mPaint.getFontMetricsInt();
        int top=baseLineY+fm.top;
        int bottom=baseLineY+fm.bottom;
        int height=bottom-top;
        return height;
    }
}
