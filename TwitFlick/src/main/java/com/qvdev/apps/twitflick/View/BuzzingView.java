package com.qvdev.apps.twitflick.View;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.newrelic.agent.android.NewRelic;
import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.DeveloperKey;
import com.qvdev.apps.twitflick.Presenter.BuzzingPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.libs.Refreshbar.RefreshBar;

/**
 * Created by QVDev on 7/29/13.
 */

public class BuzzingView extends Activity {
    private BuzzingPresenter mBuzzingPresenter;
    private GridView mBuzzingGridView;
    private RefreshBar mRefreshBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_buzzing);

        NewRelic.withApplicationToken(DeveloperKey.DEVELOPER_KEY_RELIC).start(this.getApplication());
        initLayouts();

        if (mBuzzingPresenter == null) {
            mBuzzingPresenter = new BuzzingPresenter(this);
        } else {
            mBuzzingPresenter.resumed();
        }

        initRefreshBar();
    }


    private void initLayouts() {
        mBuzzingGridView = (GridView) findViewById(R.id.GridLayoutBuzzing);
        mRefreshBar = (RefreshBar) findViewById(R.id.refresh_bar);
    }

    private void initRefreshBar() {
        mRefreshBar.setRefreshableView(mBuzzingGridView);
        mRefreshBar.setRefreshListener(mBuzzingPresenter);
    }

    public void setAdapter(BuzzingListAdapter adapter) {
        mBuzzingGridView.setAdapter(adapter);
    }

    public void onRefreshFinished() {
        mRefreshBar.onRefreshFinished();
    }

    public void showProgress() {
        mRefreshBar.startLoadingProgress();
    }
}