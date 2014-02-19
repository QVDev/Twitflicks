package com.qvdev.apps.twitflick.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;
import com.qvdev.apps.twitflick.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuzzingListAdapter extends BaseAdapter implements Filterable {

    public enum BuzzingType {
        PULL, BUZZING
    }

    private final BuzzingModel mBuzzingModel;
    private Context mContext;
    private int mResourceLayoutId;
    private onBuzzingItemClickedListener mListener;
    private Filter mFilter;

    /**
     * Viewholder used for smooth scrolling
     */
    public static class BuzzingViewHolder {

        public TextView itemName;
        public TextView itemTweetsToday;
        public TextView itemTweetsTotal;
        public ImageButton itemPoster;
        public ImageButton popupMenuButton;
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

        if (getItemViewType(position) == BuzzingType.PULL.ordinal()) {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.buzzing_item_pull, parent, false);
            }
        } else {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(mResourceLayoutId, parent, false);

                viewHolder = new BuzzingViewHolder();

                viewHolder.itemName = (TextView) v.findViewById(R.id.buzzingName);
                viewHolder.itemPoster = (ImageButton) v.findViewById(R.id.buzzingPoster);
                viewHolder.itemTweetsToday = (TextView) v.findViewById(R.id.buzzingToday);
                viewHolder.itemTweetsTotal = (TextView) v.findViewById(R.id.buzzingTotal);
                viewHolder.popupMenuButton = (ImageButton) v.findViewById(R.id.buzzing_popup_menu);


                if (viewHolder.popupMenuButton != null) {
                    viewHolder.popupMenuButton = (ImageButton) v.findViewById(R.id.buzzing_popup_menu);
                    setButtonOnClickListener(viewHolder.popupMenuButton);
                }

                v.setTag(viewHolder);

            } else {
                viewHolder = (BuzzingViewHolder) v.getTag();
            }

            if (viewHolder.itemName != null)
                viewHolder.itemName.setText(mBuzzingModel.getBuzzing().get(position).getName());
            if (viewHolder.itemTweetsToday != null)
                viewHolder.itemTweetsToday.setText(v.getContext().getString(R.string.tweets_today, (int) mBuzzingModel.getBuzzing().get(position).getTweetsToday()));
            if (viewHolder.itemTweetsTotal != null)
                viewHolder.itemTweetsTotal.setText(v.getContext().getString(R.string.tweets_total, (int) mBuzzingModel.getBuzzing().get(position).getTweets()));

            if (viewHolder.itemPoster != null) {
                String imageUrl = "" + v.getContext().getString(R.string.base_url) + mBuzzingModel.getBuzzing().get(position).getPosterUrl();
                Picasso.with(v.getContext()).load(imageUrl).transform(new CircleTransform()).placeholder(android.R.drawable.ic_menu_gallery).into(viewHolder.itemPoster);
            }


            if (viewHolder.itemPoster != null)
                viewHolder.itemPoster.setTag(mBuzzingModel.getBuzzing().get(position).getTrailer());
            if (viewHolder.popupMenuButton != null)
                viewHolder.popupMenuButton.setTag(position);

            viewHolder.position = position;
            setButtonOnClickListener(v);
        }

        return v;
    }

    private void setButtonOnClickListener(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    switch (view.getId()) {
                        case R.id.buzzing_popup_menu:
                            mListener.onPopupClicked(view, (Integer) view.getTag());
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

    @Override
    public int getViewTypeCount() {
        return BuzzingType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        BuzzingType type = ((Buzzing) getItem(position)).getType();
        switch (type) {
            case PULL:
                return BuzzingType.PULL.ordinal();
            case BUZZING:
                return BuzzingType.BUZZING.ordinal();
            default:
                return BuzzingType.BUZZING.ordinal();
        }
    }


    public long getItemId(int position) {

        return position;
    }

    @Override
    public Filter getFilter() {

        if (mFilter == null) {
            mFilter = new Filter() {

                List<Buzzing> allBuzzings = mBuzzingModel.getBuzzing();

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    if (constraint == null || constraint.length() == 0) {
                        results.values = allBuzzings;
                        results.count = allBuzzings.size();
                    } else {
                        List<Buzzing> buzzingFilteredList = new ArrayList<Buzzing>();

                        for (Buzzing buzzing : allBuzzings) {
                            if (buzzing.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                buzzingFilteredList.add(buzzing);
                            }
                        }

                        results.values = buzzingFilteredList;
                        results.count = buzzingFilteredList.size();

                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

//                    if (filterResults.count == 0)
//                        notifyDataSetInvalidated();
//                    else {
                        mBuzzingModel.setBuzzing((List<Buzzing>) filterResults.values);
                        notifyDataSetChanged();
//                    }
                }
            };
        }

        return mFilter;
    }
}
