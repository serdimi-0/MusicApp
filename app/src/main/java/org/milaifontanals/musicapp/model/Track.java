package org.milaifontanals.musicapp.model;

public class Track {
    private int number;
    private String title;
    private int duration;
    private boolean fav;

    public Track(int number, String title, int duration, boolean fav) {
        this.number = number;
        this.title = title;
        this.duration = duration;
        this.fav = fav;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
