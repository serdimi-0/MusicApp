package org.milaifontanals.musicappnofragments.model;

public class Track {
    private int number;
    private String title;
    private int duration;

    public Track(int number, String title, int duration) {
        this.number = number;
        this.title = title;
        this.duration = duration;
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
}
