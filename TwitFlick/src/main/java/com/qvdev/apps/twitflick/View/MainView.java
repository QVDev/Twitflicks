package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

import com.newrelic.agent.android.NewRelic;
import com.qvdev.apps.twitflick.Adapters.SectionsPagerAdapter;
import com.qvdev.apps.twitflick.DeveloperKey;
import com.qvdev.apps.twitflick.Presenter.MainPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.listeners.onBuzzingListItemClicked;

import java.util.Observer;

public class MainView extends FragmentActivity implements onBuzzingListItemClicked {

    private ViewPager mViewPager;
    private MainPresenter mMainPresenter;
    private PagerTitleStrip mPagerTitleStrip;
    private PagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewRelic.withApplicationToken(
                DeveloperKey.DEVELOPER_KEY_RELIC
        ).start(this.getApplication());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        initViews();

        mMainPresenter = new MainPresenter(this);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        mPagerTitleStrip.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onBuzzingItemSelected(float buzzingId) {
        mViewPager.setCurrentItem(1);
        mMainPresenter.getBuzzingDetails(buzzingId);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            finish();
        } else {
            mViewPager.setCurrentItem(0);
        }
    }

    public void addBuzzingDetailsObserver(Observer detailView) {
        mMainPresenter.addBuzzingDetailsObserver(detailView);
    }
}
