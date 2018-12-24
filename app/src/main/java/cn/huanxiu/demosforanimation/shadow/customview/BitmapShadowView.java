package cn.huanxiu.demosforanimation.shadow.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/24
 * 功能：
 */
public class BitmapShadowView extends View {

    private Paint mPaint;
    private Bitmap mBitmap,mAlphaBmp;

    public BitmapShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jingdong);
        mAlphaBmp = mBitmap.extractAlpha();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=200;
        int height=width*mAlphaBmp.getWidth()/mAlphaBmp.getHeight();

        mPaint.setColor(Color.GRAY);
        mPaint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(mAlphaBmp,null,new Rect(10,10,width,height),mPaint);

        canvas.translate(-5,-5);
        mPaint.setMaskFilter(null);
        canvas.drawBitmap(mBitmap,null,new Rect(0,0,width,height),mPaint);
    }
}
