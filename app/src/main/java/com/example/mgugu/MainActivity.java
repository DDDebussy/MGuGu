package com.example.mgugu;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mgugu.fragment.FragmentDiscovery;
import com.example.mgugu.fragment.FragmentLuck;
import com.example.mgugu.fragment.FragmentPreferential;
import com.example.mgugu.fragment.FragmentShake;
import com.example.mgugu.fragment.FragmentUserCoupons;
import com.example.mgugu.usualtools.FastBlur;
import com.example.mgugu.usualtools.MPopupWindow;
import com.example.mgugu.usualtools.PopItemAnim;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FragmentDiscovery mFragmentDiscovery;//发现fragment
    private FragmentLuck mFragmentLuck;//拼手气fragment
    private FragmentPreferential mFragmentPreferential;//最优惠fragment
    private FragmentUserCoupons mFragmentUser;//用户fragment
    private FragmentShake mFragmentShake;//摇一摇fragment
    private FrameLayout mFrameLayout;//fragment要替换的部分

    private View mPopItemView;//popupwindow中的布局

    private LinearLayout mLinearPre;
    private LinearLayout mLinearShake;
    private LinearLayout mLinearLuck;
    private RelativeLayout mRelativeDiscovery;
    private LinearLayout mLinearUser;
    private CheckBox mCheckBoxPre;
    private CheckBox mCheckBoxShake;
    private CheckBox mCheckBoxLuck;
    private CheckBox mCheckBoxDiscovery;
    private CheckBox mCheckBoxUser;
    private TextView mTextPre;
    private TextView mTextShake;
    private TextView mTextLuck;
    private TextView mTextDiscovery;
    private TextView mTextUser;
    private RelativeLayout mImgPopBackFirst;//popupwindow中返回
    private ImageView mImgPopMore;//popupwindow更多
    private ImageView mImgPopLbs;//popupwindow定位
    private ImageView mImgPopReview;//popupwindow签到
    private ImageView mImgPopCarema;//popupwindow相机
    private ImageView mImgPopIdea;//popupwindow文字
    private ImageView mImgPopPhoto;//popupwindow照片
    private RelativeLayout mPopRelative;
    private RelativeLayout mFlexibleFrame;//打开popupwindow后活动的背景
    private ImageView mImgFuzzy;//模糊处理
    private View mViewScreenCut;//截屏
    private ImageView mImgPopBottom;//popupwindow底部按钮
    private ImageView mImgPlus;//底部加号
    private ArrayList<View> mViewList;//popupwindow关闭时所有的有动画的所有的view
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    PopItemAnim.finishPopItemAnimatior(mImgPopReview);
                    Message messageReview = new Message();
                    messageReview.what = 2;
                    mHandler.sendMessageDelayed(messageReview, 30);
                    break;
                case 2:
                    PopItemAnim.finishPopItemAnimatior(mImgPopPhoto);
                    Message messagePhoto = new Message();
                    messagePhoto.what = 3;
                    mHandler.sendMessageDelayed(messagePhoto, 30);
                    break;
                case 3:
                    PopItemAnim.finishPopItemAnimatior(mImgPopLbs);
                    Message messageLbs = new Message();
                    messageLbs.what = 4;
                    mHandler.sendMessageDelayed(messageLbs, 30);
                    break;
                case 4:
                    PopItemAnim.finishPopItemAnimatior(mImgPopIdea);
                    Message messageIdea = new Message();
                    messageIdea.what = 5;
                    mHandler.sendMessageDelayed(messageIdea, 30);
                    break;
                case 5:
                    PopItemAnim.finishPopItemAnimatior(mImgPopCarema);
                    Message messageCarema = new Message();
                    messageCarema.what = 6;
                    mHandler.sendMessageDelayed(messageCarema, 200);
                    break;
                case 6:
                    MPopupWindow.mPopupWindow.dismiss();
                    Message messageTimeLagClose=new Message();
                    messageTimeLagClose.what=7;
                    mHandler.sendMessageDelayed(messageTimeLagClose, 300);
                    break;
                case 7:
//                    打开点击效果
                    mPopRelative.setClickable(true);
                    mRelativeDiscovery.setClickable(true);
                    break;
                case 10:
                    PopItemAnim.startPopItemAnimatior(mImgPopIdea);
                    Message messageStartIdea = new Message();
                    messageStartIdea.what = 11;
                    mHandler.sendMessageDelayed(messageStartIdea, 50);
                    break;
                case 11:
                    PopItemAnim.startPopItemAnimatior(mImgPopLbs);
                    Message messageStartLbs = new Message();
                    messageStartLbs.what = 12;
                    mHandler.sendMessageDelayed(messageStartLbs, 50);
                    break;
                case 12:
                    PopItemAnim.startPopItemAnimatior(mImgPopPhoto);
                    Message messageStartPhoto = new Message();
                    messageStartPhoto.what = 13;
                    mHandler.sendMessageDelayed(messageStartPhoto, 50);
                    break;
                case 13:
                    PopItemAnim.startPopItemAnimatior(mImgPopReview);
                    Message messageStartReview = new Message();
                    messageStartReview.what = 14;
                    mHandler.sendMessageDelayed(messageStartReview, 50);
                    break;
                case 14:
                    PopItemAnim.startPopItemAnimatior(mImgPopMore);
                    Message messageTimeLagOpen=new Message();
                    messageTimeLagOpen.what=15;
                    mHandler.sendMessageDelayed(messageTimeLagOpen, 350);
                    break;
                case 15:
//                    打开点击效果
                    mRelativeDiscovery.setClickable(true);
                    mPopRelative.setClickable(true);
                    mFlexibleFrame.setClickable(true);
                default:
                    break;


            }
        }
    };


    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        设置statusbar为透明色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

//        控件初始化
        mFrameLayout = (FrameLayout) findViewById(R.id.framelayou_fragment);
        mRelativeDiscovery = (RelativeLayout) findViewById(R.id.relative_discovery);
        mLinearShake = (LinearLayout) findViewById(R.id.linear_shake);
        mLinearLuck = (LinearLayout) findViewById(R.id.linear_luck);
        mLinearPre = (LinearLayout) findViewById(R.id.linear_preferential);
        mLinearUser = (LinearLayout) findViewById(R.id.linear_user);
//        mCheckBoxDiscovery = (CheckBox) findViewById(R.id.checkbox_discovery);
        mCheckBoxLuck = (CheckBox) findViewById(R.id.checkbox_luck);
        mCheckBoxPre = (CheckBox) findViewById(R.id.checkbox_preferential);
        mCheckBoxShake = (CheckBox) findViewById(R.id.checkbox_shake);
        mCheckBoxUser = (CheckBox) findViewById(R.id.checkbox_user);
//        mTextDiscovery = (TextView) findViewById(R.id.text_discovery);
        mTextLuck = (TextView) findViewById(R.id.text_luck);
        mTextPre = (TextView) findViewById(R.id.text_preferential);
        mTextShake = (TextView) findViewById(R.id.text_shake);
        mTextUser = (TextView) findViewById(R.id.text_user);
        mFlexibleFrame = (RelativeLayout) findViewById(R.id.flexible_framelayout);
        mImgFuzzy = (ImageView) findViewById(R.id.popupwindow_img_background);
        mImgPlus = (ImageView) findViewById(R.id.img_bottom_center);

//        fragment初始化
        mFragmentDiscovery = new FragmentDiscovery();
        mFragmentLuck = new FragmentLuck();
        mFragmentUser = new FragmentUserCoupons();
        mFragmentShake = new FragmentShake();
        mFragmentPreferential = new FragmentPreferential();

//        fragmentmanager初始化
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.framelayou_fragment, mFragmentPreferential);
        mFragmentTransaction.commit();


//        设置第一个按键为红色
        mTextPre.setTextColor(getResources().getColor(R.color.titleRed));
//        获取popupwindow布局
        mPopItemView = getLayoutInflater().inflate(R.layout.popupwindow_item_view, null);
//        mImgPopBackFirst = (RelativeLayout) mPopItemView.findViewById(R.id.popupwindow_back);
        mImgPopMore = (ImageView) mPopItemView.findViewById(R.id.popupwindow_more);
        mImgPopCarema = (ImageView) mPopItemView.findViewById(R.id.popoupwindow_camera);
        mImgPopIdea = (ImageView) mPopItemView.findViewById(R.id.popupwindow_idea);
        mImgPopReview = (ImageView) mPopItemView.findViewById(R.id.popupwindow_review);
        mImgPopLbs = (ImageView) mPopItemView.findViewById(R.id.popupwindow_lbs);
        mImgPopPhoto = (ImageView) mPopItemView.findViewById(R.id.popupwindow_photo);
        mPopRelative = (RelativeLayout) mPopItemView.findViewById(R.id.popupwindow_relative);
//        mImgPopBottom= (ImageView) mPopItemView.findViewById(R.id.img_pop_bottom);

//        初始化viewlist
        mViewList = new ArrayList<>();
        mViewList.add(0, mImgPlus);
        mViewList.add(1, mLinearLuck);
        mViewList.add(2, mLinearPre);
        mViewList.add(3, mLinearShake);
        mViewList.add(4, mLinearUser);

//        设置首页底部的点击事件
        mLinearUser.setOnClickListener(this);
        mRelativeDiscovery.setOnClickListener(this);
        mLinearLuck.setOnClickListener(this);
        mLinearPre.setOnClickListener(this);
        mLinearShake.setOnClickListener(this);
//        mImgPopBackFirst.setOnClickListener(this);
        mImgPopMore.setOnClickListener(this);
        mImgPopIdea.setOnClickListener(this);
        mImgPopReview.setOnClickListener(this);
        mImgPopLbs.setOnClickListener(this);
        mImgPopCarema.setOnClickListener(this);
        mImgPopPhoto.setOnClickListener(this);
        mPopRelative.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.linear_preferential:
                checkAllFalse();
                allWhite();
                mTextPre.setTextColor(getResources().getColor(R.color.titleRed));
                mCheckBoxPre.setChecked(true);
                mFragmentTransaction.replace(R.id.framelayou_fragment, mFragmentPreferential);

                break;
            case R.id.relative_discovery:
//              关闭点击效果，防止用户狂点出异常，在动画完全展示万后再打开点击效果
                mRelativeDiscovery.setClickable(false);//防止多次点打开
                mPopRelative.setClickable(false);//防止未完全打开的时候点击popupwindwo关闭
                mFlexibleFrame.setClickable(false);//防止未完全打开的时候点击popupwindow以外的地方关闭

                MPopupWindow.changeAlphaAnimation(1.0f, 0.0f, mLinearUser);
                mLinearUser.setVisibility(View.INVISIBLE);
                MPopupWindow.changeAlphaAnimation(1.0f, 0.0f, mLinearShake);
                mLinearShake.setVisibility(View.INVISIBLE);
                MPopupWindow.changeAlphaAnimation(1.0f, 0.0f, mLinearPre);
                mLinearPre.setVisibility(View.INVISIBLE);
                MPopupWindow.changeAlphaAnimation(1.0f, 0.0f, mLinearLuck);
                mLinearLuck.setVisibility(View.INVISIBLE);

                applyBlur();
                MPopupWindow.mPopupWindowSettle(MainActivity.this, mPopItemView, mRelativeDiscovery, mFlexibleFrame, mViewList);
                PopItemAnim.startImgPlusAnimator(mImgPlus);

                PopItemAnim.startPopItemAnimatior(mImgPopCarema);
                Message messageStartCarema = new Message();
                messageStartCarema.what = 10;
                mHandler.sendMessageDelayed(messageStartCarema, 50);
                break;
            case R.id.popupwindow_more:
                break;
            case R.id.popupwindow_idea:
                break;
            case R.id.popupwindow_review:
                break;
            case R.id.popoupwindow_camera:
                break;
            case R.id.popupwindow_lbs:
                break;
            case R.id.popupwindow_photo:
                break;
            case R.id.popupwindow_relative:
//              关闭点击效果，防止用户狂点出异常，在动画完全展示万后再打开点击效果
                mPopRelative.setClickable(false);//防止多次点退出
                mRelativeDiscovery.setClickable(false);//防止尚未完全退出时点打开
                PopItemAnim.finishPopItemAnimatior(mImgPopMore);
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessageDelayed(message, 30);
                break;
            case R.id.linear_luck:
                checkAllFalse();
                allWhite();
                mTextLuck.setTextColor(getResources().getColor(R.color.titleRed));
                mCheckBoxLuck.setChecked(true);
                mFragmentTransaction.replace(R.id.framelayou_fragment, mFragmentLuck);
                break;
            case R.id.linear_shake:
                checkAllFalse();
                allWhite();
                mTextShake.setTextColor(getResources().getColor(R.color.titleRed));
                mCheckBoxShake.setChecked(true);
                mFragmentTransaction.replace(R.id.framelayou_fragment, mFragmentShake);
                break;
            case R.id.linear_user:
                checkAllFalse();
                allWhite();
                mTextUser.setTextColor(getResources().getColor(R.color.titleRed));
                mCheckBoxUser.setChecked(true);
                mFragmentTransaction.replace(R.id.framelayou_fragment, mFragmentUser);
                break;
        }
        mFragmentTransaction.commit();

    }

    /**
     * 将所有的checkbox设置为未选中状态
     */
    private void checkAllFalse() {
        mCheckBoxUser.setChecked(false);
//        mCheckBoxDiscovery.setChecked(false);
        mCheckBoxShake.setChecked(false);
        mCheckBoxLuck.setChecked(false);
        mCheckBoxPre.setChecked(false);
    }

    /**
     * 使底部按键下面多有的文字都变成灰色
     */
    private void allWhite() {
//        mTextDiscovery.setTextColor(getResources().getColor(R.color.gray));
        mTextShake.setTextColor(getResources().getColor(R.color.gray));
        mTextUser.setTextColor(getResources().getColor(R.color.gray));
        mTextPre.setTextColor(getResources().getColor(R.color.gray));
        mTextLuck.setTextColor(getResources().getColor(R.color.gray));
    }

    /**
     * 点击手机上后退按键先关闭popupwindow
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            必须用短路与，如果mPopupWindow是null直接跳出，不再执行后面的判断条件
            if (MPopupWindow.mPopupWindow != null && MPopupWindow.mPopupWindow.isShowing()) {
                MPopupWindow.mPopupWindow.dismiss();
//              return true后这个方法内下面的语句就不执行了。
//                不返回true的话按back键会继续执行下面的语句，退出活动
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取屏幕快照
     */
    private void applyBlur() {
//       截屏
        mViewScreenCut = getWindow().getDecorView();
        mViewScreenCut.setDrawingCacheEnabled(true);
        mViewScreenCut.buildDrawingCache(true);//截屏更新必备
        mFlexibleFrame.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mFlexibleFrame.getViewTreeObserver().removeOnPreDrawListener(this);
//              将截屏转换为bitmap类型
                Bitmap bmp1 = mViewScreenCut.getDrawingCache();
                int height = getOtherHeight();
//               除去状态栏和标题栏
                Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height, bmp1.getWidth(), bmp1.getHeight() - height);
                Log.d("date", "截屏时底层bitmap变化 ：" + bmp2.toString());
//                mCheckBoxPre.setBackground(new BitmapDrawable(getResources(),bmp2));
//                必须要给“随时随地发生新鲜事”加动画，不然在截屏是可能会先截到这个背景
                blur(bmp2, mFlexibleFrame);
                return true;
            }
        });
    }

    /**
     * 进行模糊处理并将模糊处理后的图片作为目标view的背景
     *
     * @param bkg  被模糊的图片
     * @param view 目标view（设置其背景为模糊图片）
     */
    @SuppressLint("NewApi")
    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;//图片缩放比例；
        float radius = 30;//模糊程度

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        canvas.drawBitmap(bkg, 0, 0, null);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);//模糊处理
        view.setBackground(new BitmapDrawable(getResources(), overlay));//将view的背景设置为模糊图片
//      打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
        Log.i("date", "blur time:" + (System.currentTimeMillis() - startMs));
    }

    /**
     * 获取系统状态栏和软件标题栏
     *
     * @return 返回系统状态栏和标题栏的高度
     */
    private int getOtherHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight;
        return statusBarHeight + titleBarHeight;
    }

    /**
     * 发送100ms延时信息
     * @param i message.what
     */
    private void sendDelayMessage(int i) {
        Message message = new Message();
        message.what = 3;
        mHandler.sendMessageDelayed(message, 100);
    }
}
