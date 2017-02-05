package net.arvin.afbaselibrary.uis.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.listeners.ITabContent;
import net.arvin.afbaselibrary.listeners.ITabPager;
import net.arvin.afbaselibrary.uis.adapters.TabPagerAdapter;
import net.arvin.afbaselibrary.utils.ScreenUtil;
import net.arvin.afbaselibrary.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by arvin on 16/11/21 17:54
 * email：1035407623@qq.com
 */
public abstract class BaseTabActivity<T extends ITabPager> extends BaseSwipeBackActivity implements ViewPager.OnPageChangeListener, ITabContent {
    protected PagerSlidingTabStrip mTabLayout;
    protected ViewPager mPager;
    protected List<T> mItems = new ArrayList<>();
    protected TabPagerAdapter<T> mAdapter;
    protected int selectedIndex = -1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTabLayout = getView(R.id.pre_tab_layout);
        mPager = getView(R.id.pre_pager);
        initTabView();
        getData();
    }

    private void initTabView() {
        mTabLayout.setTextColor(getTabTextColor());
        mTabLayout.setSelectedTextColorResource(getSelectedTabTextColor());
        mTabLayout.setIndicatorColorResource(getSelectedTabTextColor());
        mTabLayout.setIndicatorHeight(ScreenUtil.dp2px(2));
        mTabLayout.setUnderlineColor(getResources().getColor(R.color.black_divider));
        mTabLayout.setUnderlineHeight(ScreenUtil.dp2px(getUnderLineHeight()));
        mTabLayout.setDrawDivider(isDrawDivider());
    }

    protected float getUnderLineHeight() {
        return 1;
    }

    protected void initPager() {
        if (mItems == null || mItems.size() == 0) {
            return;
        }
        mAdapter = new TabPagerAdapter<>(getSupportFragmentManager(), mItems, this);
        mPager.setAdapter(mAdapter);
        mTabLayout.setTabAddWay(getItemAddWay());
        mTabLayout.setViewPager(mPager);
        mTabLayout.setOnPageChangeListener(this);
    }

    protected boolean isDrawDivider() {
        return false;
    }

    protected int getSelectedTabTextColor() {
        return R.color.colorAccent;
    }

    protected int getTabTextColor() {
        return R.color.black_normal;
    }

    protected PagerSlidingTabStrip.TabAddWay getItemAddWay() {
        if (mItems.size() <= 4) {
            return PagerSlidingTabStrip.TabAddWay.ITEM_MATCH;
        }
        return PagerSlidingTabStrip.TabAddWay.ITEM_WARP;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int position) {
        selectedIndex = position;
    }

    /**
     * 获取完数据后回调设置pager
     */
    protected abstract void getData();
}
