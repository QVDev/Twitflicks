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
    private ListView mBuzzingListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_buzzing, container, false);
        mBuzzingListView = (ListView) rootView.findViewById(R.id.listViewBuzzing);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBuzzingPresenter = new BuzzingPresenter(this);
    }

    public void setAdapter(BuzzingListAdapter adapter) {
        mBuzzingListView.setAdapter(adapter);
    }

}