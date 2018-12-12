package cn.huanxiu.demosforanimation.animation;

import android.view.View;
import android.widget.Button;
import cn.huanxiu.demosforanimation.R;
import cn.huanxiu.demosforanimation.base.BaseActivity;

/**
 * 作者：liujinlong
 * 时间：2018/12/10
 * 功能：
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener{

    private Button btnBujian,btnZhuzhen,btnShuxing,btnPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animation;
    }

    @Override
    protected void initView() {
        btnBujian=findViewById(R.id.btn_bujian);
        btnBujian.setOnClickListener(this);
        btnZhuzhen=findViewById(R.id.btn_zhuzhen);
        btnZhuzhen.setOnClickListener(this);
        btnShuxing=findViewById(R.id.btn_shuxing);
        btnShuxing.setOnClickListener(this);
        btnPath=findViewById(R.id.btn_path);
        btnPath.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bujian:
                startActivity(TweenAnimationActivity.class);
                break;
            case R.id.btn_zhuzhen:
                startActivity(FrameAnimationActivity.class);
                break;
            case R.id.btn_shuxing:
                startActivity(PropertyAnimationActivity.class);
                break;
            case R.id.btn_path:
                startActivity(PathAnimationActivity.class);
                break;
        }
    }
}
