package com.app.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.demo.fragment.ProductListFragment;

/**
 * @author Rajeev on 10/11/16.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    // This will Store the Titles of the Tabs which are Going to be passed when TabsPagerAdapter is created.
    private CharSequence titles[];

    // Store the number of tabs, this will also be passed when the TabsPagerAdapter is created.
    private int numbOfTabs;

    // fragment count
    private static final int PRODUCT_LIST_FRAGMENT = 0;

    /**
     * Build a Constructor and assign the passed Values to appropriate values in the class.
     *
     * @param fragmentManager passing fragment manager.
     * @param mTitles         passing title of action bar.
     * @param mNumbOfTabs     passing number of tabs.
     */
    public TabsPagerAdapter(FragmentManager fragmentManager, CharSequence mTitles[], int mNumbOfTabs) {
        super(fragmentManager);
        this.titles = mTitles;
        this.numbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PRODUCT_LIST_FRAGMENT: // if the position is 0 we are returning the First tab
                return ProductListFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
