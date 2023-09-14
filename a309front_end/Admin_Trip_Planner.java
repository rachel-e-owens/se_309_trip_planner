package com.coms309.a309front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.android.volley.Response;
import com.coms309.a309front_end.screens.FriendList;
import com.coms309.a309front_end.screens.FriendList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coms309.a309front_end.screens.Login;
import com.coms309.a309front_end.screens.Main_Page;
import com.coms309.a309front_end.screens.RegisterNewUser;
import com.coms309.a309front_end.utils.DataGetter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import com.coms309.a309front_end.screens.Profile_page; // FIX ME - SWITCH TO MAIN PAGE

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

import static com.coms309.a309front_end.utils.imageUtils.BitMapToString;

/**
 * Trip Planner for Admin users:
 * Admins have extra ability to add and delete friends from trips
 * @author  Rachel owens
 */
public class Admin_Trip_Planner extends AppCompatActivity implements OnMapReadyCallback {

    // map
    private GoogleMap mMap;
    private FrameLayout mapCard;
    private EditText date;
    private TextView location;
    private Button addFriend, setLocation;
    private ImageView banner;
    private String locationTitle;
    private int tripID = Main_Page.getTripID();
    public static final int GET_DATES_RESULT = 2;
    public static final int GET_MAPS_RESULT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__trip__planner);

        location = findViewById(R.id.locationText);
        mapCard = findViewById(R.id.map_container);
        addFriend = findViewById(R.id.addFriendButton);
        banner = findViewById(R.id.bannerView);
        date = findViewById(R.id.editTextDate);
        setLocation = findViewById(R.id.setLocationButton);

        //addFriend
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Trip_Planner.this, FriendList.class));
            }
        });

        //addLocation - google maps api needed
        addMapFragment();
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Admin_Trip_Planner.this, com.coms309.a309front_end.screens.Map.class), GET_MAPS_RESULT);
            }
        });


        //updateBanner
        banner.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        updateBanner(findViewById(R.id.bannerView));
                    }
                }
        );

        //TODO: update itinerary - update date, time, flight no

        //set Date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Admin_Trip_Planner.this, DatePicker.class), GET_DATES_RESULT);


            }
        });

    }

    /**
     * Method to respond and handle to startActivityForResult methods
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.bannerView);
                imageView.setImageBitmap(bitmap);

                String imageToSend = BitMapToString(bitmap);
                sendImage(imageToSend);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == GET_DATES_RESULT) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                date.setText(result);
                sendDates(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == GET_MAPS_RESULT) {
            if(resultCode == Activity.RESULT_OK) {

                //get map pin
                //mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
                double[] result = data.getDoubleArrayExtra("result");
                Toast.makeText(getApplicationContext(), "Lat: " + result[0] + " Long: " + result[1], Toast.LENGTH_SHORT).show();
                try {
                    Geocoder geocoder = new Geocoder(Admin_Trip_Planner.this, Locale.getDefault());
                    List<Address> address = geocoder.getFromLocation(result[0], result[1], 1);
                    //Toast.makeText(getApplicationContext(), maddress.get(0).toString(), Toast.LENGTH_SHORT).show();
                    Address obj = address.get(0);
                    locationTitle = obj.getAddressLine(0);
                    locationTitle = obj.getAdminArea() + ", " + obj.getCountryName();

                    Log.i("Address Name", locationTitle);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendMapData(locationTitle, result);
                updateMap(result, location);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //no result
            }
        }

    }

    /**
     * Method to update the banner image of the trip
     * @param view
     */
    public void updateBanner(View view) {
        ImageView banner = findViewById(R.id.bannerView);
        final Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);
            }
        });

        //set of photo of tripID and send to server
    }

    /**
     * This method adds map card to the activity.
     */
    private void addMapFragment() {
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mMapFragment).commit();

        //don't need to send to server since this is default
    }

    /**
     * Method to update the map fragment for given lat and long and location name
     * @param points
     * @param locationName
     */
    private void updateMap(double[] points, TextView locationName) {

        // add a marker to the map fragment with the title of the location selected
        mMap.addMarker(new MarkerOptions().position(new LatLng(points[0],points[1])).title(locationTitle));
        //Toast.makeText(getApplicationContext(), locationTitle, Toast.LENGTH_SHORT).show();
        LatLng location = new LatLng(points[0], points[1]);

        //move the camera of the map fragment to the selected location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        // set the title of the map
        locationName.setText(locationTitle);
    }


    /**
     * TODO: Method to add friends to trip
     */
    public void addFriends() {


        //send friend list to server -> add tripID to each friend on list (?) - set status as Contributor
    }


    /**
     * Sets up map fragment
     * needed for the implementation of OnMapReadyCallBack
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng zero = new LatLng(0, 0);
        //mMap.addMarker(new MarkerOptions().position(zero).title("Starting Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zero));

        // set a marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(LatLng point) {
                mMap.addMarker(new MarkerOptions().position(point));

                double lat = point.latitude;
                double lon = point.longitude;
                //Toast.makeText(getApplicationContext(), "Lat: " + lat + " & Long: " + lon, Toast.LENGTH_SHORT).show();
                //getLocationName(lat, lon);
            }

        });

        // remove a marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
    }


    public boolean canSetFriends(String sessionKey, FriendList friends) throws JSONException {

        if (friends.getFriendsToAdd(sessionKey, friends.getFriendNames()).getBoolean("friendsReturned")) {
            return true;
        }

        return false;

    }

    /**
     * Method to send map data to server for an associated trip
     * @param locationName
     * @param points
     */
    public void sendMapData(String locationName, double[] points) {

        //send location name
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("location", locationName);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                startActivity(new Intent(Admin_Trip_Planner.this, Admin_Trip_Planner.class));
            }
        };
        DataGetter.postMap(getApplicationContext(), params, "/trips/" + tripID + "/addLocation", listener);


        //send latitude
        params = new HashMap<String, String>();
        params.put("latitude", String.valueOf(points[0]));
        DataGetter.postMap(getApplicationContext(), params, "/trips/" + tripID + "/addLatitude", listener);

        //send longitude
        params = new HashMap<String, String>();
        params.put("longitude", String.valueOf(points[1]));
        DataGetter.postMap(getApplicationContext(), params, "/trips/" + tripID + "/addLongitude", listener);


    }

    /**
     * Method to send dates data to server
     * @param dates
     */
    public void sendDates(String dates) {
        //Create parameter map
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("date", dates);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                startActivity(new Intent(Admin_Trip_Planner.this, Admin_Trip_Planner.class));
            }
        };
        DataGetter.postMap(getApplicationContext(), params, "/trips/" + tripID + "/addDate", listener);

    }

    /**
     * Method to send image data to server
     * @param image
     */
    public void sendImage(String image) {

        //Create parameter map
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("banner", image);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                startActivity(new Intent(Admin_Trip_Planner.this, Admin_Trip_Planner.class));
            }
        };
        DataGetter.postMap(getApplicationContext(), params, "/trips/" + tripID + "/addPic", listener);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
