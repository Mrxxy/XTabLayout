package com.gaohui.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gaohui.badgeview.BadgeView;
import com.gaohui.image.XImageBean;
import com.gaohui.image.XImageTab;
import com.gaohui.xtablayout.R;
import com.gaohui.xtablayout.XTabLayout;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayoutOne;
    private TabLayout tabLayoutTwo;
    private TabLayout tabLayoutThree;
    private XTabLayout tabLayoutFour;
    private XTabLayout tabLayoutFive;
    private XTabLayout tabLayoutSix;
    private ViewPager viewPager;

    private BadgeView redDotBadgeView;
    private BadgeView redNumberBadgeView;

    private String[] strArray = new String[]{"关注", "推荐", "新人必看", "卫浴攻略", "住友津贴", "新人必看", "新人必看"};

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews() {
        tabLayoutOne = findViewById(R.id.tabsOne);
        tabLayoutTwo = findViewById(R.id.tabsTwo);
        tabLayoutThree = findViewById(R.id.tabsThree);
        tabLayoutFour = findViewById(R.id.tabsFour);
        tabLayoutFive = findViewById(R.id.tabsFive);
        tabLayoutSix = findViewById(R.id.tabsSix);
        viewPager = findViewById(R.id.viewPager);

        initData();

        IndexPagerAdapter indexPagerAdapter = new IndexPagerAdapter(getSupportFragmentManager(), stringList, fragmentList);
        viewPager.setAdapter(indexPagerAdapter);

        tabLayoutOne.setupWithViewPager(viewPager);
        tabLayoutTwo.setupWithViewPager(viewPager);
        tabLayoutThree.setupWithViewPager(viewPager);
        tabLayoutFour.setupWithViewPager(viewPager);
        tabLayoutFive.setupWithViewPager(viewPager);


//        tabLayoutTwo.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //了解源码得知 线的宽度是根据 tabView的宽度来设置的
//                    LinearLayout mTabStrip = (LinearLayout) tabLayoutTwo.getChildAt(0);
//                    int dp10 = dip2px(tabLayoutTwo.getContext(), 10);
//
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField =
//                                tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//
//                        tabView.setPadding(0, 0, 0, 0);
//
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width = 0;
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }
//
//                        //设置tab左右间距为10dp  注意这里不能使用Padding
//                        // 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        LinearLayout.LayoutParams params =
//                                (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width = width;
//                        params.leftMargin = dp10;
//                        params.rightMargin = dp10;
//                        tabView.setLayoutParams(params);
//
//                        tabView.invalidate();
//                    }
//
//                } catch (Exception e) {
//
//                }
//            }
//        });
//
        tabLayoutThree.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Class<?> tablayout = tabLayoutThree.getClass();
                    Field tabStrip = tablayout.getDeclaredField("mTabStrip");
                    tabStrip.setAccessible(true);
                    LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabLayoutThree);
                    for (int i = 0; i < ll_tab.getChildCount(); i++) {
                        View child = ll_tab.getChildAt(i);
                        child.setPadding(0, 0, 0, 0);
                        LinearLayout.LayoutParams params = new
                                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        params.setMarginStart(dip2px(MainActivity.this, 1f));
                        params.setMarginEnd(dip2px(MainActivity.this, 15f));
                        child.setLayoutParams(params);
                        child.invalidate();
                    }
                } catch (Exception e) {

                }
            }
        });


        //add normal badgeView
        if (redDotBadgeView == null) {
            XTabLayout.Tab tab = tabLayoutFive.getTabAt(0);
            if (tab != null && tab.getView() != null) {
                redDotBadgeView = new BadgeView(this, tab.getView());
                redDotBadgeView.setBadgeMargin(BadgeView.POSITION_TOP_RIGHT);
                redDotBadgeView.setBadgeMargin(0, 0);
                redDotBadgeView.setOvalShape(3);
                redDotBadgeView.show();
            }
        }

//        //add number badgeView
//        if (redNumberBadgeView == null) {
//            XTabLayout.Tab tab = tabLayoutFive.getTabAt(1);
//            if (tab != null && tab.getTabView() != null) {
//                redNumberBadgeView = new BadgeView(this, tab.getTabView());
//                redNumberBadgeView.setBadgeMargin(BadgeView.POSITION_TOP_RIGHT);
//                redNumberBadgeView.setBadgeMargin(dip2px(this, 8f), 0);
//                redNumberBadgeView.setGravity(Gravity.CENTER);
//                redNumberBadgeView.setText("9");
//                redNumberBadgeView.show();
//            }
//        }

        // image tab
        List<XImageBean> list = new ArrayList<>();
        list.add(new XImageBean("关注", Color.parseColor("#000000"), Color.parseColor("#B5B5B5")));
        list.add(new XImageBean("推荐", Color.parseColor("#000000"), Color.parseColor("#B5B5B5")));
        list.add(new XImageBean("新人必看", "https://img.haohaozhu.cn/Op-imageShow/601d7308002o0iP00q92z2u?iv=1",
                "https://img.haohaozhu.cn/Op-imageShow/9a54c305c02o0kP00q92z4z?iv=1",
                0, 0));
        list.add(new XImageBean("卫浴攻略", Color.parseColor("#0d5ea7"), Color.parseColor("#000000")));
        list.add(new XImageBean("住友津贴", Color.parseColor("#ff6038"), Color.parseColor("#000000")));
        list.add(new XImageBean("新人必看", XImageBean.TEXT, XImageBean.IMAGE, "",
                "https://img.haohaozhu.cn/Op-imageShow/9a54c305c02o0kP00q92z4z?iv=1", 0, 0, 0, 0));
        list.add(new XImageBean("新人必看", XImageBean.IMAGE, XImageBean.TEXT, "https://img.haohaozhu.cn/Op-imageShow/601d7308002o0iP00q92z2u?iv=1",
                "", 0, 0, Color.parseColor("#000000"), Color.parseColor("#0d5ea7")));
        fragmentList.clear();
        for (int i = 0; i < list.size(); i++) {
            fragmentList.add(EmptyFragment.newInstance(i));
        }
        IndexPagerAdapter tabAdapter = new IndexPagerAdapter(getSupportFragmentManager(), stringList, fragmentList);
        viewPager.setAdapter(tabAdapter);
        tabLayoutSix.setupWithViewPager(viewPager);
        for (int i = 0; i < list.size(); i++) {
            XImageTab imageTab = new XImageTab(this).setData(list.get(i), i == 1 ? 12f : 9f);
            tabLayoutSix.getTabAt(i).setCustomView(imageTab);
        }
    }

    private void initData() {
        stringList.addAll(Arrays.asList(strArray));
        for (int i = 0; i < stringList.size(); i++) {
            Fragment fragment = EmptyFragment.newInstance(i);
            fragmentList.add(fragment);
        }
    }

    static class IndexPagerAdapter extends FragmentPagerAdapter {
        private List<String> titleList;
        private List<Fragment> fragmentList;

        public IndexPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
