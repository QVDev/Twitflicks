package com.qvdev.apps.twitflick.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.View.MainView;

import java.util.Locale;

/**
 * Created by dirkwilmer on 7/29/13.
 */

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final MainView mMainView;

    public SectionsPagerAdapter(FragmentManager fm, MainView mainView) {
        super(fm);
        mMainView = mainView;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new BuzzingView();
        Bundle args = new Bundle();
        args.putInt(BuzzingView.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        // Show x pages
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mMainView.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mMainView.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return mMainView.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}

