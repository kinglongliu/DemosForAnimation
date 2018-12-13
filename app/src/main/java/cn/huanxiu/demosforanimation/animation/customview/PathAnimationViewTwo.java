package cn.huanxiu.demosforanimation.animation.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：路径动画例子
 */
public class PathAnimationViewTwo extends View {

    private Paint mPaint;
    private int mPaintColor;
    private float mBorderStrokeWidth = 5;
    private Path mDstPath,mCirclePath;
    private PathMeasure mPathMeasure;
    private float mCurAnimValue;

    public PathAnimationViewTwo(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float stop=mPathMeasure.getLength()*mCurAnimValue;
        float start=0;
        if(mCurAnimValue>=0.5){
            start=(2*mCurAnimValue-1)*mPathMeasure.getLength();
        }
        mDstPath.reset();
        mPathMeasure.getSegment(start,stop,mDstPath,true);
        canvas.drawPath(mDstPath,mPaint);
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaintColor=getResources().getColor(R.color.colorPrimary);
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderStrokeWidth);

        mDstPath=new Path();
        mCirclePath=new Path();
        mCirclePath.addCircle(100,100,50,Path.Direction.CW);
        mPathMeasure=new PathMeasure(mCirclePath,true);

        ValueAnimator animator=ValueAnimator.ofFloat(0,1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurAnimValue=(Float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
