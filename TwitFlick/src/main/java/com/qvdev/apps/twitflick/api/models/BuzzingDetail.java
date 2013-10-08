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

    private List<Positive> Positives = new ArrayList<Positive>();

    private List<Negative> Negatives = new ArrayList<Negative>();

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

    public List<Positive> getPositives() {
        return Positives;
    }

    public void setPositives(List<Positive> Positives) {
        this.Positives = Positives;
    }

    public List<Negative> getNegatives() {
        return Negatives;
    }

    public void setNegatives(List<Negative> Negatives) {
        this.Negatives = Negatives;
    }

}