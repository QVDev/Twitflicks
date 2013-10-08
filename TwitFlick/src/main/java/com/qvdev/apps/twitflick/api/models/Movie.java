package com.qvdev.apps.twitflick.api.models;

/**
 * Created by dirkwilmer on 10/8/13.
 */

public class Movie {
    private float ID;
    private String Name;
    private String Trailer;
    private String ShortSynposis;
    private float Rating;
    private String ReleaseYear;
    private String Duration;
    private String PosterUrl;
    private Integer Tweets;
    private Integer TweetsToday;

    public float getID() {
        return ID;
    }

    public void setID(float ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String Trailer) {
        this.Trailer = Trailer;
    }

    public String getShortSynposis() {
        return ShortSynposis;
    }

    public void setShortSynposis(String ShortSynposis) {
        this.ShortSynposis = ShortSynposis;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float Rating) {
        this.Rating = Rating;
    }

    public String getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(String ReleaseYear) {
        this.ReleaseYear = ReleaseYear;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String PosterUrl) {
        this.PosterUrl = PosterUrl;
    }

    public Integer getTweets() {
        return Tweets;
    }

    public void setTweets(Integer Tweets) {
        this.Tweets = Tweets;
    }

    public Integer getTweetsToday() {
        return TweetsToday;
    }

    public void setTweetsToday(Integer TweetsToday) {
        this.TweetsToday = TweetsToday;
    }

}