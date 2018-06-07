package com.tarunsmalviya.hackernews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tarunsmalviya.hackernews.fragment.ListFragment;
import com.tarunsmalviya.hackernews.util.ListType;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final String[] TABS = new String[]{ListType.TOP_STORIES.toString(), ListType.NEW_STORIES.toString(), ListType.BEST_STORIES.toString()};

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListFragment.newInstance(ListType.TOP_STORIES);
            case 1:
                return ListFragment.newInstance(ListType.NEW_STORIES);
            case 2:
                return ListFragment.newInstance(ListType.BEST_STORIES);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
