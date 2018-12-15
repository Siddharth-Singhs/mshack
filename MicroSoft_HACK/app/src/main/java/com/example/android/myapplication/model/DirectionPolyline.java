package com.example.android.myapplication.model;

import com.mmi.util.GeoPoint;

import java.util.ArrayList;

public class DirectionPolyline {

    private GeoPoint start;
    private GeoPoint end;
    private ArrayList<GeoPoint> geoPoint;

    public GeoPoint getStart() {
        return start;
    }

    public void setStart(GeoPoint start) {
        this.start = start;
    }

    public GeoPoint getEnd() {
        return end;
    }

    public void setEnd(GeoPoint end) {
        this.end = end;
    }

    public ArrayList<GeoPoint> getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(ArrayList<GeoPoint> geoPoint) {
        this.geoPoint = geoPoint;
    }
}
