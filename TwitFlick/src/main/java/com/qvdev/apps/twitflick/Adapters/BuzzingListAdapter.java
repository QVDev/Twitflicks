package com.qvdev.apps.twitflick.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;
import com.qvdev.apps.twitflick.utils.CircleTransform;
import com.squareup.picasso.Picasso;

public class BuzzingListAdapter extends BaseAdapter {

    private final BuzzingModel mBuzzingModel;
    private Context mContext;
    private int mResourceLayoutId;
    private onBuzzingItemClickedListener mListener;

    /**
     * Viewholder used for smooth scrolling
     */
    public static class BuzzingViewHolder {

        public TextView itemName;
        public TextView itemSummary;
        public TextView itemTweetsToday;
        public TextView itemTweetsTotal;
        public ImageButton itemPoster;
        public RatingBar itemRating;
        public ImageButton itemInfoButton;
        public ImageButton playTrailerButton;
        public ImageButton tweetLikeButton;
        public ImageButton tweetHateButton;
        public int position;
    }


    /**
     * Constructor for Folder Content List Adapter.
     *
     * @param context          - Views context
     * @param resourceLayoutId - The resource layout that the listview cells should use
     * @param model            - List<BuzzingModel> - Items that the adapter should use
     */
    public BuzzingListAdapter(Context context, int resourceLayoutId, BuzzingModel model) {
        super();

        mContext = context;
        mBuzzingModel = model;
        mResourceLayoutId = resourceLayoutId;
    }


    public View getView(int position, View v, ViewGroup parent) {

        BuzzingViewHolder viewHolder;

        if (v == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(mResourceLayoutId, parent, false);

            viewHolder = new BuzzingViewHolder();

            viewHolder.itemName = (TextView) v.findViewById(R.id.buzzingName);
            viewHolder.itemPoster = (ImageButton) v.findViewById(R.id.buzzingPoster);
            setButtonOnClickListener(viewHolder.itemPoster);
            viewHolder.itemSummary = (TextView) v.findViewById(R.id.buzzingSummary);
            viewHolder.itemTweetsToday = (TextView) v.findViewById(R.id.buzzingToday);
            viewHolder.itemTweetsTotal = (TextView) v.findViewById(R.id.buzzingTotal);
            viewHolder.itemInfoButton = (ImageButton) v.findViewById(R.id.itemInformation);
            viewHolder.itemRating = (RatingBar) v.findViewById(R.id.buzzingRating);

            if (viewHolder.playTrailerButton != null) {
                viewHolder.playTrailerButton = (ImageButton) v.findViewById(R.id.playTrailerButton);
                setButtonOnClickListener(viewHolder.playTrailerButton);
            }

            if (viewHolder.tweetLikeButton != null) {
                viewHolder.tweetLikeButton = (ImageButton) v.findViewById(R.id.tweetLikeButton);
                setButtonOnClickListener(viewHolder.tweetLikeButton);
            }

            if (viewHolder.tweetHateButton != null) {
                viewHolder.tweetHateButton = (ImageButton) v.findViewById(R.id.tweetHateButton);
                setButtonOnClickListener(viewHolder.tweetHateButton);
            }

            v.setTag(viewHolder);

        } else {
            viewHolder = (BuzzingViewHolder) v.getTag();
        }

        if (viewHolder.itemName != null)
            viewHolder.itemName.setText(mBuzzingModel.getBuzzing().get(position).getName());
        if (viewHolder.itemRating != null)
            viewHolder.itemRating.setRating(mBuzzingModel.getBuzzing().get(position).getRating());
        if (viewHolder.itemSummary != null)
            viewHolder.itemSummary.setText(mBuzzingModel.getBuzzing().get(position).getShortSynposis());
        if (viewHolder.itemTweetsToday != null)
            viewHolder.itemTweetsToday.setText(v.getContext().getString(R.string.tweets_today, (int) mBuzzingModel.getBuzzing().get(position).getTweetsToday()));
        if (viewHolder.itemTweetsTotal != null)
            viewHolder.itemTweetsTotal.setText(v.getContext().getString(R.string.tweets_total, (int) mBuzzingModel.getBuzzing().get(position).getTweets()));

        if (viewHolder.itemPoster != null) {
            String imageUrl = "" + v.getContext().getString(R.string.base_url) + mBuzzingModel.getBuzzing().get(position).getPosterUrl();
            Picasso.with(v.getContext()).load(imageUrl).transform(new CircleTransform()).placeholder(android.R.drawable.ic_menu_gallery).into(viewHolder.itemPoster);
        }


        if (viewHolder.itemInfoButton != null)
            viewHolder.itemInfoButton.setTag(mBuzzingModel.getBuzzing().get(position).getID());
        if (viewHolder.playTrailerButton != null)
            viewHolder.playTrailerButton.setTag(mBuzzingModel.getBuzzing().get(position).getTrailer());
        if (viewHolder.itemPoster != null)
            viewHolder.itemPoster.setTag(mBuzzingModel.getBuzzing().get(position).getTrailer());
        if (viewHolder.tweetLikeButton != null)
            viewHolder.tweetLikeButton.setTag(position);
        if (viewHolder.tweetHateButton != null)
            viewHolder.tweetHateButton.setTag(position);

        viewHolder.position = position;
        setButtonOnClickListener(v);

        return v;
    }

    private void setButtonOnClickListener(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    switch (view.getId()) {
                        case R.id.buzzingPoster:
                            mListener.onTrailerClicked(view.getTag().toString());
                            break;
                        case R.id.tweetLikeButton:
                            mListener.onLikeClicked((Integer) view.getTag());
                            break;
                        case R.id.tweetHateButton:
                            mListener.onHateClicked((Integer) view.getTag());
                            break;
                        default:
                            BuzzingViewHolder buzzing = (BuzzingViewHolder) view.getTag();
                            mListener.onViewClicked(buzzing.position);
                            break;
                    }

                }
            }
        });
    }

    public void setOnBuzzingItemClicked(onBuzzingItemClickedListener listener) {
        mListener = listener;
    }

    public int getCount() {
        return mBuzzingModel.getBuzzing().size();
    }


    public Object getItem(int position) {
        return mBuzzingModel.getBuzzing().get(position);
    }


    public long getItemId(int position) {

        return position;
    }
}
