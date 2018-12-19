package cn.huanxiu.demosforanimation.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.animation.customview.FallingBallImageView;
import cn.huanxiu.demosforanimation.animation.customview.LoadingImageView;
import cn.huanxiu.demosforanimation.base.BaseActivity;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：
 */
public class PropertyAnimationActivity extends BaseActivity implements View.OnClickListener{

    private LoadingImageView loadingImageView;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    private TextView textView;
    private ValueAnimator valueAnimator1,valueAnimator2,valueAnimator3,valueAnimator4,valueAnimator5;
    private ObjectAnimator objectAnimator1,objectAnimator2;
    private AnimatorSet animatorSet;
    private ImageView imgBall;
    private FallingBallImageView fallingBallImageView;

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
        btn4=findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5=findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        imgBall=findViewById(R.id.img_ball);
        btn6=findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        fallingBallImageView=findViewById(R.id.img_fall_ball);
        btn7=findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
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

    private ValueAnimator doCharacterAnimation(){
        ValueAnimator animator=ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                char text=(Character)valueAnimator.getAnimatedValue();
                btn4.setText(String.valueOf(text));
            }
        });
        animator.start();
        return animator;
    }

    private ValueAnimator doFallingBallAnimation(){
        ValueAnimator animator=ValueAnimator.ofObject(new FallingBallEvaluator(),new Point(0,imgBall.getTop()),new Point(500,800));
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point=(Point)valueAnimator.getAnimatedValue();
                imgBall.layout(point.x,point.y,point.x+imgBall.getWidth(),point.y+imgBall.getHeight());
            }
        });
        animator.start();
        return animator;
    }

    private AnimatorSet doSetAnimation(){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(btn6,"alpha",1,0,1);
        ObjectAnimator objectAnimator2=ObjectAnimator.ofFloat(btn6,"translationY",0,300,0);
        ObjectAnimator objectAnimator3=ObjectAnimator.ofFloat(btn6,"translationY",0,400,0);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(objectAnimator,objectAnimator2,objectAnimator3);
        animatorSet.setDuration(1000);
        animatorSet.start();
        return animatorSet;
    }

    private ObjectAnimator doMyObjectAnimator(){
        ObjectAnimator objectAnimator=ObjectAnimator.ofObject(fallingBallImageView,
                "fallingPos",new FallingBallEvaluator(),new Point(0,fallingBallImageView.getTop()),new Point(500,800));
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        return objectAnimator;
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
            case R.id.btn4:
                if(valueAnimator4==null){
                    valueAnimator4=doCharacterAnimation();
                } else {
                    valueAnimator4.cancel();
                    valueAnimator4=null;
                }
                break;
            case R.id.btn5:
                if(valueAnimator5==null){
                    imgBall.setVisibility(View.VISIBLE);
                    valueAnimator5=doFallingBallAnimation();
                } else {
                    valueAnimator5.cancel();
                    valueAnimator5=null;
                    imgBall.setVisibility(View.GONE);
                }
                break;
            case R.id.btn6:
                if(animatorSet==null){
                    animatorSet=doSetAnimation();
                } else {
                    animatorSet.cancel();
                    animatorSet=null;
                }
                break;
            case R.id.btn7:
                if(objectAnimator2==null){
                    fallingBallImageView.setVisibility(View.VISIBLE);
                    objectAnimator2=doMyObjectAnimator();
                } else {
                    fallingBallImageView.setVisibility(View.INVISIBLE);
                    objectAnimator2.cancel();
                    objectAnimator2=null;
                }
                break;
        }
    }
}
