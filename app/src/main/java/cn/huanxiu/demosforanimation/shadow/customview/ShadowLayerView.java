package cn.huanxiu.demosforanimation.shadow.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class ShadowLayerView extends View {

    private Paint mPaint=new Paint();
    private Bitmap mDogBmp;

    public ShadowLayerView(Context context, AttributeSet attrs){
        super(context,attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(25);
        mPaint.setAntiAlias(true);
        mPaint.setShadowLayer(1,20,20,Color.GRAY);
        mDogBmp= BitmapFactory.decodeResource(getResources(), R.drawable.dog);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawText("哈哈",50,100,mPaint);
        canvas.drawCircle(200,100,50,mPaint);
        canvas.drawBitmap(mDogBmp,null, new Rect(300,50,300+mDogBmp.getWidth(),50+mDogBmp.getHeight()),mPaint);
    }

}
