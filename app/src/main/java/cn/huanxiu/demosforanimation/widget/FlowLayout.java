package cn.huanxiu.demosforanimation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：liujinlong
 * 时间：2018/12/26
 * 功能：
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;
        int height = 0;
        int width = 0;

        int count = getChildCount();
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //如果忘记重写generateLayoutParams，则hild.getLayoutParams()将不是MarginLayoutParams的实例
            //在强制转换时就会出错，此时我们把左右间距设置为0，但由于在计算布局宽高时没有加上间距值，就是计算出的宽高要比实际小，所以是onLayout时就会出错
            MarginLayoutParams lp = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams) {
                lp = (MarginLayoutParams) child
                        .getLayoutParams();
            }else{
                lp = new MarginLayoutParams(0,0);
            }
            int childWidth = child.getMeasuredWidth() + lp.leftMargin +lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > measureWidth){
                //需要换行
                width = Math.max(lineWidth,width);
                height += lineHeight;
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else{
                // 否则累加值lineWidth,lineHeight取最大高度
                lineHeight = Math.max(lineHeight,childHeight);
                lineWidth += childWidth;
            }

            //最后一行是不会超出width范围的，所以要单独处理
            if (i == count -1){
                height += lineHeight;
                width = Math.max(width,lineWidth);
            }

        }
        //当属性是MeasureSpec.EXACTLY时，那么它的高度就是确定的，
        // 只有当是wrap_content时，根据内部控件的大小来确定它的大小时，大小是不确定的，属性是AT_MOST,此时，就需要我们自己计算它的应当的大小，并设置进去
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth
                : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int top=0,left=0;
        for (int i=0; i<count;i++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;

            if (childWidth + lineWidth >getMeasuredWidth()){
                //如果换行,当前控件将跑到下一行，从最左边开始，所以left就是0，而top则需要加上上一行的行高，才是这个控件的top点;
                top += lineHeight;
                left = 0;
                //同样，重新初始化lineHeight和lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else{
                lineHeight = Math.max(lineHeight,childHeight);
                lineWidth += childWidth;
            }
            //计算childView的left,top,right,bottom
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc =lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc, tc, rc, bc);
            //将left置为下一子控件的起始点
            left+=childWidth;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }
}
