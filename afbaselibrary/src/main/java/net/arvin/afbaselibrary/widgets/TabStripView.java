package net.arvin.afbaselibrary.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.uis.fragments.BaseFragment;
import net.arvin.afbaselibrary.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2016/2/2 16:47
 */
public class TabStripView extends LinearLayout implements View.OnClickListener {

    private static final String KEY_CURRENT_TAG = "net.arvin.afbaselibrary.widgets.TabStripViewTag";

    private List<ViewHolder> mViewHolderList;
    private OnTabSelectedListener mTabSelectListener;
    private FragmentActivity mFragmentActivity;
    private String mCurrentTag;
    private String mRestoreTag;
    /*主内容显示区域View的id*/
    private int mMainContentLayoutId;
    /*选中的Tab文字颜色*/
    private ColorStateList mSelectedTextColor;
    /*正常的Tab文字颜色*/
    private ColorStateList mNormalTextColor;
    /*Tab文字的颜色*/
    private float mTabTextSize;
    /*默认选中的tab index*/
    private int mDefaultSelectedTab = 0;

    private int mCurrentSelectedTab;
    private BaseFragment currentFragment;
    private TabClickJudgeListener clickJudgeListener;

    public TabStripView(Context context) {
        this(context, null);
    }

    public TabStripView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabStripView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TabStripView, 0, 0);

        ColorStateList tabTextColor = typedArray.getColorStateList(R.styleable.TabStripView_navigateTabTextColor);
        ColorStateList selectedTabTextColor = typedArray.getColorStateList(R.styleable.TabStripView_navigateTabSelectedTextColor);

        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.TabStripView_navigateTabTextSize, 0);
        mMainContentLayoutId = typedArray.getResourceId(R.styleable.TabStripView_containerId, 0);

        mNormalTextColor = (tabTextColor != null ? tabTextColor : context.getResources().getColorStateList(R.color.black_normal));


        if (selectedTabTextColor != null) {
            mSelectedTextColor = selectedTabTextColor;
        } else {
            ScreenUtil.checkAppCompatTheme(context);
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            mSelectedTextColor = context.getResources().getColorStateList(typedValue.resourceId);
        }

        mViewHolderList = new ArrayList<>();
    }

    public void addTab(Class frameLayoutClass, TabParam tabParam) {
        int defaultLayout = R.layout.ui_layout_navi_tab_view;
//        if (tabParam.tabViewResId > 0) {
//            defaultLayout = tabParam.tabViewResId;
//        }
        if (TextUtils.isEmpty(tabParam.title)) {
            tabParam.title = getContext().getString(tabParam.titleStringRes);
        }

        View view = LayoutInflater.from(getContext()).inflate(defaultLayout, null);
        view.setFocusable(true);

        ViewHolder holder = new ViewHolder();

        holder.tabIndex = mViewHolderList.size();

        holder.fragmentClass = frameLayoutClass;
        holder.tag = tabParam.title;
        holder.pageParam = tabParam;

        holder.tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
        holder.tabTitle = ((TextView) view.findViewById(R.id.tab_title));

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.tabTitle.setVisibility(View.INVISIBLE);
        } else {
            holder.tabTitle.setText(tabParam.title);
        }

        if (mTabTextSize != 0) {
            holder.tabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        }
        if (mNormalTextColor != null) {
            holder.tabTitle.setTextColor(mNormalTextColor);
        }

        if (tabParam.backgroundColor > 0) {
            view.setBackgroundResource(tabParam.backgroundColor);
        }

        if (tabParam.iconResId > 0) {
            holder.tabIcon.setImageResource(tabParam.iconResId);
        } else {
            holder.tabIcon.setVisibility(View.INVISIBLE);
        }

        if (tabParam.iconResId > 0 && tabParam.iconSelectedResId > 0) {
            view.setTag(holder);
            view.setOnClickListener(this);
            mViewHolderList.add(holder);
        }

        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));

    }

    public void setClickJudgeListener(TabClickJudgeListener clickJudgeListener) {
        this.clickJudgeListener = clickJudgeListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if (mMainContentLayoutId == 0) {
//            throw new RuntimeException("mFrameLayoutId Cannot be 0");
//        }
//        if (mViewHolderList.size() == 0) {
//            throw new RuntimeException("mViewHolderList.size Cannot be 0, Please call addTab()");
//        }
//        if (!(getContext() instanceof FragmentActivity)) {
//            throw new RuntimeException("parent activity must is extends FragmentActivity");
//        }
        try {
            mFragmentActivity = (FragmentActivity) getContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewHolder defaultHolder = null;

        if (!TextUtils.isEmpty(mRestoreTag)) {
            for (ViewHolder holder : mViewHolderList) {
                if (TextUtils.equals(mRestoreTag, holder.tag)) {
                    defaultHolder = holder;
                    mRestoreTag = null;
                    break;
                }
            }
        } else {
            if (mViewHolderList.size() > 0) {
                defaultHolder = mViewHolderList.get(mDefaultSelectedTab);
            }
        }

        showFragment(defaultHolder);
    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag();
        if (object != null && object instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) v.getTag();
            if (clickJudgeListener != null && clickJudgeListener.judge(holder)) {
                return;
            }
            showFragment(holder);
        }
    }

    /**
     * 显示 holder 对应的 fragment
     */
    private void showFragment(ViewHolder holder) {
        if (holder == null) {
            return;
        }
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();

        if (isFragmentShown(transaction, holder.tag)) {
            return;
        }
        hideAllFragment();
        setCurrSelectedTabByTag(holder.tag);
        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(holder.tag);
        if (fragment == null) {
            fragment = getFragmentInstance(holder.tag);
            transaction.add(mMainContentLayoutId, fragment, holder.tag);
        } else {
            transaction.show(fragment);
            fragment.setUserVisibleHint(true);
        }
        currentFragment = (BaseFragment) fragment;
        transaction.commitAllowingStateLoss();
        mCurrentSelectedTab = holder.tabIndex;
        if (mTabSelectListener != null) {
            mTabSelectListener.onTabSelected(holder);
        }
    }

    public BaseFragment getCurrentFragment(String tag) {
        return currentFragment;
    }

    private boolean isFragmentShown(FragmentTransaction transaction, String newTag) {
        if (TextUtils.equals(newTag, mCurrentTag)) {
            return true;
        }

        if (TextUtils.isEmpty(mCurrentTag)) {
            return false;
        }

        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(mCurrentTag);
        if (fragment != null && !fragment.isHidden()) {
            transaction.hide(fragment);
        }

        return false;
    }

    /*设置当前选中tab的图片和文字颜色*/
    private void setCurrSelectedTabByTag(String tag) {
        if (TextUtils.equals(mCurrentTag, tag)) {
            return;
        }
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(mCurrentTag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconResId);
                holder.tabTitle.setTextColor(mNormalTextColor);
            } else if (TextUtils.equals(tag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconSelectedResId);
                holder.tabTitle.setTextColor(mSelectedTextColor);
            }
        }
        mCurrentTag = tag;
    }


    private Fragment getFragmentInstance(String tag) {
        Fragment fragment = null;
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(tag, holder.tag)) {
                try {
                    fragment = (Fragment) Class.forName(holder.fragmentClass.getName()).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return fragment;
    }

    private void hideAllFragment() {
        if (mViewHolderList == null || mViewHolderList.size() == 0) {
            return;
        }
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();

        for (ViewHolder holder : mViewHolderList) {
            Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(holder.tag);
            if (fragment != null && !fragment.isHidden()) {
                transaction.hide(fragment);
                fragment.setUserVisibleHint(false);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    public void setSelectedTabTextColor(ColorStateList selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setSelectedTabTextColor(int color) {
        mSelectedTextColor = ColorStateList.valueOf(color);
    }

    public void setTabTextColor(ColorStateList color) {
        mNormalTextColor = color;
    }

    public void setTabTextColor(int color) {
        mNormalTextColor = ColorStateList.valueOf(color);
    }

    public void setFrameLayoutId(int frameLayoutId) {
        mMainContentLayoutId = frameLayoutId;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_TAG);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CURRENT_TAG, mCurrentTag);
    }

    @SuppressWarnings("WeakerAccess")
    public static class ViewHolder {
        public String tag;
        public TabParam pageParam;
        public ImageView tabIcon;
        public TextView tabTitle;
        public Class fragmentClass;
        public int tabIndex;
    }


    @SuppressWarnings("WeakerAccess")
    public static class TabParam {
        public int backgroundColor = android.R.color.white;
        public int iconResId;
        public int iconSelectedResId;
        public int titleStringRes;
        //        public int tabViewResId;
        public String title;

        public TabParam(int iconResId, int iconSelectedResId, String title) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }

        public TabParam(int iconResId, int iconSelectedResId, int titleStringRes) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, int titleStringRes) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, String title) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }
    }


    public interface OnTabSelectedListener {
        void onTabSelected(ViewHolder holder);
    }

    public void setTabSelectListener(OnTabSelectedListener tabSelectListener) {
        mTabSelectListener = tabSelectListener;
    }

    public void setDefaultSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            mDefaultSelectedTab = index;
        }
    }

    public void setCurrentSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            ViewHolder holder = mViewHolderList.get(index);
            showFragment(holder);
        }
    }

    public int getCurrentSelectedTab() {
        return mCurrentSelectedTab;
    }

    public interface TabClickJudgeListener {
        boolean judge(ViewHolder holder);
    }

}