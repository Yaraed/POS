/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.weyee.poswidget.textview.label;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 标签文字
 *
 * @author xuexiang
 * @since 2018/12/3 上午12:18
 */
public class LabelTextView extends AppCompatTextView {

    LabelViewHelper utils;

    public LabelTextView(Context context) {
        this(context, null);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        utils = new LabelViewHelper(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        utils.onDraw(canvas, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setLabelHeight(int height) {
        utils.setLabelHeight(this, height);
    }

    public int getLabelHeight() {
        return utils.getLabelHeight();
    }

    public void setLabelDistance(int distance) {
        utils.setLabelDistance(this, distance);
    }

    public int getLabelDistance() {
        return utils.getLabelDistance();
    }

    public boolean isLabelEnable() {
        return utils.isLabelVisual();
    }

    public void setLabelEnable(boolean enable) {
        utils.setLabelVisual(this, enable);
    }

    public int getLabelOrientation() {
        return utils.getLabelOrientation();
    }

    public void setLabelOrientation(int orientation) {
        utils.setLabelOrientation(this, orientation);
    }

    public int getLabelTextColor() {
        return utils.getLabelTextColor();
    }

    public void setLabelTextColor(int textColor) {
        utils.setLabelTextColor(this, textColor);
    }

    public int getLabelBackgroundColor() {
        return utils.getLabelBackgroundColor();
    }

    public void setLabelBackgroundColor(int backgroundColor) {
        utils.setLabelBackgroundColor(this, backgroundColor);
    }

    public String getLabelText() {
        return utils.getLabelText();
    }

    public void setLabelText(String text) {
        utils.setLabelText(this, text);
    }

    public int getLabelTextSize() {
        return utils.getLabelTextSize();
    }

    public void setLabelTextSize(int textSize) {
        utils.setLabelTextSize(this, textSize);
    }

    public int getLabelTextStyle() {
        return utils.getLabelTextStyle();
    }

    public void setLabelTextStyle(int textStyle) {
        utils.setLabelTextStyle(this, textStyle);
    }

    public String getLabelTextFont() {
        return utils.getLabelTextFont();
    }

    public void setLabelTextFont(String textFont) {
        utils.setLabelTextFont(this, textFont);
    }
}


