package com.qvdev.apps.twitflick.Presenter;

import android.content.Intent;
import android.widget.Toast;

import com.qvdev.apps.twitflick.Model.DetailModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observer;

/**
 * Created by QVDev on 7/26/13.
 */
public class DetailPresenter implements onBuzzingDetailsResultListener {

    public final static String EXTRA_MESSAGE_ID = "movie_id";

    private final DetailView mDetailView;
    private DetailModel mDetailModel;

    public DetailPresenter(DetailView view) {
        mDetailView = view;
        mDetailModel = new DetailModel();

        Intent intent = mDetailView.getIntent();

        if (intent != null) {
            float id = intent.getFloatExtra(EXTRA_MESSAGE_ID, 0);
            if (id != -1) {
                getBuzzingDetails(id);
            }
        }
    }

    public void addBuzzingDetailsObserver(Observer observer) {
        mDetailModel.addObserver(observer);
    }

    public void getBuzzingDetails(float movieId) {
        if ((int) movieId == 0 || (mDetailModel.getBuzzingDetail() != null && mDetailModel.getBuzzingDetail().getID() == movieId)) {
            return;
        }

        NetworkHelper networkHelper = new NetworkHelper(mDetailView);
        URL url = null;
        try {

            url = new URL("" + mDetailView.getString(R.string.base_url) + mDetailView.getString(R.string.api_url) + mDetailView.getString(R.string.buzzing_detail_url) + (int) movieId + "&count=150");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.getBuzzingDetails(this, new URL[]{url});
    }

    @Override
    public void onBuzzingRetrievalSuccess(BuzzingDetail buzzingDetail) {
        mDetailModel.setBuzzingDetail(buzzingDetail);
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        Toast.makeText(mDetailView, "Failed to fetch data", Toast.LENGTH_LONG).show();
    }
}
