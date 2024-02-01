package org.milaifontanals.musicapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"number", "album_id"},
        foreignKeys = @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "album_id"))
public class Track {
    @ColumnInfo(name = "album_id")
    public long albumId;
    private int number;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "duration")
    private int duration;
    @ColumnInfo(name = "fav")
    private boolean fav;


    public Track() {
    }

    public Track(long albumId, int number, String title, int duration, boolean fav) {
        this.albumId = albumId;
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
