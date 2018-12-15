package com.example.android.myapplication.fragments;

import android.os.Bundle;


import com.example.android.myapplication.R;
import com.example.android.myapplication.model.MarkerModel;
import com.example.android.myapplication.util.Data;
import com.example.android.myapplication.util.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mmi.layers.BasicInfoWindow;
import com.mmi.layers.Marker;
import com.mmi.util.GeoPoint;

import java.util.ArrayList;


public class MarkersInfoWindowFragment extends MapBaseFragment {

    private static final String TAG = MarkersInfoWindowFragment.class.getSimpleName();
    private static String longitude;
    private static String latitude;
    @Override
    public String getSampleTitle() {
        return "Maps Marker";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        longitude = getArguments().getString("Longitude");// data which sent from activity
        latitude=getArguments().getString("Latitude");
        ArrayList<MarkerModel> markerModels = new ArrayList<>();
        markerModels.add(new MarkerModel( new GeoPoint(28.549356, 77.26780099999999)));
        markerModels.add(new MarkerModel( new GeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude))));
        markerModels.add(new MarkerModel( new GeoPoint(28.554454, 77.265473)));
        markerModels.add(new MarkerModel( new GeoPoint(28.549637999999998, 77.262909)));
        markerModels.add(new MarkerModel( new GeoPoint(28.555245, 77.266117)));
        markerModels.add(new MarkerModel( new GeoPoint(28.558149, 77.269787)));
        markerModels.add(new MarkerModel( new GeoPoint(28.555369, 77.271042)));
        markerModels.add(new MarkerModel( new GeoPoint(28.544428, 77.279057)));
        markerModels.add(new MarkerModel(new GeoPoint(28.538275, 77.283821)));
        markerModels.add(new MarkerModel( new GeoPoint(28.536604999999998, 77.2872)));
        markerModels.add(new MarkerModel( new GeoPoint(28.538442999999997, 77.291921)));
        markerModels.add(new MarkerModel( new GeoPoint(28.542326, 77.30133)));
        markerModels.add(new MarkerModel( new GeoPoint(28.542609, 77.30211299999999)));
        markerModels.add(new MarkerModel(new GeoPoint(28.543042999999997, 77.302843)));


        addOverLays(markerModels);
    }


    void addOverLays(ArrayList<MarkerModel> markerModels) {
        ArrayList<GeoPoint> points = new ArrayList<>();
        BasicInfoWindow infoWindow = new BasicInfoWindow(R.layout.tooltip, mMapView);

        infoWindow.setTipColor(getResources().getColor(R.color.base_color));
        for (MarkerModel markerModel : markerModels) {
            Marker marker = new Marker(mMapView);
            //marker.setTitle(markerModel.getTitle());
            //marker.setDescription(markerModel.getDescription());
            //marker.setSubDescription(markerModel.getSubDescription());

            marker.setPosition(markerModel.getGeoPoint());

            marker.setInfoWindow(infoWindow);
            marker.setRelatedObject(markerModel);
            mMapView.getOverlays().add(marker);
            points.add(markerModel.getGeoPoint());
        }
        mMapView.invalidate();
        mMapView.setBounds(points);
    }


    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        BasicInfoWindow.closeAllInfoWindowsOn(mMapView);
        return super.singleTapConfirmedHelper(p);
    }


}
