package com.qvdev.apps.twitflick.Presenter;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.qvdev.apps.twitflick.DeveloperKey;
import com.qvdev.apps.twitflick.Model.DetailModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class DetailPresenter implements onBuzzingDetailsResultListener, YouTubePlayer.OnInitializedListener {

    private static final String DETAIL_ID = "detail_id";
    private DetailView mDetailView;
    private DetailModel mDetailModel;
    private YouTubePlayer mYoutubePlayer;

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
        playVideo();
    }

    private String getVideoId() {
        String youtubeUrl = mDetailModel.getBuzzingDetail().getMovie().getTrailer();
        int startIndex = youtubeUrl.lastIndexOf("=") + 1;
        String youtubeId = youtubeUrl.substring(startIndex, youtubeUrl.length());
        return youtubeId;
    }

    private void initYoutubeTrailerFragment() {
        if (mYoutubePlayer == null) {
            YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) mDetailView.getFragmentManager().findFragmentById(R.id.youtube_fragment);
            youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
        }
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        mYoutubePlayer = player;
        configureYoutubePlayer();
        playVideo();
    }

    private void playVideo() {

        if (mYoutubePlayer == null) {
            initYoutubeTrailerFragment();
        } else {
            mYoutubePlayer.cueVideo(getVideoId());
        }
    }

    private void configureYoutubePlayer() {
        mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        mYoutubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(mDetailView.getActivity(), "Failed to fetch video", Toast.LENGTH_LONG).show();
    }
}
