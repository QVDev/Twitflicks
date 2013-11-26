package com.qvdev.apps.twitflick.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.View.MainView;
import com.qvdev.apps.twitflick.View.TweetsView;

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
    private DetailView mDetailView;
    private BuzzingView mBuzzingView;
    private TweetsView mTweetsView;

    public SectionsPagerAdapter(FragmentManager fm, MainView mainView) {
        super(fm);
        mMainView = mainView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return createBuzzingView();
            case 1:
                return createDetailView();
            case 2:
                return createTweetsView();
            default:
                return null;
        }
    }

    private DetailView createDetailView() {
        if (mDetailView == null) {
            mDetailView = new DetailView();
            mMainView.addBuzzingDetailsObserver(mDetailView);
        }
        return mDetailView;
    }

    private BuzzingView createBuzzingView() {
        if (mBuzzingView == null) {
            mBuzzingView = new BuzzingView();
        }
        return mBuzzingView;
    }

    private TweetsView createTweetsView() {
        if (mTweetsView == null) {
            mTweetsView = new TweetsView();
            mMainView.addBuzzingDetailsObserver(mTweetsView);
        }
        return mTweetsView;
    }

    @Override
    public int getCount() {
        return 3;
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
            default:
                return null;
        }
    }
}

