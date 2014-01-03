package com.qvdev.apps.twitflick.Presenter;

import android.view.View;

import com.qvdev.apps.twitflick.Adapters.TweetsListAdapter;
import com.qvdev.apps.twitflick.Model.TweetsModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.TweetsView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;

/**
 * Created by dirkwilmer on 11/25/13.
 */
public class TweetsPresenter implements View.OnClickListener {

    private TweetsView mTweetsView;
    private TweetsListAdapter mTweetsListAdapter;
    private TweetsModel mTweetsModel;

    public TweetsPresenter(TweetsView view) {
        mTweetsView = view;
        mTweetsModel = new TweetsModel();

        init();
    }

    private void init() {
        mTweetsListAdapter = new TweetsListAdapter(mTweetsView.getActivity(), R.layout.tweets_list_positive_circle_item, R.layout.tweets_list_negative_circle_item, mTweetsModel);
        mTweetsView.setAdapter(mTweetsListAdapter);
    }


    public void update(BuzzingDetail buzzingDetail) {
        mTweetsModel.setNegatives(buzzingDetail.getNegatives());
        mTweetsModel.setPositives(buzzingDetail.getPositives());

        mTweetsListAdapter.notifyDataSetChanged();
    }

    public void toggleTweets() {
        switch (mTweetsListAdapter.kind) {
            case POSITIVE:
                mTweetsListAdapter.kind = TweetsListAdapter.Kind.NEGATIVE;
                mTweetsListAdapter.notifyDataSetChanged();
                break;
            case NEGATIVE:
                mTweetsListAdapter.kind = TweetsListAdapter.Kind.POSITIVE;
                mTweetsListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_tweets_button:
                toggleTweets();
                break;
            default:
                break;
        }
    }
}
