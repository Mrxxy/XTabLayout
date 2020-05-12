package com.gaohui.image;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gaohui.android.code.library.R;

public class XImageTab extends LinearLayout {

    private ImageView ivSelected;
    private ImageView ivUnSelect;
    private TextView tvName;
    private ConstraintLayout clTab;

    private int selectedTextStyle;
    private int textStyle;
    private float textSize;
    private ColorStateList textColor;
    private XImageBean xImageBean;
    private boolean ivSelectedLoaded;
    private boolean ivUnSelectLoaded;
    private int defaultHeight;
    private boolean needInit = true;

    public XImageTab(Context context) {
        this(context, null);
    }

    public XImageTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XImageTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.XImageTab, 0, R.style.XImageTab_Default_Style);
        this.selectedTextStyle = typedArray.getInt(R.styleable.XImageTab_x_imageTabSelectedTextStyle, 0);
        this.textStyle = typedArray.getInt(R.styleable.XImageTab_x_imageTabTextStyle, 0);
        this.textSize = typedArray.getDimensionPixelSize(R.styleable.XImageTab_x_imageTabTextSize, 16);
        if (typedArray.hasValue(R.styleable.XImageTab_x_imageTabTextColor)) {
            typedArray.getColor(R.styleable.XImageTab_x_imageTabTextColor, 0);
            textColor = typedArray.getColorStateList(R.styleable.XImageTab_x_imageTabTextColor);
        }
        if (typedArray.hasValue(R.styleable.XImageTab_x_imageTabSelectedTextColor)) {
            int selectTextColor = typedArray.getColor(R.styleable.XImageTab_x_imageTabSelectedTextColor, 0);
            if (textColor != null) {
                textColor = createColorStateList(textColor.getDefaultColor(), selectTextColor);
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.x_tab_image_layout, this, false);
        ivSelected = rootView.findViewById(R.id.x_image);
        ivUnSelect = rootView.findViewById(R.id.x_image_un_select);
        tvName = rootView.findViewById(R.id.x_text);
        clTab = rootView.findViewById(R.id.x_clTab);
        defaultHeight = dp2px(32);
        addView(rootView);
        setup();
    }

    private void setup() {
        if (tvName != null) {
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        setSelected(isSelected());
    }

    public final ColorStateList createColorStateList(int textColor, int selectTextColor) {
        return new ColorStateList(new int[][]{new int[]{
                android.R.attr.state_selected},
                new int[]{-android.R.attr.state_selected}}, new int[]{selectTextColor, textColor});
    }

    public XImageTab setData(XImageBean xImageBean, float paddingSize) {
        this.xImageBean = xImageBean;
        this.needInit = true;
        if (clTab != null) {
            clTab.setPadding(dp2px(paddingSize), 0, dp2px(paddingSize), 0);
            setup();
        }
        return this;
    }

    public void setSelected(boolean isSelected) {
        int changeState;
        if (isSelected() != isSelected) {
            changeState = 1;
        } else {
            changeState = 0;
        }
        super.setSelected(isSelected);
        if ((changeState == 0) && (!needInit)) {
            return;
        }
        this.needInit = false;
        if (xImageBean != null) {
            tvName.setSelected(isSelected);
            tvName.setTypeface(Typeface.defaultFromStyle(isSelected ? selectedTextStyle : textStyle));
            if (isSelected) {
                tvName.setTextColor(xImageBean.getSelectedColor());
            } else {
                tvName.setTextColor(xImageBean.getUnSelectedColor());
            }
            tvName.setText(xImageBean.getTabName());
            if (isSelected) {
                if (xImageBean.getTypeSelected().equals(XImageBean.IMAGE)) {
                    ivSelected.setVisibility(VISIBLE);
                    ivUnSelect.setVisibility(INVISIBLE);
                    tvName.setVisibility(GONE);
                } else {
                    ivSelected.setVisibility(GONE);
                    ivUnSelect.setVisibility(GONE);
                    tvName.setVisibility(VISIBLE);
                }
                if (xImageBean.getSelectedDrawable() != 0) {
                    ivSelected.setImageResource(xImageBean.getSelectedDrawable());
                } else if (!TextUtils.isEmpty(xImageBean.getSelectedUrl()) && !ivSelectedLoaded) {
                    // load image
                    Glide.with(this).asBitmap().load(xImageBean.getSelectedUrl()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int imageW = resource.getWidth();
                            int imageH = resource.getHeight();
                            float ratio = ivSelected.getHeight() * 1.0f / imageH;
                            ivSelected.getLayoutParams().width = (int) (imageW * ratio);
                            ivSelected.setLayoutParams(ivSelected.getLayoutParams());
                            ivSelected.setImageBitmap(resource);
                        }
                    });
                    ivSelectedLoaded = true;
                }
            } else {
                if (xImageBean.getTypeNormal().equals(XImageBean.IMAGE)) {
                    ivSelected.setVisibility(INVISIBLE);
                    ivUnSelect.setVisibility(VISIBLE);
                    tvName.setVisibility(GONE);
                } else {
                    ivSelected.setVisibility(GONE);
                    ivUnSelect.setVisibility(GONE);
                    tvName.setVisibility(VISIBLE);
                }
                if (xImageBean.getUnSelectedDrawable() != 0) {
                    ivUnSelect.setImageResource(xImageBean.getUnSelectedDrawable());
                } else if (!TextUtils.isEmpty(xImageBean.getUnSelectedUrl()) && !ivUnSelectLoaded) {
                    // load image
                    Glide.with(this).asBitmap().load(xImageBean.getUnSelectedUrl()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int imageW = resource.getWidth();
                            int imageH = resource.getHeight();
                            ViewGroup.LayoutParams params = ivUnSelect.getLayoutParams();
                            if (imageH <= defaultHeight && imageH > 0) {
                                params.height = imageH;
                            } else {
                                params.height = defaultHeight;
                            }
                            if (imageH <= params.height) {
                                params.width = imageW;
                            } else {
                                params.width = imageW * defaultHeight / imageH;
                            }
                            ivUnSelect.setLayoutParams(params);
                            ivUnSelect.setImageBitmap(resource);
                        }
                    });
                    ivUnSelectLoaded = true;
                }
            }
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
