package org.milaifontanals.musicapp.model;

import android.graphics.Bitmap;

public class Artist {
    private String name;
    private String imgSrc;
    private Bitmap imgBitmap;


    public Artist(String name, String imgSrc) {
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }


    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
