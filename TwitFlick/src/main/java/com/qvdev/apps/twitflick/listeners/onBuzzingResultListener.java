package com.qvdev.apps.twitflick.listeners;

import com.qvdev.apps.twitflick.api.models.Buzzing;

import java.util.List;

/**
 * Created by dirkwilmer on 9/25/13.
 */
public interface onBuzzingResultListener {

    public void onBuzzingRetrievalSuccess(List<Buzzing> buzzingList);

    public void onBuzzingRetrievalFailed();
}
