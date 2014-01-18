package com.qvdev.apps.twitflick.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.MovieView;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.View.TweetsView;

import java.util.Locale;

/**
 * Created by QVDev on 7/29/13.
 */

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    private final DetailView mDetailView;
    private MovieView mMovieView;
    private TweetsView mTweetsView;

    public SectionsPagerAdapter(FragmentManager fm, DetailView detailView) {
        super(fm);


        mDetailView = detailView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        switch (position) {
            case 0:
                if (fragment instanceof MovieView) {
                    mMovieView = (MovieView) fragment;
                    mDetailView.addBuzzingDetailsObserver(mMovieView);
                }
                break;
            case 1:
                if (fragment instanceof TweetsView) {
                    mTweetsView = (TweetsView) fragment;
                    mDetailView.addBuzzingDetailsObserver(mTweetsView);
                }
                break;
            default:
                break;
        }
        return fragment;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return createDetailView();
            case 1:
                return createTweetsView();
            default:
                return null;
        }
    }

    private MovieView createDetailView() {
        if (mMovieView == null) {
            mMovieView = new MovieView();
            mDetailView.addBuzzingDetailsObserver(mMovieView);
        }
        return mMovieView;
    }

    private TweetsView createTweetsView() {
        if (mTweetsView == null) {
            mTweetsView = new TweetsView();
            mDetailView.addBuzzingDetailsObserver(mTweetsView);
        }
        return mTweetsView;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mDetailView.getString(R.string.title_section2).toUpperCase(l);
            case 1:
                return mDetailView.getString(R.string.title_section3).toUpperCase(l);
            default:
                return null;
        }
    }
}

