package com.qvdev.apps.twitflick.api.models;

/**
 * Created by dirkwilmer on 10/8/13.
 */

import java.util.ArrayList;
import java.util.List;

public class BuzzingDetail {

    private float ID;

    private String Timestamp;

    private com.qvdev.apps.twitflick.api.models.Movie Movie;

    private List<Tweet> Positives = new ArrayList<Tweet>();

    private List<Tweet> Negatives = new ArrayList<Tweet>();

    public float getID() {
        return ID;
    }

    public void setID(float ID) {
        this.ID = ID;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    public com.qvdev.apps.twitflick.api.models.Movie getMovie() {
        return Movie;
    }

    public void setMovie(com.qvdev.apps.twitflick.api.models.Movie Movie) {
        this.Movie = Movie;
    }

    public List<Tweet> getPositives() {
        return Positives;
    }

    public void setPositives(List<Tweet> Positives) {
        this.Positives = Positives;
    }

    public List<Tweet> getNegatives() {
        return Negatives;
    }

    public void setNegatives(List<Tweet> Negatives) {
        this.Negatives = Negatives;
    }

}