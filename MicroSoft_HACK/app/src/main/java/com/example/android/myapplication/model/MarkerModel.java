package com.example.android.myapplication.model;

import com.mmi.util.GeoPoint;

/**
 * Created by Mohammad Akram on 03-04-2015.
 */
public class MarkerModel {



    private GeoPoint geoPoint;


    public MarkerModel( GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }


    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
