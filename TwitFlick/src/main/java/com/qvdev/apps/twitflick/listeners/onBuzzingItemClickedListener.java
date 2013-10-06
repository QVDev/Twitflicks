package com.qvdev.apps.twitflick.listeners;

/**
 * Created by dirkwilmer on 9/25/13.
 */
public interface onBuzzingItemClickedListener {

    public void onTrailerClicked(String url);

    public void onLikeClicked(int position);

    public void onHateClicked(int position);
}
