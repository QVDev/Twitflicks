package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.qvdev.apps.twitflick.Presenter.DetailPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingListItemClicked;
import com.squareup.picasso.Picasso;

/**
 * Created by dirkwilmer on 7/29/13.
 */

public class DetailView extends YouTubePlayerSupportFragment implements onBuzzingListItemClicked {
    private DetailPresenter mDetailPresenter;

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mSummary;
    private RatingBar mRating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_detail, container, false);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDetailPresenter = new DetailPresenter(this);
        initLayout();
        mDetailPresenter.restoreState(savedInstanceState);
    }

    private void initLayout() {
        mPoster = (ImageView) getActivity().findViewById(R.id.detail_poster);
        mTitle = (TextView) getActivity().findViewById(R.id.detail_title);
        mSummary = (TextView) getActivity().findViewById(R.id.detail_summary);
        mRating = (RatingBar) getActivity().findViewById(R.id.detail_rating);
    }

    public void setMovieInfo(BuzzingDetail buzzingDetail) {
        String imageUrl = "" + getString(R.string.base_url) + buzzingDetail.getMovie().getPosterUrl();
        Picasso.with(getActivity()).load(imageUrl).into(mPoster);

        mTitle.setText(buzzingDetail.getMovie().getName());
        mSummary.setText(buzzingDetail.getMovie().getShortSynposis());
        mRating.setRating(buzzingDetail.getMovie().getRating());
    }

    @Override
    public void onBuzzingItemSelected(float buzzingId) {
        mDetailPresenter.getDetail(buzzingId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = mDetailPresenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}