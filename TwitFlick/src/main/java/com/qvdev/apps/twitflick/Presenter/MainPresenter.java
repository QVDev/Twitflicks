package com.qvdev.apps.twitflick.Presenter;

import android.content.Intent;
import android.widget.Toast;

import com.qvdev.apps.twitflick.Model.MainModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.MainView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observer;

/**
 * Created by QVDev on 7/26/13.
 */
public class MainPresenter implements onBuzzingDetailsResultListener {

    public final static String EXTRA_MESSAGE_ID = "movie_id";

    private final MainView mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainView view) {
        mMainView = view;
        mMainModel = new MainModel();

        Intent intent = mMainView.getIntent();

        if (intent != null) {
            float id = intent.getFloatExtra(EXTRA_MESSAGE_ID, 0);
            if (id != -1) {
                getBuzzingDetails(id);
            }
        }
    }

    public void addBuzzingDetailsObserver(Observer observer) {
        mMainModel.detailModel.addObserver(observer);
    }

    public void getBuzzingDetails(float movieId) {
        if ((int) movieId == 0 || (mMainModel.detailModel.getBuzzingDetail() != null && mMainModel.detailModel.getBuzzingDetail().getID() == movieId)) {
            return;
        }

        NetworkHelper networkHelper = new NetworkHelper(mMainView);
        URL url = null;
        try {

            url = new URL("" + mMainView.getString(R.string.base_url) + mMainView.getString(R.string.api_url) + mMainView.getString(R.string.buzzing_detail_url) + (int) movieId + "&count=150");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.getBuzzingDetails(this, new URL[]{url});
    }

    @Override
    public void onBuzzingRetrievalSuccess(BuzzingDetail buzzingDetail) {
        mMainModel.detailModel.setBuzzingDetail(buzzingDetail);
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        Toast.makeText(mMainView, "Failed to fetch data", Toast.LENGTH_LONG).show();
    }
}
