package com.example.project_class;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ImageInfo implements Serializable {
    private Bitmap bitmap;
    private String place;
    private String date;

    public ImageInfo(Bitmap bitmap, String place, String date) {
        this.bitmap = bitmap;
        this.place = place;
        this.date = date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
