package cn.huanxiu.demosforanimation.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.animation.customview.FallingBallImageView;
import cn.huanxiu.demosforanimation.animation.customview.LoadingImageView;
import cn.huanxiu.demosforanimation.animation.customview.MyTextView;
import cn.huanxiu.demosforanimation.base.BaseActivity;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：
 */
public class PropertyAnimationActivity extends BaseActivity implements View.OnClickListener{

    private LoadingImageView loadingImageView;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    private Button btnItem1,btnItem2,btnItem3,btnItem4,btnMenu,btnItem5;
    private TextView textView,textView8;
    private ValueAnimator valueAnimator1,valueAnimator2,valueAnimator3,valueAnimator4,valueAnimator5;
    private ObjectAnimator objectAnimator2,objectAnimator1,objectAnimator3,objectAnimator4;
    private AnimatorSet animatorSet;
    private ImageView imgBall,imgPhone;
    private FallingBallImageView fallingBallImageView;
    private boolean isMenuOpen=false;
    private MyTextView myTextView;

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
        btnItem1=findViewById(R.id.item1);
        btnItem2=findViewById(R.id.item2);
        btnItem3=findViewById(R.id.item3);
        btnItem4=findViewById(R.id.item4);
        btnMenu=findViewById(R.id.menu);
        btnMenu.setOnClickListener(this);
        btnItem1.setOnClickListener(this);
        btnItem2.setOnClickListener(this);
        btnItem3.setOnClickListener(this);
        btnItem4.setOnClickListener(this);
        btnItem5=findViewById(R.id.item5);
        btnItem5.setOnClickListener(this);
        textView8=findViewById(R.id.btn8);
        textView8.setOnClickListener(this);
        myTextView=findViewById(R.id.myTextView);
        myTextView.setOnClickListener(this);
        imgPhone=findViewById(R.id.img_phone);
        imgPhone.setOnClickListener(this);
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

    private void doOpenMenu(View view,int index,int total,int radius){
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

        double degress=Math.toRadians(90)/(total-1)*index;
        int translationX=-(int)(radius*Math.sin(degress));
        int translationY=-(int)(radius*Math.cos(degress));
        AnimatorSet set=new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view,"translationX",0,translationX)
        ,ObjectAnimator.ofFloat(view,"translationY",0,translationY),
                ObjectAnimator.ofFloat(view,"scaleX",0f,1f),
                ObjectAnimator.ofFloat(view,"scaleY",0f,1f),
                ObjectAnimator.ofFloat(view,"alpha",0f,1f));
        set.setDuration(500).start();
    }

    private void doCloseMenu(View view,int index,int total,int radius){
        if(view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

        double degress=Math.toRadians(90)/(total-1)*index;
        int translationX=-(int)(radius*Math.sin(degress));
        int translationY=-(int)(radius*Math.cos(degress));
        AnimatorSet set=new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view,"translationX",translationX,0)
                ,ObjectAnimator.ofFloat(view,"translationY",translationY,0),
                ObjectAnimator.ofFloat(view,"scaleX",1f,0.1f),
                ObjectAnimator.ofFloat(view,"scaleY",1f,0.1f),
                ObjectAnimator.ofFloat(view,"alpha",1f,0f));
        set.setDuration(500).start();
    }

    private void openMenu(){
        doOpenMenu(btnItem1,0,5,300);
        doOpenMenu(btnItem2,1,5,300);
        doOpenMenu(btnItem3,2,5,300);
        doOpenMenu(btnItem4,3,5,300);
        doOpenMenu(btnItem5,4,5,300);
    }

    private void closeMenu(){
        doCloseMenu(btnItem1,0,5,300);
        doCloseMenu(btnItem2,1,5,300);
        doCloseMenu(btnItem3,2,5,300);
        doCloseMenu(btnItem4,3,5,300);
        doCloseMenu(btnItem5,4,5,300);
    }

    private ObjectAnimator valuesHolderAnimator(){
        PropertyValuesHolder rotationHolder=PropertyValuesHolder.ofFloat("Rotation",60f,-60f,40f,-40f,-20f,20f,10f,-10f,0f);
        PropertyValuesHolder alphaHolder=PropertyValuesHolder.ofFloat("alpha",0.1f,1f,0.1f,1f);
        ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(textView8,rotationHolder,alphaHolder);
        animator.setDuration(3000);
        animator.start();
        return  animator;
    }

    private ObjectAnimator valuesHolderCharactorAnimator(){
        PropertyValuesHolder charHolder=PropertyValuesHolder.ofObject("CharText",new CharEvaluator(),new Character('A'),new Character('Z'));
        ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(myTextView,charHolder);
        animator.setDuration(3000);
        animator.start();
        return  animator;
    }

    private ObjectAnimator doKeyFrameAnimation(){
        Keyframe frame0=Keyframe.ofFloat(0f,0);
        Keyframe frame1=Keyframe.ofFloat(0.1f,-20f);
        Keyframe frame2=Keyframe.ofFloat(0.2f,20f);
        Keyframe frame3=Keyframe.ofFloat(0.3f,-20f);
        Keyframe frame4=Keyframe.ofFloat(0.4f,20f);
        Keyframe frame5=Keyframe.ofFloat(0.5f,-20f);
        Keyframe frame6=Keyframe.ofFloat(0.6f,20f);
        Keyframe frame7=Keyframe.ofFloat(0.7f,-20f);
        Keyframe frame8=Keyframe.ofFloat(0.8f,20f);
        Keyframe frame9=Keyframe.ofFloat(0.9f,-20f);
        Keyframe frame10=Keyframe.ofFloat(1f,0);
        frame3.setInterpolator(new BounceInterpolator());
        PropertyValuesHolder frameHolder=PropertyValuesHolder.ofKeyframe("rotation",
                frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        Keyframe scaleXframe0=Keyframe.ofFloat(0f,1);
        Keyframe scaleXframe1=Keyframe.ofFloat(0.1f,1.1f);
        Keyframe scaleXframe2=Keyframe.ofFloat(0.9f,1.1f);
        Keyframe scaleXframe3=Keyframe.ofFloat(0f,1);
        PropertyValuesHolder frameHolderScaleX=PropertyValuesHolder.ofKeyframe("ScaleX",
                scaleXframe0,scaleXframe1,scaleXframe2,scaleXframe3);

        Keyframe scaleYframe0=Keyframe.ofFloat(0f,1);
        Keyframe scaleYframe1=Keyframe.ofFloat(0.1f,1.1f);
        Keyframe scaleYframe2=Keyframe.ofFloat(0.9f,1.1f);
        Keyframe scaleYframe3=Keyframe.ofFloat(0f,1);
        PropertyValuesHolder frameHolderScaleY=PropertyValuesHolder.ofKeyframe("ScaleY",
                scaleYframe0,scaleYframe1,scaleYframe2,scaleYframe3);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(imgPhone,frameHolder,frameHolderScaleX,frameHolderScaleY);
        objectAnimator.setDuration(1000);
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
            case R.id.menu:
                if(!isMenuOpen){
                    isMenuOpen=true;
                    openMenu();
                } else {
                    isMenuOpen=false;
                    closeMenu();
                }
                break;
            case R.id.btn8:
                if(objectAnimator1==null){
                    objectAnimator1=valuesHolderAnimator();
                } else {
                    objectAnimator1.cancel();
                    objectAnimator1=null;
                }
                break;
            case R.id.myTextView:
                if(objectAnimator3==null){
                    objectAnimator3=valuesHolderCharactorAnimator();
                } else {
                    objectAnimator3.cancel();
                    objectAnimator3=null;
                }
                break;
            case R.id.img_phone:
                if(objectAnimator4==null){
                    objectAnimator4=doKeyFrameAnimation();
                } else {
                    objectAnimator4.cancel();
                    objectAnimator4=null;
                }
                break;
        }
    }
}
