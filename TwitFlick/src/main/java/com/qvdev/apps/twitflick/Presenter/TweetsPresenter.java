package com.qvdev.apps.twitflick.Presenter;

import android.widget.Toast;

import com.qvdev.apps.twitflick.Adapters.TweetsListAdapter;
import com.qvdev.apps.twitflick.Model.TweetsModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.TweetsView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;

/**
 * Created by dirkwilmer on 11/25/13.
 */
public class TweetsPresenter {

    private TweetsView mTweetsView;
    private TweetsListAdapter mTweetsListAdapter;
    private TweetsModel mTweetsModel;

    public TweetsPresenter(TweetsView view) {
        mTweetsView = view;
        mTweetsModel = new TweetsModel();

        init();
    }

    private void init() {
        mTweetsListAdapter = new TweetsListAdapter(mTweetsView.getActivity(), R.layout.tweets_list_circle_item, mTweetsModel);
        mTweetsView.setAdapter(mTweetsListAdapter);
    }


    public void update(BuzzingDetail buzzingDetail) {
        Toast.makeText(mTweetsView.getActivity(), "Tweets::" + buzzingDetail.getMovie().getName(), Toast.LENGTH_LONG).show();
        mTweetsModel.setNegatives(buzzingDetail.getNegatives());
        mTweetsModel.setPositives(buzzingDetail.getPositives());

        mTweetsListAdapter.notifyDataSetChanged();
    }
}
