package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Presenter.BuzzingPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.libs.Refreshbar.RefreshBar;

/**
 * Created by QVDev on 7/29/13.
 */

public class BuzzingView extends Fragment {
    private BuzzingPresenter mBuzzingPresenter;
    private GridView mBuzzingGridView;
    private RefreshBar mRefreshBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_buzzing, container, false);
        initLayouts(rootView);
        return rootView;
    }

    private void initLayouts(View rootView) {
        mBuzzingGridView = (GridView) rootView.findViewById(R.id.GridLayoutBuzzing);
        mRefreshBar = (RefreshBar) rootView.findViewById(R.id.refresh_bar);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBuzzingPresenter == null) {
            mBuzzingPresenter = new BuzzingPresenter(this);
        } else {
            mBuzzingPresenter.resumed();
        }

        initRefreshBar();
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
}