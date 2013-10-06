package com.qvdev.apps.twitflick.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;
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
        public ImageView itemPoster;
        public RatingBar itemRating;
        public ImageButton itemInfoButton;
        public ImageButton playTrailerButton;
        public ImageButton tweetLikeButton;
        public ImageButton tweetHateButton;
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
            viewHolder.itemPoster = (ImageView) v.findViewById(R.id.buzzingPoster);
            viewHolder.itemSummary = (TextView) v.findViewById(R.id.buzzingSummary);
            viewHolder.itemInfoButton = (ImageButton) v.findViewById(R.id.itemInformation);

            viewHolder.itemRating = (RatingBar) v.findViewById(R.id.buzzingRating);
            viewHolder.itemRating.setMax(5);

            viewHolder.playTrailerButton = (ImageButton) v.findViewById(R.id.playTrailerButton);
            setButtonOnClickListener(viewHolder.playTrailerButton);

            viewHolder.tweetLikeButton = (ImageButton) v.findViewById(R.id.tweetLikeButton);
            setButtonOnClickListener(viewHolder.tweetLikeButton);

            viewHolder.tweetHateButton = (ImageButton) v.findViewById(R.id.tweetHateButton);
            setButtonOnClickListener(viewHolder.tweetHateButton);

            v.setTag(viewHolder);

        } else {
            viewHolder = (BuzzingViewHolder) v.getTag();
        }

        viewHolder.itemName.setText(mBuzzingModel.getBuzzing().get(position).getName());
        viewHolder.itemRating.setRating(mBuzzingModel.getBuzzing().get(position).getRating());

        viewHolder.itemPoster.setImageResource(R.drawable.ic_launcher);
        String imageUrl = "" + v.getContext().getString(R.string.base_url) + mBuzzingModel.getBuzzing().get(position).getPosterUrl();
        Picasso.with(v.getContext()).load(imageUrl).into(viewHolder.itemPoster);

        viewHolder.itemSummary.setText(mBuzzingModel.getBuzzing().get(position).getShortSynposis());
        viewHolder.itemInfoButton.setTag(mBuzzingModel.getBuzzing().get(position).getID());
        viewHolder.playTrailerButton.setTag(mBuzzingModel.getBuzzing().get(position).getTrailer());
        viewHolder.tweetLikeButton.setTag(position);
        viewHolder.tweetHateButton.setTag(position);

        return v;
    }

    private void setButtonOnClickListener(final ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    switch (button.getId()) {
                        case R.id.playTrailerButton:
                            mListener.onTrailerClicked(button.getTag().toString());
                            break;
                        case R.id.tweetLikeButton:
                            mListener.onLikeClicked((Integer) button.getTag());
                            break;
                        case R.id.tweetHateButton:
                            mListener.onHateClicked((Integer) button.getTag());
                            break;
                        default:
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
