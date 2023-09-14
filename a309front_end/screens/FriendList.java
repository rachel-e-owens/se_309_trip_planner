package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.CardviewAdaptor;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FriendList extends AppCompatActivity {
    RecyclerView friends;
    Button addFriendButton;
    EditText addFriendName;

    CardviewAdaptor CardviewAdaptor;
    ArrayList<String> names, bio, images;
    ArrayList<View.OnClickListener> listeners;

    public ArrayList<String> getFriendNames() {
        return names;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friends = findViewById(R.id.friendList);
        addFriendButton = findViewById(R.id.addButton);
        addFriendName = findViewById(R.id.addName);



        if(GlobalData.debug ) {

            setFriends(defaultFriendList());
            addFriendSetup();

        } else {
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        setFriends(response.getJSONArray("friend"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    addFriendSetup();
                }
            };

            DataGetter.getJSON("/user/" + GlobalData.username + "/friends", listener);




        }




    }

    private void addFriendSetup() {

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameToAdd = addFriendName.getText().toString().trim();

                HashMap<String, String> map = new HashMap<>();

                map.put("userName", usernameToAdd);


                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("success").equals("true") ){
                                Toast.makeText(getApplicationContext(), "request sent", Toast.LENGTH_LONG).show();
                            } else{
                                Toast.makeText(getApplicationContext(), "no user with that name", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                DataGetter.postMap(getApplicationContext(), map, "/user/" + GlobalData.username + "/addFriend", listener);




            }




        });




    }

    /**
     * function to set all of the friends profile pics, name, and bios
     * @param data
     */
    public void setFriends(final JSONArray data){ //make sure this adds new friends and doesn't overwrite current friends


        if(data == null){
            return;
        }

        names = new ArrayList<>();
        bio = new ArrayList<>();
        images = new ArrayList<>();
        listeners = new ArrayList<>();






        for(int i = 0; i <data.length(); i++) {

            final JSONObject next;
            try {
                next = (JSONObject) data.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;

            }



            try {

                final String username;


                if (next.has("username") && next.getString( "username") != null){
                    username = next.getString("username");
                    if(names.contains(username)){
                        continue;
                    }
                    names.add(next.getString( "username"));
                } else{
                    username = "default_username";
                }


                if (next.has("bio") && next.getString( "bio") != null){
                    bio.add(next.getString( "bio"));
                } else {
                    bio.add("Default_bio");
                }

                if (next.has("picture") && next.getString( "picture") != null){
                    images.add(next.getString( "picture"));
                } else {
                    images.add("");
                }


                listeners.add(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Toast.makeText(getApplicationContext(), "need to add the ability to view other persons profile page", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(FriendList.this, Chat.class);
                                intent.putExtra("DM", username);

                                startActivity(intent);

                            }
                        });


            } catch (JSONException ex) {
                ex.printStackTrace();
            }



        }



        friends.setLayoutManager(new LinearLayoutManager(this));


        CardviewAdaptor = new CardviewAdaptor(this, names, bio, images, listeners);
        friends.setAdapter(CardviewAdaptor);




    }

    /**
     * Getter function to return friend list based on current session key
     * @param sessionKey
     * @return
     */
    public JSONObject getFriendsToAdd(String sessionKey, ArrayList<String> friendNames) {
        return null;
    }



    private JSONArray defaultFriendList(){

        JSONArray returnArray = new JSONArray();




        try {


            for(int i =0; i < 10; i++){
                JSONObject  returnObject = new JSONObject();
                returnObject.put("bio", "Example bio " + String.valueOf(i));
                returnObject.put("picture", "Example image " + String.valueOf(i));
                returnObject.put("username", "Example name " + String.valueOf(i));
                returnArray.put(returnObject);

            }


        } catch (JSONException e){
            e.printStackTrace();
        }




        return returnArray;

    }






}