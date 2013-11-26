package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qvdev.apps.twitflick.Adapters.TweetsListAdapter;
import com.qvdev.apps.twitflick.Presenter.TweetsPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by dirkwilmer on 11/24/13.
 */
public class TweetsView extends Fragment implements Observer {

    private TweetsPresenter mTweetsPresenter;
    private GridView mTweetsGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_tweets, container, false);
        initLayout(rootView);
        return rootView;
    }

    private void initLayout(View rootView) {
        mTweetsGridView = (GridView) rootView.findViewById(R.id.gridLayout_tweets);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTweetsPresenter = new TweetsPresenter(this);
    }

    public void setAdapter(TweetsListAdapter adapter) {
        mTweetsGridView.setAdapter(adapter);
    }

    @Override
    public void update(Observable observable, Object buzzingDetails) {
        mTweetsPresenter.update((BuzzingDetail) buzzingDetails);
    }

    public void setName(String name) {

    }
}
