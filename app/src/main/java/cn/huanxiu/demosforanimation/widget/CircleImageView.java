package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.utils.CommonUtil;

/**
 * 作者：liujinlong
 * 时间：2019/1/4
 * 功能：
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    private final int default_circle_radius=50;
    private final int default_circle_edge=3;
    private final int default_edge_color= Color.rgb(134,234,32);

    private int mEdgeColor;
    private float edgeWidth;
    private float radius;
    private Paint mPaint,mBorderPaint;
    private Shader mShader;
    private Bitmap mBitmap;


    public CircleImageView(Context context){
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public CircleImageView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageView,
                defStyle, 0);
        mEdgeColor=attributes.getColor(R.styleable.CircleImageView_circle_edge_color,default_edge_color);
        edgeWidth=attributes.getDimension(R.styleable.CircleImageView_circle_edge_width,default_circle_edge);
        radius=attributes.getDimension(R.styleable.CircleImageView_circle_radius,default_circle_radius);
        init();
    }

    private void init(){
        mBorderPaint=new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(edgeWidth);
        mBorderPaint.setColor(mEdgeColor);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mBitmap= CommonUtil.drawableToBitmap(getDrawable());
        if(mBitmap!=null){
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=Math.min(getMeasuredWidth(),getMeasuredHeight());
        int heigth=width;
        setMeasuredDimension(width,heigth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable()==null)
            return;
        float radius=getWidth()/2;
        canvas.drawCircle(radius,radius,radius-(int)edgeWidth,mPaint);
        canvas.drawCircle(radius,radius,radius-(int)edgeWidth,mBorderPaint);
    }
}
