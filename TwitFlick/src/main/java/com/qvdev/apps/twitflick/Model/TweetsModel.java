package com.qvdev.apps.twitflick.Model;

import com.qvdev.apps.twitflick.api.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QVDev on 11/25/13.
 */
public class TweetsModel {

    private List<Tweet> mPositives = new ArrayList<Tweet>();
    private List<Tweet> mNegatives = new ArrayList<Tweet>();

    public List<Tweet> getPositives() {
        return mPositives;
    }

    public void setPositives(List<Tweet> positives) {
        mPositives = positives;
    }

    public List<Tweet> getNegatives() {
        return mNegatives;
    }

    public void setNegatives(List<Tweet> negatives) {
        mNegatives = negatives;
    }
}
