
package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Movie {
    
    private @Id @GeneratedValue Long id;
    private String title;
    private String gener;
    private int duration;
    private int rating;
    private int release_year;

    Movie() {}

    Movie(String title, String gener, int duration, int rating, int release_year){
        this.title = title;
        this.gener = gener;
        this.duration = duration;
        this.rating = rating;
        this.release_year = release_year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }
    
}