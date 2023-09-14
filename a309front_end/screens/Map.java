package com.coms309.a309front_end.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.coms309.a309front_end.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;

import java.util.List;

/**
 * Map class that allows user to select a location and mark pins
 * @author Rachel Owens
 */
public class Map extends AppCompatActivity implements OnMapReadyCallback {
    

    String mapId;
    // map
    private GoogleMap mMap;
    Marker marker;
    PlaceAutocompleteFragment placeAutoComplete;

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Map.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /**
         * Ability to search places
         * code from https://www.simplifiedcoding.net/how-to-search-location-in-google-map-in-android/
         */
        placeAutoComplete = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //mMap.clear();
                //mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
            }

            @Override
            public void onError(Status status) {

            }


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

        });

    }

    //need for the implements OnMapReadyCallBack
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        mMap = googleMap;

        // set a marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(LatLng point) {
                marker = mMap.addMarker(new MarkerOptions().position(point));

                double lat = point.latitude;
                double lon = point.longitude;
                //Toast.makeText(getApplicationContext(), "Lat: " + lat + " & Long: " + lon, Toast.LENGTH_SHORT).show();
                //getLocationName(lat, lon);
                double[] points = new double[2];
                points[0] = lat;
                points[1] = lon;

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", points);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

        });

        // remove a marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.remove();
                return true;
            }
        });

    }


    public JSONArray mapPins() {
        return null;
    }
}