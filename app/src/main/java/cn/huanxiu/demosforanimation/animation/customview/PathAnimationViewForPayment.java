package cn.huanxiu.demosforanimation.animation.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：支付宝支付动画
 * 技术点：getMatrix
 */
public class PathAnimationViewForPayment extends View {

    private Paint mPaint;
    private int mPaintColor;
    private float mBorderStrokeWidth = 5;
    private Path mDstPath, mCirclePath;
    private PathMeasure mPathMeasure;
    private float mCurAnimValue;
    private boolean mNext=false;

    public PathAnimationViewForPayment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mCurAnimValue<1){
            float stop = mPathMeasure.getLength() * mCurAnimValue;
            mPathMeasure.getSegment(0, stop, mDstPath, true);
        }else {
            if(!mNext){
                mNext=true;
                mPathMeasure.getSegment(0,mPathMeasure.getLength(),mDstPath,true);
                canvas.drawPath(mDstPath, mPaint);
                mPathMeasure.nextContour();
            }
            float stop=mPathMeasure.getLength()*(mCurAnimValue-1);
            mPathMeasure.getSegment(0,stop,mDstPath,true);
        }
        canvas.drawPath(mDstPath, mPaint);
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaintColor = getResources().getColor(R.color.colorPrimary);
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderStrokeWidth);
        mDstPath = new Path();
        mCirclePath = new Path();
        mCirclePath.addCircle(100, 100, 50, Path.Direction.CW);
        mCirclePath.moveTo(75,100);
        mCirclePath.lineTo(100,125);
        mCirclePath.lineTo(125,75);
        mPathMeasure = new PathMeasure(mCirclePath, false);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurAnimValue = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
