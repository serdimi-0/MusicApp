package org.milaifontanals.musicapp.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Album {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "artist")
    private String artist;
    @ColumnInfo(name = "imgSrc")
    private String imgSrc;
    @Ignore
    private Bitmap imgBitmap;
    @ColumnInfo(name = "release_date")
    private Date releaseDate;

    @Ignore
    private List<Track> trackList;

    public Album() {
    }

    public Album(String id, String title, String artist, Date releaseDate, String imgSrc) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imgSrc = imgSrc;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    @Override
    public String toString() {
        return "Album{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", artist='" + artist + '\'' + ", imgSrc='" + imgSrc + '\'' + ", date=" + releaseDate + '}';
    }
}
