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
import com.squareup.picasso.Picasso;

public class BuzzingListAdapter extends BaseAdapter {

    private final BuzzingModel mBuzzingModel;
    private Context mContext;
    private int mResourceLayoutId;

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
        public ImageButton tweetsTodayButton;
        public ImageButton tweetsTotalButton;
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
            viewHolder.itemRating = (RatingBar) v.findViewById(R.id.buzzingRating);
            viewHolder.itemRating.setMax(5);
            viewHolder.itemSummary = (TextView) v.findViewById(R.id.buzzingSummary);
            viewHolder.itemInfoButton = (ImageButton) v.findViewById(R.id.itemInformation);
            viewHolder.playTrailerButton = (ImageButton) v.findViewById(R.id.playTrailerButton);
            viewHolder.tweetsTodayButton = (ImageButton) v.findViewById(R.id.tweetsTodayButton);
            viewHolder.tweetsTotalButton = (ImageButton) v.findViewById(R.id.tweetsTotalButton);
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
        viewHolder.tweetsTodayButton.setTag(mBuzzingModel.getBuzzing().get(position).getTweetsToday());
        viewHolder.tweetsTotalButton.setTag(mBuzzingModel.getBuzzing().get(position).getTweets());

        return v;
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