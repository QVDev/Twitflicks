package com.qvdev.apps.twitflick.listeners;

import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;

import java.util.List;

/**
 * Created by QVDev on 9/25/13.
 */
public interface onBuzzingDetailsResultListener {

    public void onBuzzingRetrievalSuccess(BuzzingDetail buzzingDetail);

    public void onBuzzingRetrievalFailed();
}
