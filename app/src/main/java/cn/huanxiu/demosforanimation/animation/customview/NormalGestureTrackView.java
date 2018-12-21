package cn.huanxiu.demosforanimation.animation.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：liujinlong
 * 时间：2018/12/21
 * 功能：
 */
public class NormalGestureTrackView extends View {
    private Path mPath = new Path();
    private Paint mPaint;
    private float mPreX,mPreY;

    public NormalGestureTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mPreX=event.getX();
                mPreY=event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX=(mPreX+event.getX())/2;
                float endY=(mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX=event.getX();
                mPreY=event.getY();
                postInvalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
