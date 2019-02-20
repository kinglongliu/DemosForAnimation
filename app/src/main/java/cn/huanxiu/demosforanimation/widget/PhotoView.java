package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 作者：liujinlong
 * 时间：2019/2/13
 * 功能：摘自鸿洋大神博客 https://blog.csdn.net/lmj623565791/article/details/39480503
 * 摘要：要实现PhotoView，主要是通过手势处理来实现，通过ScaleGestureDetector来处理两指的缩放
 *       用GestureDetector来实现双击事件的处理，用OnGlobalLayoutListener来处理图片的位置，
 *       用OnTouchListener来处理放大之后拖动图片，需要注意的是对图片的边界判断与计算
 *
 */
public class PhotoView extends AppCompatImageView implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener,ViewTreeObserver.OnGlobalLayoutListener{

    private String TAG=PhotoView.class.getSimpleName();
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mImageMatrix;
    public static final float SCALE_MAX = 3.0f;
    private static final float SCALE_MID = 1.5f;
    private float initScale = 1.0f;
    private boolean once = true;
    private float mLastX;
    private float mLastY;

    private boolean isCanDrag;
    private int lastPointerCount;
    private boolean isCheckTopAndBottom = true;
    private boolean isCheckLeftAndRight = true;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        setOnTouchListener(this);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
        mImageMatrix = new Matrix();
        mScaleGestureDetector=new ScaleGestureDetector(getContext(),this);
        mGestureDetector=new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG,"onDoubleTap");
                float x=e.getX();
                float y=e.getY();
                if(getScale()<SCALE_MID){
                    PhotoView.this.postDelayed(new ScaleRunnable(SCALE_MID,x,y),15);
                } else {
                    PhotoView.this.postDelayed(new ScaleRunnable(initScale,x,y),15);
                }
                return true;
            }
        });
    }

    /**
     * 根据图片的宽和高以及屏幕的宽和高，对图片进行缩放以及移动至屏幕的中心。
     * 如果图片很小，那就正常显示，不放大了~
     */
    @Override
    public void onGlobalLayout() {
        if(once){
            Log.d(TAG,"onGlobalLayout");
            Drawable d=getDrawable();
            if(d==null)
                return;
            int width=getWidth();
            int height=getHeight();
            // 图片的宽和高
            int dw=d.getIntrinsicWidth();
            int dh=d.getIntrinsicHeight();
            Log.d(TAG,"width=="+width+"--heigth=="+height+"--dw=="+dw+"--dh=="+dh);
            float scale=1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if(dw > width && dh <= height){
                scale=width*1.0f/dw;
            }

            if(dh > height && dw<width){
                scale=height*1.0f/dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if(dw>width&&dh>height){
                scale=Math.min(width*1.0f/dw,height*1.0f/dh);
            }
            initScale=scale;
            Log.d(TAG,"--initScale=="+initScale);
            mImageMatrix.postTranslate((width-dw)/2,(height-dh)/2);
            mImageMatrix.postScale(scale,scale,getWidth()/2,getHeight()/2);
            setImageMatrix(mImageMatrix);
            once=false;
        }
    }

    private class ScaleRunnable implements Runnable{
        static final float BIGGER=1.07f;
        static final float SMALLER=0.93f;
        private float mTargetScale;
        private float tmpScale;
        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        public ScaleRunnable(float targetScale, float x,float y){
            this.mTargetScale=targetScale;
            this.x=x;
            this.y=y;
            if(getScale()<mTargetScale){
                tmpScale=BIGGER;
            } else {
                tmpScale=SMALLER;
            }
        }

        @Override
        public void run() {
            mImageMatrix.postScale(tmpScale,tmpScale,x,y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mImageMatrix);

            final float currentScale = getScale();
            // 如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale)) || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                PhotoView.this.postDelayed(this, 16);
            } else {
                // 设置为目标的缩放比例
                final float deltaScale = mTargetScale / currentScale;
                mImageMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mImageMatrix);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x=0,y=0;
        int pointerCount=event.getPointerCount();
        for(int i=0;i<pointerCount;i++){
            x+=event.getX(i);
            y+=event.getY(i);
        }
        x=x/pointerCount;
        y=y/pointerCount;

        if(pointerCount!=lastPointerCount){
            isCanDrag=false;
            mLastX=x;
            mLastY=y;
        }
        lastPointerCount=pointerCount;
        RectF rectF=getMatrixRectF();

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dx=x-mLastX;
                float dy=y-mLastY;

                if(!isCanDrag){
                    isCanDrag=isCanDrag(dx,dy);
                }

                if(isCanDrag){
                    if(getDrawable()!=null){
                        isCheckLeftAndRight=isCheckTopAndBottom=true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if(rectF.width()<getWidth()){
                            dx=0;
                            isCheckLeftAndRight=false;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if(rectF.height()>getHeight()){
                            dy=0;
                            isCheckTopAndBottom=false;
                        }
                        mImageMatrix.postTranslate(dx,dy);
                        checkMatrixBounds();
                        setImageMatrix(mImageMatrix);
                    }
                }
                mLastX=x;
                mLastY=y;
                break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    lastPointerCount=0;
                        break;
        }
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();
        Log.d(TAG,"onScale=="+scaleFactor+"--"+scale);
        if(getDrawable()==null)
            return true;

        if((scale<SCALE_MAX && scaleFactor>1.0f) || scale>initScale && scaleFactor<1.0f) {
            if(scaleFactor*scale<initScale){
                scaleFactor=initScale/scale;
            }

            if(scaleFactor*scale>SCALE_MAX){
                scaleFactor=SCALE_MAX/scale;
            }

            mImageMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mImageMatrix);
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
    private float getScale() {
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        Log.d(TAG,"getScale=="+values[Matrix.MSCALE_X]);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        mImageMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mImageMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow==");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow==");
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 是否是推动行为
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= 0;
    }

    private void checkMatrixBounds(){
        RectF rect=getMatrixRectF();

        float deltaX=0,deltaY=0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        mImageMatrix.postTranslate(deltaX, deltaY);
    }
}
