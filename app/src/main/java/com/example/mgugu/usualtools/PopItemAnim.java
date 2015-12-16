package com.example.mgugu.usualtools;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.example.mgugu.R;

/**
 * Created by 王宗贤 on 2015/12/7.
 */
public class PopItemAnim {
    /**
     * 点击底部加号启动时的动画
     * @param view
     */
    public static void startImgPlusAnimator(View view){
//        不仅可以用于动画，还可以用于位置变化
//        rotationX rotationY alpha scaleX scaleY translationX translationY translationZ
        ObjectAnimator.ofFloat(view, "rotation",0F,45F).setDuration(400).start();
    }

    /**
     * 关闭popupwindow时底部加号的动画
     * @param view
     */
    public static void finishImgPlusPopAnimator(View view){
//        不仅可以用于动画，还可以用于位置变化
//        rotationX rotationY alpha scaleX scaleY translationX translationY translationZ
        ObjectAnimator.ofFloat(view, "rotation",45F,0F).setDuration(400).start();
    }

    /**
     * popupwindow中子view进入的动画
     * @param view
     */
    public static void startPopItemAnimatior(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationY",400.0f,-70.0f,0.0f);
        animator.setDuration(600);
//        animator.setStartDelay(200);//延时设置
        animator.start();
    }

    /**
     * popupwindow中子view退出的动画
     * @param view
     */
    public static void finishPopItemAnimatior(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationY",0.0f,1500.0f);
        animator.setDuration(200);
//        animator.setStartDelay(200);//延时设置
        animator.start();
    }

}
