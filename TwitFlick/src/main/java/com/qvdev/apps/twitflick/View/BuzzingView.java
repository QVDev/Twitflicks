package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Presenter.BuzzingPresenter;
import com.qvdev.apps.twitflick.R;

/**
 * Created by dirkwilmer on 7/29/13.
 */

public class BuzzingView extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private BuzzingPresenter mBuzzingPresenter;
    private ListView mDummyListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_buzzing, container, false);
        mDummyListView = (ListView) rootView.findViewById(R.id.listViewBuzzing);
        mBuzzingPresenter = new BuzzingPresenter(this);
        mBuzzingPresenter.getBuzzing();
        return rootView;
    }


    public void setAdapter(BuzzingListAdapter adapter) {
        mDummyListView.setAdapter(adapter);
    }
}