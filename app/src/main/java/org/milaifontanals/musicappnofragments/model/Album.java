package org.milaifontanals.musicappnofragments.model;

import java.util.List;

public class Album {
    private long id;
    private String title;
    private String artist;

    private String imgSrc;
    private short year;

    private List<Track> trackList;

    public Album(long id, String title, String artist, short year, String imgSrc) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imgSrc = imgSrc;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
