package com.example.mgugu.usualtools;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;

import com.example.mgugu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * popupwindow
 * Created by 王宗贤 on 2015/12/3.
 */
public class MPopupWindow {
    public static PopupWindow mPopupWindow;
    /**
     * 自定义popupwindow
     * @param activity 传入上下文
     * @param view 传入popupwindow中的布局文件
     * @param viewTouch 传入触发popupwindow的view
     * @param viewBackground 传入设置的popupwindow背景图片的view
     * @param list 传入关闭popupwindow时需要动画的view集合
     */
    public static void mPopupWindowSettle(Activity activity,View view,View viewTouch,View viewBackground,ArrayList<View> list){
        mPopupWindow=new PopupWindow(activity);
//        设置宽高
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        mPopupWindow.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        mPopupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.glass));
        mPopupWindow.setContentView(view);
//         点击Window以外的界面返回,必须在show之前设置
        mPopupWindow.setOutsideTouchable(true);
//      设置popWindow的显示和消失动画
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

//        在按键上面显示
        int[] location = new int[2];
        viewTouch.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(viewTouch, Gravity.BOTTOM, 0, 0);

        //设置背景为灰色
//        backgroundAlpha(0.5f, activity);

//        显示在某个按键上面
        viewBackground.setVisibility(View.VISIBLE);
        changeAlphaAnimation(0.0f,1.0f,viewBackground);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener(activity,viewBackground,list));

    }
    /**
     * 透明度动画
     * @param start 开始变化的透明度
     * @param end 结束变化的透明度
     * @param view 添加动画的view
     */
    public static void changeAlphaAnimation(float start, float end, View view){
        AlphaAnimation alphaAnimation=new AlphaAnimation(start,end);
        alphaAnimation.setDuration(400);
        view.setAnimation(alphaAnimation);
    }

//    /**
//     * 修改透明度
//     * @param bgAlpha
//     * @param activity
//     */
//    public static void backgroundAlpha(float bgAlpha, Activity activity) {
//        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        activity.getWindow().setAttributes(lp);
//    }

    /**
     * 监听popupwindow关闭
     */
    public static class poponDismissListener implements PopupWindow.OnDismissListener {
        private Activity mActivity;
        private View view;
        private List<View> list;
        public poponDismissListener(Activity mActivity,View view,ArrayList<View> list) {
            this.mActivity = mActivity;
            this.view=view;
            this.list=list;
        }

        @Override
        public void onDismiss() {
//            backgroundAlpha(1f, mActivity);

            PopItemAnim.finishImgPlusPopAnimator(list.get(0));

            changeAlphaAnimation(0.0f, 1.0f, list.get(1));
            list.get(1).setVisibility(View.VISIBLE);
            changeAlphaAnimation(0.0f, 1.0f, list.get(2));
            list.get(2).setVisibility(View.VISIBLE);
            changeAlphaAnimation(0.0f, 1.0f, list.get(3));
            list.get(3).setVisibility(View.VISIBLE);
            changeAlphaAnimation(0.0f, 1.0f, list.get(4));
            list.get(4).setVisibility(View.VISIBLE);

            changeAlphaAnimation(1.0f, 0.0f, view);
            view.setVisibility(View.GONE);//背景消失
        }
    }


}
