package cn.huanxiu.demosforanimation.animation;

import android.animation.TypeEvaluator;

/**
 * 作者：liujinlong
 * 时间：2018/12/19
 * 功能：
 */
public class CharEvaluator implements TypeEvaluator<Character> {

    @Override
    public Character evaluate(float v, Character character, Character t1) {
        int startInt=(int)character;
        int endInt=(int)t1;
        int curInt=(int)(startInt+v*(endInt-startInt));
        char result=(char)curInt;
        return result;
    }
}
