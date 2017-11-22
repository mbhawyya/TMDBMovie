package com.bhawyyamittal.tabbedactivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class IMDBResponse {
    private List<IMDBTop> imdbTops = new ArrayList<IMDBTop>();
    public List<IMDBTop> getResponse() {
        return imdbTops;
    }
}

class IMDBTop {

    @SerializedName("date_scraped")
    @Expose
    private Object dateScraped;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("year")
    @Expose
    private String year;


    public Object getDateScraped() {
        return dateScraped;
    }

    public void setDateScraped(Object dateScraped) {
        this.dateScraped = dateScraped;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}