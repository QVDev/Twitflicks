package com.qvdev.apps.twitflick.Presenter;

import android.os.Bundle;
import android.widget.Toast;

import com.qvdev.apps.twitflick.Model.DetailModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.com.qvdev.apps.twitflick.network.NetworkHelper;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class DetailPresenter implements onBuzzingDetailsResultListener {

    private static final String DETAIL_ID = "detail_id";
    private DetailView mDetailView;
    private DetailModel mDetailModel;

    public DetailPresenter(DetailView detailView) {
        mDetailView = detailView;
        mDetailModel = new DetailModel();
    }

    public void getDetail(float movieId) {
        if ((int) movieId == 0 || (mDetailModel.getBuzzingDetail() != null && mDetailModel.getBuzzingDetail().getID() == movieId)) {
            return;
        }

        NetworkHelper networkHelper = new NetworkHelper();
        URL url = null;
        try {

            url = new URL("" + mDetailView.getString(R.string.base_url) + mDetailView.getString(R.string.api_url) + mDetailView.getString(R.string.buzzing_detail_url) + (int) movieId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.getBuzzingDetails(this, new URL[]{url});
    }


    @Override
    public void onBuzzingRetrievalSuccess(BuzzingDetail buzzingDetail) {
        mDetailModel.setBuzzingDetail(buzzingDetail);
        mDetailView.setMovieInfo(mDetailModel.getBuzzingDetail());
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        Toast.makeText(mDetailView.getActivity(), "Failed to fetch data", Toast.LENGTH_LONG).show();
    }

    public Bundle saveState(Bundle outState) {
        if (outState != null && mDetailModel.getBuzzingDetail() != null) {
            outState.putFloat(DETAIL_ID, mDetailModel.getBuzzingDetail().getID());
        }
        return outState;
    }

    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(DETAIL_ID)) {
            getDetail(savedInstanceState.getFloat(DETAIL_ID));
        }
    }
}
