package com.example.android.myapplication.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;


import com.example.android.myapplication.R;
import com.example.android.myapplication.model.MarkerModel;
import com.mmi.layers.Marker;
import com.mmi.layers.MarkerClusterer;
import com.mmi.util.GeoPoint;

import java.util.ArrayList;


public class MarkersClusteringFragment extends MapBaseFragment {


    @Override
    public String getSampleTitle() {
        return "Maps Marker";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<MarkerModel> markerModels = new ArrayList<>();
        markerModels.add(new MarkerModel(new GeoPoint(28.549356, 77.26780099999999)));



        addOverLays(markerModels);
    }

    void addOverLays(ArrayList<MarkerModel> markerModels) {

        MarkerClusterer markerClusterer = new MarkerClusterer(getActivity());

        markerClusterer.setColor(getResources().getColor(R.color.green_color));
        markerClusterer.mAnchorV = Marker.ANCHOR_CENTER;

        markerClusterer.mTextAnchorV = Marker.ANCHOR_CENTER;

        markerClusterer.setTextSize(12);
        ArrayList<GeoPoint> points = new ArrayList<>();

        for (MarkerModel markerModel : markerModels) {
            Marker marker = new Marker(mMapView);
            marker.setIcon(getResources().getDrawable(R.drawable.marker_default_1));
            marker.setPosition(markerModel.getGeoPoint());
            marker.setInfoWindow(null);
            marker.setRelatedObject(markerModel);
            markerClusterer.add(marker);
            points.add(markerModel.getGeoPoint());
        }
        mMapView.setBounds(points);
        mMapView.getOverlays().add(markerClusterer);
        mMapView.invalidate();

    }


    Bitmap generateMarker() {
        ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
        biggerCircle.setIntrinsicHeight(50);
        biggerCircle.setIntrinsicWidth(50);
        biggerCircle.setBounds(new Rect(0, 0, 50, 50));
        biggerCircle.getPaint().setColor(Color.parseColor("#799C5A"));
        biggerCircle.setAlpha(200);

        ShapeDrawable smallerCircle = new ShapeDrawable(new OvalShape());
        smallerCircle.setIntrinsicHeight(10);
        smallerCircle.setIntrinsicWidth(10);
        smallerCircle.setBounds(new Rect(0, 0, 10, 10));
        smallerCircle.getPaint().setColor(Color.parseColor("#799C5A"));
        smallerCircle.setPadding(5, 5, 5, 5);
        smallerCircle.setAlpha(95);
        Drawable[] d = {smallerCircle, biggerCircle};

        return getSingleDrawable(new LayerDrawable(d)).getBitmap();
    }

    public BitmapDrawable getSingleDrawable(LayerDrawable layerDrawable) {

        int resourceBitmapHeight = 15, resourceBitmapWidth = 15;

        float widthInInches = 0.3f;

        int widthInPixels = (int) (widthInInches * getResources().getDisplayMetrics().densityDpi);
        int heightInPixels = (int) (widthInPixels * resourceBitmapHeight / resourceBitmapWidth);

        int insetLeft = 10, insetTop = 10, insetRight = 10, insetBottom = 10;

        layerDrawable.setLayerInset(1, insetLeft, insetTop, insetRight, insetBottom);

        Bitmap bitmap = Bitmap.createBitmap(widthInPixels, heightInPixels, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        layerDrawable.setBounds(0, 0, widthInPixels, heightInPixels);
        layerDrawable.draw(canvas);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setBounds(0, 0, widthInPixels, heightInPixels);

        return bitmapDrawable;
    }
}
