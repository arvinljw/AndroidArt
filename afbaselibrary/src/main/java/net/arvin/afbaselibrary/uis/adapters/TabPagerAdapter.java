package net.arvin.afbaselibrary.uis.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.arvin.afbaselibrary.listeners.ITabContent;
import net.arvin.afbaselibrary.listeners.ITabPager;

import java.util.List;

/**
 * Created by arvin on 2016/2/4 15:26
 * .
 */
public class TabPagerAdapter<T extends ITabPager> extends FragmentStatePagerAdapter {
    private List<T> mList;
    private ITabContent tabContent;

    public TabPagerAdapter(FragmentManager fm, List<T> list, ITabContent tabContent) {
        super(fm);
        this.mList = list;
        this.tabContent = tabContent;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return tabContent.getContent(position);
    }
}
