package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

import com.qvdev.apps.twitflick.Adapters.SectionsPagerAdapter;
import com.qvdev.apps.twitflick.Presenter.DetailPresenter;
import com.qvdev.apps.twitflick.R;

import java.util.Observer;

public class DetailView extends FragmentActivity {

    private ViewPager mViewPager;
    private DetailPresenter mDetailPresenter;
    private PagerTitleStrip mPagerTitleStrip;
    private PagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        initViews();

        mDetailPresenter = new DetailPresenter(this);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        mPagerTitleStrip.setTextColor(getResources().getColor(R.color.white));
    }

    public void addBuzzingDetailsObserver(Observer detailView) {
        mDetailPresenter.addBuzzingDetailsObserver(detailView);
    }
}
