
package com.qvdev.apps.twitflick.api.models;

public class Buzzing {
    private String Duration;
    private float ID;
    private String Name;
    private String PosterUrl;
    private float Rating;
    private String ReleaseYear;
    private String ShortSynposis;
    private String Trailer;
    private float Tweets;
    private float TweetsToday;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public float getID() {
        return ID;
    }

    public void setID(float ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        PosterUrl = posterUrl;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        ReleaseYear = releaseYear;
    }

    public String getShortSynposis() {
        return ShortSynposis;
    }

    public void setShortSynposis(String shortSynposis) {
        ShortSynposis = shortSynposis;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public float getTweets() {
        return Tweets;
    }

    public void setTweets(float tweets) {
        Tweets = tweets;
    }

    public float getTweetsToday() {
        return TweetsToday;
    }

    public void setTweetsToday(float tweetsToday) {
        TweetsToday = tweetsToday;
    }

}
