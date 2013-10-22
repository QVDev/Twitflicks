package com.qvdev.apps.twitflick.View;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Presenter.DetailPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by dirkwilmer on 7/29/13.
 */

public class DetailView extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private DetailPresenter mDetailPresenter;

    private LinearLayout mRootView;
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mSummary;


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
        Bundle args = getArguments();
        float buzzingId = args.getFloat("id");
        mDetailPresenter.getDetail(buzzingId);
    }

    private void initLayout() {
        mRootView = (LinearLayout) getActivity().findViewById(R.id.buzzing_detail);
//        mPoster = (ImageView) getActivity().findViewById(R.id.detail_poster);
        mTitle = (TextView) getActivity().findViewById(R.id.detail_title);
        mSummary = (TextView) getActivity().findViewById(R.id.detail_summary);
    }

    public void setMovieInfo(BuzzingDetail buzzingDetail) {
        String imageUrl = "" + getString(R.string.base_url) + buzzingDetail.getMovie().getPosterUrl();

        Target tar = new Target() {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getActivity().getResources(), bitmap);
                drawable.setAlpha(60);
                mRootView.setBackground(drawable);
            }

            @Override
            public void onError() {

            }
        };


        mTitle.setText(buzzingDetail.getMovie().getName());
        mSummary.setText(buzzingDetail.getMovie().getShortSynposis());

        Picasso.with(getActivity()).load(imageUrl).into(tar);
    }
}