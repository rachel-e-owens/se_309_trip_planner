package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.CardviewAdaptor;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.InternetInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A list of all of the trips you are currently part of
 * that lets you open up a screen for each of them.
 */
public class All_trips extends AppCompatActivity {


    RecyclerView trips;
    com.coms309.a309front_end.utils.CardviewAdaptor CardviewAdaptor;
    ArrayList<String> tripName, tripDiscription, images;
    ArrayList<View.OnClickListener> liseners;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trips);

        trips = findViewById(R.id.trips);

        tripName = new ArrayList<>();
        tripDiscription = new ArrayList<>();
        images = new ArrayList<>();
        liseners = new ArrayList<>();





        if(!GlobalData.debug && GlobalData.online) {
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        setTrips(response.getJSONArray("tripTable"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error in getting trip table, defaulting built in trips", Toast.LENGTH_LONG).show();
                        setTrips(defultTrips());
                        e.printStackTrace();
                    }
                }
            };



            DataGetter.getJSON("/users/" + GlobalData.username + "/" + GlobalData.sessionKey, listener);
        } else{
            setTrips(defultTrips());
        }

    }


    /**
     * methiod to create an JSONArray of the default trips in case there is a problem
     * with getting them from the server
     * @return JSONArray of default trips
     */
    private JSONArray defultTrips() {
        Toast.makeText(getApplicationContext(), "using default Trips", Toast.LENGTH_LONG).show();

        JSONObject  addObj = new JSONObject();
        JSONArray returnObj = new JSONArray();


        try {


            for(int i =0; i < 10; i++){
                addObj = new JSONObject();
                addObj.put( "name", "Example name " + String.valueOf(i));
                addObj.put( "description", "Example description " + String.valueOf(i));
                addObj.put( "image", "Example image " + String.valueOf(i));
                addObj.put("id" , "Example postID " + String.valueOf(i));
                returnObj.put(addObj);

            }





        } catch (JSONException e){
            e.printStackTrace();
        }




        return returnObj;

    }



    public void setTrips(JSONArray data){


        if(data == null){
            return;
        }

        //Take the JSON object that was returned and populate the card view
        try{




            for(int i = 0; i < data.length(); i++){

                JSONObject next = data.getJSONObject(i);


                tripName.add(next.getString("tripname"));
                if(next.getString(  "description").length() > 20) {
                    tripDiscription.add(next.getString("description").substring(0, 20) + "...");
                } else{
                    tripDiscription.add(next.getString("description"));
                }
                //images.add(next.getString(String.valueOf(i) + "picture"));

                //TODO
                //tripLead
                //latitude
                //longitude




                final String tripID =  next.getString( "id" );


                liseners.add(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "attempting to open a trip page", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent( All_trips.this, CurrentTrip.class);
                                intent.putExtra("id", tripID);

                                startActivity(intent);




                            }
                        });

            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        trips.setLayoutManager(new LinearLayoutManager(this));

        CardviewAdaptor = new CardviewAdaptor(this, tripName, tripDiscription, images, liseners);
        trips.setAdapter(CardviewAdaptor);


    }





}