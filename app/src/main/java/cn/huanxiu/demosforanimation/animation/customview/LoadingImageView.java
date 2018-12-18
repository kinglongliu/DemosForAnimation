package cn.huanxiu.demosforanimation.animation.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/18
 * 功能：
 */
public class LoadingImageView extends ImageView {

    private int mTop;
    private int mCurImgIndex=0;
    private static int mImgCount=3;
    private ValueAnimator valueAnimator;


    public LoadingImageView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mTop=top;
    }

    public ValueAnimator startAnimation(){
        return init();
    }



    private ValueAnimator init(){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,100,0);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer dx=(Integer)valueAnimator.getAnimatedValue();
                setTop(mTop-dx);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                setImageDrawable(getResources().getDrawable(R.drawable.ic_cat));
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                mCurImgIndex++;
                switch (mCurImgIndex%mImgCount){
                    case 0:
                        setImageDrawable(getResources().getDrawable(R.drawable.ic_cat));
                        break;
                    case 1:
                        setImageDrawable(getResources().getDrawable(R.drawable.ic_dog));
                        break;
                    case 2:
                        setImageDrawable(getResources().getDrawable(R.drawable.ic_tuzi));
                        break;
                }
            }
        });
        valueAnimator.start();

        return valueAnimator;
    }
}
