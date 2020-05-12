package com.gaohui.image;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

public class XImageBean {

    public static final String TEXT = "TEXT";
    public static final String IMAGE = "IMAGE";

    private String tabName;
    private String typeNormal;
    private String typeSelected;
    private String unSelectedUrl;
    private String selectedUrl;
    private int unSelectedDrawable;
    private int selectedDrawable;
    private int unSelectedColor;
    private int selectedColor;

    public XImageBean(String tabName, @ColorInt int unSelectedColor, @ColorInt int selectedColor) {
        this(tabName, TEXT, TEXT, "", "", 0, 0, unSelectedColor, selectedColor);
    }

    public XImageBean(String tabName, String unSelectedUrl, String selectedUrl, @DrawableRes int unSelectedDrawable, @DrawableRes int selectedDrawable) {
        this(tabName, IMAGE, IMAGE, unSelectedUrl, selectedUrl, unSelectedDrawable, selectedDrawable, 0, 0);
    }

    public XImageBean(String tabName, String typeNormal, String typeSelected,
                      String unSelectedUrl, String selectedUrl,
                      @DrawableRes int unSelectedDrawable, @DrawableRes int selectedDrawable,
                      @ColorInt int unSelectedColor, @ColorInt int selectedColor) {
        this.tabName = tabName;
        this.typeNormal = typeNormal;
        this.typeSelected = typeSelected;
        this.unSelectedUrl = unSelectedUrl;
        this.selectedUrl = selectedUrl;
        this.unSelectedDrawable = unSelectedDrawable;
        this.selectedDrawable = selectedDrawable;
        this.unSelectedColor = unSelectedColor == 0 ? Color.parseColor("#888888") : unSelectedColor;
        this.selectedColor = selectedColor == 0 ? Color.parseColor("#000000") : selectedColor;
    }

    public String getTabName() {
        return tabName;
    }

    public String getTypeNormal() {
        return typeNormal;
    }

    public String getTypeSelected() {
        return typeSelected;
    }

    public String getUnSelectedUrl() {
        return unSelectedUrl;
    }

    public String getSelectedUrl() {
        return selectedUrl;
    }

    public int getUnSelectedDrawable() {
        return unSelectedDrawable;
    }

    public int getSelectedDrawable() {
        return selectedDrawable;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }
}
