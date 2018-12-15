package com.example.android.myapplication.fragments;

import android.os.Bundle;


import com.example.android.myapplication.R;
import com.example.android.myapplication.model.MarkerModel;
import com.example.android.myapplication.util.Data;
import com.example.android.myapplication.util.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mmi.layers.BasicInfoWindow;
import com.mmi.layers.Marker;
import com.mmi.layers.MarkerClusterer;
import com.mmi.util.GeoPoint;

import java.util.ArrayList;


public class MarkersInfoWindowFragment extends MapBaseFragment {

    private static final String TAG = MarkersInfoWindowFragment.class.getSimpleName();
    private static String longitude;
    private static String latitude;
    private ArrayList<GeoPoint> greenPoint;
    private ArrayList<GeoPoint> redPoint;
    @Override
    public String getSampleTitle() {
        return "Maps Marker";
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        longitude = getArguments().getString("Longitude");// data which sent from activity
        latitude=getArguments().getString("Latitude");
        greenPoint=getArguments().getParcelableArrayList("GreenZone");
        redPoint=getArguments().getParcelableArrayList("RedZone");
        ArrayList<MarkerModel> greenMarkerModels = new ArrayList<>();
        for(int i=0;i<greenPoint.size();i++)
        {
            greenMarkerModels.add(new MarkerModel(greenPoint.get(i)));
        }
        ArrayList<MarkerModel> redMarkerModels=new ArrayList<>();
        for(int i=0;i<redPoint.size();i++)
        {
            redMarkerModels.add(new MarkerModel(redPoint.get(i)));
        }


        addOverLays(greenMarkerModels,redMarkerModels);
    }

    void addOverLays(ArrayList<MarkerModel> greenModels,ArrayList<MarkerModel> redModel) {
       /** ArrayList<GeoPoint> points = new ArrayList<>();**/
        BasicInfoWindow infoWindow = new BasicInfoWindow(R.layout.tooltip, mMapView);

        infoWindow.setTipColor(getResources().getColor(R.color.base_color));

      MarkerClusterer redMarkerCluster=new MarkerClusterer(getActivity());
       MarkerClusterer greenMarkerCluster=new MarkerClusterer(getActivity());

       greenMarkerCluster.setColor(getResources().getColor(R.color.green_color));
        redMarkerCluster.setColor(getResources().getColor(R.color.red_color));

        greenMarkerCluster.mAnchorV=Marker.ANCHOR_CENTER;
        redMarkerCluster.mAnchorV=Marker.ANCHOR_CENTER;

        greenMarkerCluster.mTextAnchorV=Marker.ANCHOR_CENTER;
        redMarkerCluster.mTextAnchorV=Marker.ANCHOR_CENTER;

        greenMarkerCluster.setTextSize(12);
        redMarkerCluster.setTextSize(12);

        ArrayList<GeoPoint> points = new ArrayList<>();
        MarkerModel currentLocation=new MarkerModel(new GeoPoint(Double.parseDouble(latitude),Double.parseDouble(longitude)));
        Marker currentMarker=new Marker(mMapView);
        currentMarker.setPosition(currentLocation.getGeoPoint());
        //currentMarker.setIcon(getResources().getDrawable(R.drawable.marker_selected));
        currentMarker.setInfoWindow(null);
        greenMarkerCluster.add(currentMarker);
        points.add(currentLocation.getGeoPoint());
        for (MarkerModel GreenMarkerModel : greenModels) {
            Marker marker = new Marker(mMapView);
           // marker.setIcon(getResources().getDrawable(R.drawable.transparentcircle));

            marker.setPosition(GreenMarkerModel.getGeoPoint());
            marker.setInfoWindow(null);
            marker.setRelatedObject(GreenMarkerModel);
            greenMarkerCluster.add(marker);
            points.add(GreenMarkerModel.getGeoPoint());
        }
        for (MarkerModel RedMarkerModel : redModel) {
            Marker marker = new Marker(mMapView);
            //marker.setIcon(getResources().getDrawable(R.drawable.redcircle));

            marker.setPosition(RedMarkerModel.getGeoPoint());
            marker.setInfoWindow(null);
            marker.setRelatedObject(RedMarkerModel);

            redMarkerCluster.add(marker);
            points.add(RedMarkerModel.getGeoPoint());
        }
        mMapView.setBounds(points);
        ArrayList<MarkerClusterer> zoneCluster=new ArrayList<>();
        zoneCluster.add(greenMarkerCluster);
        zoneCluster.add(redMarkerCluster);
        mMapView.getOverlays().addAll(zoneCluster);
        mMapView.invalidate();

       /** for (MarkerModel markerModel : markerModels) {
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
        mMapView.setBounds(points);**/
    }


    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        BasicInfoWindow.closeAllInfoWindowsOn(mMapView);
        return super.singleTapConfirmedHelper(p);
    }


}
