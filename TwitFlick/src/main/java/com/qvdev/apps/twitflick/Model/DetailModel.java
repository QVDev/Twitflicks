package com.qvdev.apps.twitflick.Model;

import com.qvdev.apps.twitflick.api.models.BuzzingDetail;

import java.util.Observable;

/**
 * Created by QVDev on 7/29/13.
 */
public class DetailModel extends Observable {

    private BuzzingDetail mBuzzingDetail;

    public BuzzingDetail getBuzzingDetail() {
        return mBuzzingDetail;
    }

    public void setBuzzingDetail(BuzzingDetail buzzingDetail) {
        mBuzzingDetail = buzzingDetail;
        triggerUpdate();
    }

    private void triggerUpdate() {
        setChanged();
        notifyObservers(mBuzzingDetail);
    }

}
