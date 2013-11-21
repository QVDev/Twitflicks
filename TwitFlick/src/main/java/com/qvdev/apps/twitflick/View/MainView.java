package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.qvdev.apps.twitflick.Adapters.SectionsPagerAdapter;
import com.qvdev.apps.twitflick.Presenter.MainPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.listeners.onBuzzingListItemClicked;

public class MainView extends FragmentActivity implements onBuzzingListItemClicked {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private MainPresenter mMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mMainPresenter = new MainPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBuzzingItemSelected(float buzzingId) {
        mViewPager.setCurrentItem(1);
        mSectionsPagerAdapter.onBuzzingItemSelected(buzzingId);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            finish();
        } else {
            mViewPager.setCurrentItem(0);
        }
    }


}
