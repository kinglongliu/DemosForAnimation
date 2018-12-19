package cn.huanxiu.demosforanimation.animation.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者：liujinlong
 * 时间：2018/12/19
 * 功能：
 */
public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void setCharText(Character character){
        setText(String.valueOf(character));
    }
}
