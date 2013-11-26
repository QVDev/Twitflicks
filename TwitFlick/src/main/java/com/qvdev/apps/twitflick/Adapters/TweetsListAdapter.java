package com.qvdev.apps.twitflick.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Model.TweetsModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.utils.CircleTransform;
import com.squareup.picasso.Picasso;

public class TweetsListAdapter extends BaseAdapter {

    private final TweetsModel mTweetsModel;
    private Context mContext;
    private int mResourceLayoutId;

    /**
     * Viewholder used for smooth scrolling
     */
    public static class BuzzingViewHolder {

        public TextView userName;
        public ImageButton userImage;
        public int position;
    }


    /**
     * Constructor for Folder Content List Adapter.
     *
     * @param context          - Views context
     * @param resourceLayoutId - The resource layout that the listview cells should use
     * @param model            - List<BuzzingModel> - Items that the adapter should use
     */
    public TweetsListAdapter(Context context, int resourceLayoutId, TweetsModel model) {
        super();

        mContext = context;
        mTweetsModel = model;
        mResourceLayoutId = resourceLayoutId;
    }


    public View getView(int position, View v, ViewGroup parent) {

        BuzzingViewHolder viewHolder;

        if (v == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(mResourceLayoutId, parent, false);

            viewHolder = new BuzzingViewHolder();

            viewHolder.userName = (TextView) v.findViewById(R.id.tweets_username);
            viewHolder.userImage = (ImageButton) v.findViewById(R.id.tweet_user_icon);

            v.setTag(viewHolder);

        } else {
            viewHolder = (BuzzingViewHolder) v.getTag();
        }

        if (viewHolder.userName != null)
            viewHolder.userName.setText(mTweetsModel.getNegatives().get(position).getUsername());

        if (viewHolder.userImage != null) {
            String imageUrl = mTweetsModel.getNegatives().get(position).getImage();
            Picasso.with(v.getContext()).load(imageUrl).transform(new CircleTransform()).placeholder(android.R.drawable.ic_menu_gallery).into(viewHolder.userImage);
        }

        viewHolder.position = position;

        return v;
    }

    public int getCount() {
        return mTweetsModel.getNegatives().size();
    }

    public Object getItem(int position) {
        return mTweetsModel.getNegatives().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
