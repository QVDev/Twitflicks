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
import com.qvdev.apps.twitflick.api.models.Tweet;
import com.qvdev.apps.twitflick.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsListAdapter extends BaseAdapter {

    public enum Kind {
        POSITIVE, NEGATIVE
    }

    private final TweetsModel mTweetsModel;
    private Context mContext;
    private int mResourcePositiveLayoutId;
    private int mResourceNegativeLayoutId;

    public Kind kind = Kind.NEGATIVE;

    public static class BuzzingViewHolder {

        public ImageButton userImage;
        public TextView userName;
        public TextView userText;
        public int position;
    }

    public TweetsListAdapter(Context context, int resourcePositiveLayoutId, int resourceNegativeLayoutId, TweetsModel model) {
        super();

        mContext = context;
        mTweetsModel = model;
        mResourcePositiveLayoutId = resourcePositiveLayoutId;
        mResourceNegativeLayoutId = resourceNegativeLayoutId;
    }


    public View getView(int position, View v, ViewGroup parent) {

        BuzzingViewHolder viewHolder;

        if (v == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(getLayout(), parent, false);

            viewHolder = new BuzzingViewHolder();

            viewHolder.userImage = (ImageButton) v.findViewById(R.id.tweet_user_icon);
            viewHolder.userName = (TextView) v.findViewById(R.id.tweets_username);
            viewHolder.userText = (TextView) v.findViewById(R.id.tweets_text);

            v.setTag(viewHolder);

        } else {
            viewHolder = (BuzzingViewHolder) v.getTag();
        }

        if (viewHolder.userName != null)
            viewHolder.userName.setText(getCorrectModel().get(position).getUsername());

        if (viewHolder.userText != null)
            viewHolder.userText.setText(getCorrectModel().get(position).getText());

        if (viewHolder.userImage != null) {
            String imageUrl = getCorrectModel().get(position).getImage();
            Picasso.with(v.getContext()).load(imageUrl).transform(new CircleTransform()).placeholder(android.R.drawable.ic_menu_gallery).into(viewHolder.userImage);
        }

        viewHolder.position = position;

        return v;
    }

    private List<Tweet> getCorrectModel() {
        switch (kind) {
            case POSITIVE:
                return mTweetsModel.getPositives();
            case NEGATIVE:
                return mTweetsModel.getNegatives();
            default:
                return null;
        }
    }

    private int getLayout() {
        switch (kind) {
            case POSITIVE:
                return mResourcePositiveLayoutId;
            case NEGATIVE:
                return mResourceNegativeLayoutId;
            default:
                return mResourcePositiveLayoutId;
        }
    }

    public int getCount() {
        switch (kind) {
            case POSITIVE:
                return mTweetsModel.getPositives().size();
            case NEGATIVE:
                return mTweetsModel.getNegatives().size();
            default:
                return 0;
        }


    }

    public Object getItem(int position) {
        switch (kind) {
            case POSITIVE:
                return mTweetsModel.getPositives().get(position);
            case NEGATIVE:
                return mTweetsModel.getNegatives().get(position);
            default:
                return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return Kind.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        switch (kind) {
            case POSITIVE:
                return Kind.POSITIVE.ordinal();
            case NEGATIVE:
                return Kind.NEGATIVE.ordinal();
            default:
                return Kind.POSITIVE.ordinal();
        }
    }

}
