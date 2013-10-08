package com.qvdev.apps.twitflick.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Presenter.DetailPresenter;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.squareup.picasso.Picasso;

/**
 * Created by dirkwilmer on 7/29/13.
 */

public class DetailView extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private DetailPresenter mDetailPresenter;

    private TextView mTitle;
    private ImageView mPoster;

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
        mTitle = (TextView) getActivity().findViewById(R.id.detail_title);
        mPoster = (ImageView) getActivity().findViewById(R.id.detail_poster);
    }

    public void setMovieInfo(BuzzingDetail buzzingDetail) {
        mTitle.setText(buzzingDetail.getMovie().getName());

        String imageUrl = "" + getString(R.string.base_url) + buzzingDetail.getMovie().getPosterUrl();
        Picasso.with(getActivity()).load(imageUrl).into(mPoster);
    }
}