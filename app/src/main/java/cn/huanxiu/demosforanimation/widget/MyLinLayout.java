package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 作者：liujinlong
 * 时间：2018/12/25
 * 功能：
 */
public class MyLinLayout extends ViewGroup {

    public MyLinLayout(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight=MeasureSpec.getMode(heightMeasureSpec);
        int measureWidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode=MeasureSpec.getMode(heightMeasureSpec);

        int height=0;
        int width=0;
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childHight=child.getMeasuredHeight();
            int childWidth=child.getMeasuredWidth();
            height+=childHight;
            width=Math.max(childWidth,width);
        }
        setMeasuredDimension((measureWidthMode==MeasureSpec.EXACTLY)?measureWidth:width,(measureHeightMode==MeasureSpec.EXACTLY)?measureHeight:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top=0;
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            int childHeight=child.getMeasuredHeight();
            int childWidth=child.getMeasuredWidth();
            child.layout(0,top,childWidth,top+childHeight);
            top+=childHeight;
        }
    }
}
