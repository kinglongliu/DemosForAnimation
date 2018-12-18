package cn.huanxiu.demosforanimation.animation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.animation.customview.LoadingImageView;
import cn.huanxiu.demosforanimation.base.BaseActivity;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：
 */
public class PropertyAnimationActivity extends BaseActivity implements View.OnClickListener{

    private LoadingImageView loadingImageView;
    private Button btn1,btn2,btn3;
    private TextView textView;
    private ValueAnimator valueAnimator1,valueAnimator2,valueAnimator3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_animation;
    }

    @Override
    protected void initView() {
        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        loadingImageView=findViewById(R.id.loading);
        textView=findViewById(R.id.tv);
        btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
    }

    private ValueAnimator doAnimation(){
        ValueAnimator animator=ValueAnimator.ofInt(50,400);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new MyInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue=(Integer)valueAnimator.getAnimatedValue();
                textView.layout(textView.getLeft(),curValue,textView.getRight(),curValue+textView.getHeight());
            }
        });
        animator.start();
        return animator;
    }

    private ValueAnimator doArgbAnimation(){
        ValueAnimator animator=ValueAnimator.ofInt(0xffffff00,0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue=(Integer)valueAnimator.getAnimatedValue();
                btn3.setBackgroundColor(curValue);
            }
        });
        animator.start();
        return animator;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                if(valueAnimator2==null){
                    valueAnimator2=doAnimation();
                    textView.setVisibility(View.VISIBLE);
                }else {
                    valueAnimator2.cancel();
                    valueAnimator2=null;
                    textView.setVisibility(View.GONE);
                }
                break;
            case R.id.btn2:
                if(valueAnimator1==null){
                    valueAnimator1=loadingImageView.startAnimation();
                    loadingImageView.setVisibility(View.VISIBLE);
                }else {
                    valueAnimator1.cancel();
                    valueAnimator1=null;
                    loadingImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.btn3:
                if(valueAnimator3==null){
                    valueAnimator3=doArgbAnimation();
                }else {
                    valueAnimator3.cancel();
                    valueAnimator3=null;
                }
                break;
        }
    }
}
