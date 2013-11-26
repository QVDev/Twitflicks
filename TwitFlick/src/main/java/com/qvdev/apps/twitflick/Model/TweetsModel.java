package com.qvdev.apps.twitflick.Model;

import com.qvdev.apps.twitflick.api.models.Negative;
import com.qvdev.apps.twitflick.api.models.Positive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dirkwilmer on 11/25/13.
 */
public class TweetsModel {

    private List<Positive> mPositives = new ArrayList<Positive>();
    private List<Negative> mNegatives = new ArrayList<Negative>();

    public List<Positive> getPositives() {
        return mPositives;
    }

    public void setPositives(List<Positive> positives) {
        mPositives = positives;
    }

    public List<Negative> getNegatives() {
        return mNegatives;
    }

    public void setNegatives(List<Negative> negatives) {
        mNegatives = negatives;
    }
}
