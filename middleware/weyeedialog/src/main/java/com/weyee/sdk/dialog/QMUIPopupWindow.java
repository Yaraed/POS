/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weyee.sdk.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.*;
import android.widget.PopupWindow;
import androidx.annotation.RequiresApi;

/**
 * <p>自定义PopWindow类，封装了PopWindow的一些常用属性，用Builder模式支持链式调用
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/19 0019
 */
public class QMUIPopupWindow implements PopupWindow.OnDismissListener {
    private static final String TAG = "QMUIPopupWindow";
    private static final float DEFAULT_ALPHA = 0.7f;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;
    private int mResLayoutId = -1;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private int mAnimationStyle = -1;

    private boolean mClippEnable = true;//default is true
    private boolean mIgnoreCheekPress = false;
    private int mInputMode = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode = -1;
    private boolean mTouchable = true;//default is ture
    private View.OnTouchListener mOnTouchListener;

    private Window mWindow;//当前Activity 的窗口
    /**
     * 弹出PopWindow 背景是否变暗，默认不会变暗。
     */
    private boolean mIsBackgroundDark = false;

    private float mBackgroundDrakValue = 0;// 背景变暗的值，0 - 1
    /**
     * 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
     */
    private boolean enableOutsideTouchDisMiss = true;// 默认点击pop之外的地方可以关闭

    private QMUIPopupWindow(Context context) {
        mContext = context;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    public QMUIPopupWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public QMUIPopupWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public QMUIPopupWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }


    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     *
     * @param parent  父控件
     * @param gravity
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return
     */
    public QMUIPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    /**
     * 添加一些属性设置
     *
     * @param popupWindow
     */
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippEnable);
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != -1) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != -1) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);


    }

    @SuppressLint("ClickableViewAccessibility")
    private PopupWindow build() {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }

        // 2017.3.17 add
        // 获取当前Activity的window
        Activity activity = (Activity) mContentView.getContext();
        if (activity != null && mIsBackgroundDark) {
            //如果设置的值在0 - 1的范围内，则用设置的值，否则用默认值
            final float alpha = (mBackgroundDrakValue > 0 && mBackgroundDrakValue < 1) ? mBackgroundDrakValue : DEFAULT_ALPHA;
            mWindow = activity.getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = alpha;
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mWindow.setAttributes(params);
        }


        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        } else {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }

        apply(mPopupWindow);//设置一些属性

        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        // 添加dissmiss 监听
        mPopupWindow.setOnDismissListener(this);

        //2017.6.27 add:fix 设置  setOutsideTouchable（false）点击外部取消的bug.
        // 判断是否点击PopupWindow之外的地方关闭 popWindow
        if (!enableOutsideTouchDisMiss) {
            //注意这三个属性必须同时设置，不然不能disMiss，以下三行代码在Android 4.4 上是可以，然后在Android 6.0以上，下面的三行代码就不起作用了，就得用下面的方法
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setBackgroundDrawable(null);
            //注意下面这三个是contentView 不是PopupWindow
            mPopupWindow.getContentView().setFocusable(true);
            mPopupWindow.getContentView().setFocusableInTouchMode(true);
            mPopupWindow.getContentView().setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPopupWindow.dismiss();

                    return true;
                }
                return false;
            });
            //在Android 6.0以上 ，只能通过拦截事件来解决
            mPopupWindow.setTouchInterceptor((v, event) -> {

                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if ((event.getAction() == MotionEvent.ACTION_DOWN) && ((x < 0) || (x >= mWidth) || (y < 0) || (y >= mHeight))) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }
                return false;
            });
        } else {
            mPopupWindow.setFocusable(mIsFocusable);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(mIsOutside);
        }
        // update
        mPopupWindow.update();

        return mPopupWindow;
    }

    /**
     * 关闭popWindow
     */
    public void dismiss() {

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }

        //如果设置了背景变暗，那么在dissmiss的时候需要还原
        if (mWindow != null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public static class PopupWindowBuilder {
        private QMUIPopupWindow mCustomPopWindow;

        public PopupWindowBuilder(Context context) {
            mCustomPopWindow = new QMUIPopupWindow(context);
        }

        public PopupWindowBuilder size(int width, int height) {
            mCustomPopWindow.mWidth = width;
            mCustomPopWindow.mHeight = height;
            return this;
        }


        public PopupWindowBuilder setFocusable(boolean focusable) {
            mCustomPopWindow.mIsFocusable = focusable;
            return this;
        }


        public PopupWindowBuilder setView(int resLayoutId) {
            mCustomPopWindow.mResLayoutId = resLayoutId;
            mCustomPopWindow.mContentView = null;
            return this;
        }

        public PopupWindowBuilder setView(View view) {
            mCustomPopWindow.mContentView = view;
            mCustomPopWindow.mResLayoutId = -1;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable) {
            mCustomPopWindow.mIsOutside = outsideTouchable;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationStyle
         * @return
         */
        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            mCustomPopWindow.mAnimationStyle = animationStyle;
            return this;
        }


        public PopupWindowBuilder setClippingEnable(boolean enable) {
            mCustomPopWindow.mClippEnable = enable;
            return this;
        }


        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress) {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopupWindowBuilder setInputMethodMode(int mode) {
            mCustomPopWindow.mInputMode = mode;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            mCustomPopWindow.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            mCustomPopWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public PopupWindowBuilder setTouchable(boolean touchable) {
            mCustomPopWindow.mTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            mCustomPopWindow.mOnTouchListener = touchIntercepter;
            return this;
        }

        /**
         * 设置背景变暗是否可用
         *
         * @param isDark
         * @return
         */
        public PopupWindowBuilder enableBackgroundDark(boolean isDark) {
            mCustomPopWindow.mIsBackgroundDark = isDark;
            return this;
        }

        /**
         * 设置背景变暗的值
         *
         * @param darkValue
         * @return
         */
        public PopupWindowBuilder setBgDarkAlpha(float darkValue) {
            mCustomPopWindow.mBackgroundDrakValue = darkValue;
            return this;
        }

        /**
         * 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
         *
         * @param disMiss
         * @return
         */
        public PopupWindowBuilder enableOutsideTouchableDissmiss(boolean disMiss) {
            mCustomPopWindow.enableOutsideTouchDisMiss = disMiss;
            return this;
        }


        public QMUIPopupWindow create() {
            //构建PopWindow
            mCustomPopWindow.build();
            return mCustomPopWindow;
        }

    }

    @Override
    public void onDismiss() {
        dismiss();
    }
}