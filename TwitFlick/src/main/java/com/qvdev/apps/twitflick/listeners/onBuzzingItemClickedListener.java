package com.qvdev.apps.twitflick.listeners;

import android.view.View;

/**
 * Created by dirkwilmer on 9/25/13.
 */
public interface onBuzzingItemClickedListener {

    public void onTrailerClicked(String url);

    public void onPopupClicked(View view, int position);

    public void onLikeClicked(int position);

    public void onHateClicked(int position);

    public void onViewClicked(int position);
}
