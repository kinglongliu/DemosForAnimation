package cn.huanxiu.demosforanimation.animation.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 作者：liujinlong
 * 时间：2018/12/21
 * 功能：
 */
public class AnimWaveView extends View{

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength=1200;
    private int dx;

    public AnimWaveView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int originY=300;
        int halfWavenLen=mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength+dx,originY);
        for(int i=-mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            mPath.rQuadTo(halfWavenLen/2,-100,halfWavenLen,0);
            mPath.rQuadTo(halfWavenLen/2,100,halfWavenLen,0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    private void init(){
        mPath=new Path();
        mPaint=new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        startAnim();
    }

    private void startAnim(){
        ValueAnimator animator=ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx=(int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
