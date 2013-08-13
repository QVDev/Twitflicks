package com.qvdev.apps.twitflick.Presenter;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.com.qvdev.apps.twitflick.network.NetworkHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class BuzzingPresenter {

    private BuzzingView mBuzzingView;
    private BuzzingModel mBuzzingModel;
    private BuzzingListAdapter mBuzzingListAdapter;

    public BuzzingPresenter(BuzzingView buzzingView) {
        mBuzzingView = buzzingView;
        mBuzzingModel = new BuzzingModel();

        init();
    }

    private void init() {
        mBuzzingListAdapter = new BuzzingListAdapter(mBuzzingView.getActivity(), R.layout.buzzing_list_item, mBuzzingModel);
        mBuzzingView.setAdapter(mBuzzingListAdapter);
    }


    public void getBuzzing() {
        NetworkHelper networkHelper = new NetworkHelper(this);
        URL url = null;
        try {
            url = new URL("" + mBuzzingView.getString(R.string.base_url) + mBuzzingView.getString(R.string.api_url) + mBuzzingView.getString(R.string.buzzing_url) + mBuzzingView.getString(R.string.buzzing_retrieve_count) + mBuzzingView.getString(R.string.buzzing_retrieve_limit));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.execute(new URL[]{url});
    }

    public void refresh(List<Buzzing> result) {
        mBuzzingModel.getBuzzing().clear();
        mBuzzingModel.getBuzzing().addAll(result);
        mBuzzingListAdapter.notifyDataSetChanged();
    }
}
