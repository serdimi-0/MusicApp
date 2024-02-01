package org.milaifontanals.musicapp.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Album {
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "artist")
    private String artist;
    @ColumnInfo(name = "imgSrc")
    private String imgSrc;
    @Ignore
    private Bitmap imgBitmap;
    @ColumnInfo(name = "year")
    private short year;

    @Ignore
    private List<Track> trackList;

    public Album() {}

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

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

}
