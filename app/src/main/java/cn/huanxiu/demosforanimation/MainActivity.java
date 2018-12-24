package cn.huanxiu.demosforanimation;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.huanxiu.demosforanimation.animation.AnimationActivity;
import cn.huanxiu.demosforanimation.base.BaseActivity;
import cn.huanxiu.demosforanimation.shadow.ShadowActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button btnAnimation,btnShadow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnAnimation=findViewById(R.id.btn_animation);
        btnAnimation.setOnClickListener(this);
        btnShadow=findViewById(R.id.btn_shadow);
        btnShadow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_animation:
                startActivity(AnimationActivity.class);
                break;
            case R.id.btn_shadow:
                startActivity(ShadowActivity.class);
                break;
        }
    }
}
