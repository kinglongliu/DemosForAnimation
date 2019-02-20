package cn.huanxiu.demosforanimation.widget;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import cn.huanxiu.demosforanimation.R;

/**
 * 作者：liujinlong
 * 时间：2018/12/25
 * 功能：
 */
public class WidgetActivity extends AppCompatActivity {

    private ProgressBarTwo progressBar;
    private ProgressBar progressBarOne;
    private PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        progressBar=findViewById(R.id.progress);
        progressBarOne=findViewById(R.id.progess_one);
        photoView=findViewById(R.id.photo_view);

        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(5000);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progerss=(float)animation.getAnimatedValue();
                progressBar.setProgerss(progerss);
                progressBarOne.setProgerss(progerss);
            }
        });
        valueAnimator.start();
    }

}
