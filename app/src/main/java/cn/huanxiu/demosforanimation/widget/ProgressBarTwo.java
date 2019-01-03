package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private int mLineColor;
    private int mTextColor;
    private float mLineWidth;
    private float mTextLineWidth;
    private float textWidth;
    private int measureWidth;
    private float progressWidth;
    private float progress;
    private int baseLineY=50;
    private float mTextSize;

    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_line_color = Color.rgb(66, 145, 241);
    private final int default_line_width=5;
    private final int default_text_lint_width=1;
    private final int default_text_size=30;

    public ProgressBarTwo(Context context) {
        this(context, null);
    }

    public ProgressBarTwo(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ProgressBarTwo(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressBarTwo,
                defStyleAttr, 0);
        mTextColor=attributes.getColor(R.styleable.ProgressBarTwo_progress_text_color,default_text_color);
        mLineColor=attributes.getColor(R.styleable.ProgressBarTwo_progress_reached_color,default_line_color);
        mLineWidth=attributes.getDimension(R.styleable.ProgressBarTwo_progress_reached_bar_height,default_line_width);
        mTextLineWidth=attributes.getDimension(R.styleable.ProgressBarTwo_progress_text_line_height,default_text_lint_width);
        mTextSize=attributes.getDimension(R.styleable.ProgressBarTwo_progress_text_size,default_text_size);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(mTextLineWidth);
        mPaint.setTextSize(mTextSize);
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
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        canvas.drawLine(0,baseLineY,progressWidth,baseLineY,mPaint);

        mPaint.setStrokeWidth(mTextLineWidth);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
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
