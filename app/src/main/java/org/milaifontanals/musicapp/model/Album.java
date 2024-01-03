package org.milaifontanals.musicapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private long id;
    private String title;
    private String artist;
    private String imgSrc;
    private Bitmap imgBitmap;
    private short year;

    private List<Track> trackList;

    private static List<Album> albumList = new ArrayList<>();

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

    public static List<Album> getAlbumList() {
        return albumList;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public static void generateAlbumList(){
        Album a1 = new Album(1L, "BBO", "Hoke", (short) 2022, "https://lastfm.freetls.fastly.net/i/u/770x0/1e9fb81bd814ed33dff0aeef21f296bf.jpg");

        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Ojo de Halcón", 110000, false));
        tracks.add(new Track(2, "Five O", 140000, false));
        tracks.add(new Track(3, "Chorbo Real", 182000, false));
        tracks.add(new Track(4, "Jjjj", 207000, false));
        tracks.add(new Track(5, "Speedrun Skit", 66000, false));
        tracks.add(new Track(6, "Desamparados", 197000, false));
        tracks.add(new Track(7, "Medallones", 150000, false));
        tracks.add(new Track(8, "TT", 121000, false));
        tracks.add(new Track(9, "Automático", 154000, false));
        tracks.add(new Track(10, "Olympique", 152000, false));
        tracks.add(new Track(11, "Santo", 92000, false));
        a1.setTrackList(tracks);

        albumList.add(a1);
    }
}
