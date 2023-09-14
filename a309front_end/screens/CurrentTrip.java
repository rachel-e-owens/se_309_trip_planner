package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.CardviewAdaptor;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


/**
 * A class to let you look at what is going on in a trip.
 * See all of the posts, dates, ect
 */
public class CurrentTrip extends AppCompatActivity {

    String tripID;

    TextView title, discription, metaData;

    Button addPost;


    RecyclerView posts;
    CardviewAdaptor CardviewAdaptor;
    ArrayList<String> postTitles, discriptions, images;
    ArrayList<View.OnClickListener> liseners;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trip);

        setPassedInValues();

        posts = findViewById(R.id.posts);

        addPost = findViewById(R.id.addPost);


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            //TODO Need to make a new post class
            public void onClick(View v) {
                Intent intent = new Intent( CurrentTrip.this, Post.class);
                intent.putExtra("parent", tripID);
                intent.putExtra("postID", "-1");

                startActivity(intent);


            }
        });




        title = findViewById(R.id.Title);
        discription = findViewById(R.id.description);
        metaData = findViewById(R.id.metaData);



        postTitles = new ArrayList<>();
        discriptions = new ArrayList<>();
        images = new ArrayList<>();
        liseners = new ArrayList<>();


        //Methiod to get the data for the posts
        if(!GlobalData.debug && GlobalData.online) {
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        setPosts(response.getJSONArray("posts"));

                    } catch (JSONException e) {
                        setPosts(defultPosts());
                        e.printStackTrace();
                    }
                }
            };


            DataGetter.getJSON("/trips/" + tripID,  listener);
        } else{
            setPosts(defultPosts());
        }



//        //Code to get the meta Data
//        if(!GlobalData.debug && GlobalData.online) {
//            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    setMeta(response);
//
//                }
//            };
//
//
//            DataGetter.getJSON("/trips/" + tripID , listener);
//        } else{
//            //setPosts(defultPosts());
//        }






    }

    private void setPassedInValues() {


        Intent intent = getIntent();
        if(intent == null){
            return;
        }

        Bundle b = intent.getExtras();

        if(b.containsKey("id")){
            tripID = b.getString("id");
        }


    }

    private void setMeta(JSONObject response) {

        try {
            title.setText(response.getString("tripname"));
            discription.setText(response.getString("description"));

            String meta = "By " + response.getString("tripLead")
                    + " at " + response.getString("latitude") + ", " + response.getString("longitude");

            metaData.setText(meta);
        } catch (JSONException e) {
            e.printStackTrace();
        }







    }

    private JSONArray defultPosts() {
        Toast.makeText(getApplicationContext(), "error in getting posts, defaulting built in posts", Toast.LENGTH_LONG).show();


        JSONArray returnObj = new JSONArray();


        try {


            for(int i =0; i < 10; i++){
                JSONObject addObj = new JSONObject();
                addObj.put("title", "Example title for post" + String.valueOf(i));
                addObj.put("body", "Example description for post" + String.valueOf(i));
                addObj.put( "image", "Example image for post" + String.valueOf(i));
                addObj.put( "id", "Example postID for post" + String.valueOf(i));
                returnObj.put(addObj);
            }


        } catch (JSONException e){
            e.printStackTrace();
        }




        return returnObj;

    }




    public void setPosts(JSONArray data){

        if( data == null){
            return;
        }

        //Take the JSON object that was returned and populate the card view
        try{
            //TODO make iterator
        for( int i = 0; i < data.length(); i++){

            JSONObject next = data.getJSONObject(i);

            /*
            This is in the same JSONARRAY format
            id
            title
            body
            author
            date
            latitude
            longitude
             */

            postTitles.add(next.getString("title"));
            discriptions.add(next.getString("body"));
            images.add(next.getString( "picture"));


            final String postID = next.getString("id");
            liseners.add(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent( CurrentTrip.this, Post.class);
                            intent.putExtra("postID", postID);

                            startActivity(intent);


                        }
                    });

        }
        } catch (JSONException e){
            e.printStackTrace();
        }

        posts.setLayoutManager(new LinearLayoutManager(this));

        CardviewAdaptor = new CardviewAdaptor(this, postTitles, discriptions, images, liseners);
        posts.setAdapter(CardviewAdaptor);


    }

    /**
     * TODO: Set Map Pins for a trip
     * @param sessionKey
     * @param id
     */
    public void setMapPins(String sessionKey, Map id) {
        //send session key and Map ID to database to get Map pins


    }
}