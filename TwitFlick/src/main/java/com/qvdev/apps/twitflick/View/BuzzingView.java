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

/**
 * Created by dirkwilmer on 7/29/13.
 */

public class BuzzingView extends Fragment {
    private BuzzingPresenter mBuzzingPresenter;
    private GridView mBuzzingGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_buzzing, container, false);
        mBuzzingGridView = (GridView) rootView.findViewById(R.id.GridLayoutBuzzing);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBuzzingPresenter = new BuzzingPresenter(this);
    }

    public void setAdapter(BuzzingListAdapter adapter) {
        mBuzzingGridView.setAdapter(adapter);
    }

}