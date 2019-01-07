package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import cn.huanxiu.demosforanimation.R;

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
    private Paint mPaint;

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
        Log.d("CircleImageView","mEdgeColor=="+mEdgeColor+"edgeWidth=="+edgeWidth+"radius=="+radius);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(edgeWidth);
        mPaint.setColor(mEdgeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,100,radius,mPaint);
        Drawable drawable=getDrawable();
        if(drawable!=null){
            Log.d("CircleImageView","drawable is not null");
        }
    }
}
