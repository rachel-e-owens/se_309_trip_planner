package com.coms309.a309front_end.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.coms309.a309front_end.Admin_Trip_Planner;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.InternetInterface;
import com.coms309.a309front_end.utils.StorageIO;
import com.coms309.a309front_end.utils.imageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that controls the profile page of the user
 *
 */
public class Profile_page extends AppCompatActivity {
    //TODO have download an image to dispaly
    //TODO have the image displayed be the downloaded one
    private static ImageView profilePic;
    private static TextView username;
    private static EditText Bio;
    private static TextView myNextTrip;
    private static TextView friendList;
    private static Button uploadPhoto, offline, removeSave;
    private Collator AppController;


    //change this functionality so it doesn't switch to a new page to upload a photo, just does it as a popup
    public void addPhoto() {

        //TODO get bitmap to string
        //post /user/{username}/uploadImage" - does not use key just straight string
        // /user/patch - need "userName" : userName, profilePic : encoded string

        //This should set the global data to a new bitmap
        Intent switchPage = new Intent(Profile_page.this, UploadPicture.class); //switch to upload picture page
        //TODO make path user/{username}/uploadImage"


        switchPage.putExtra("path", "/user");
        HashMap<String, String> params = new HashMap<>();

        params.put("userName", GlobalData.username);
        switchPage.putExtra("key", "profilePic");


        switchPage.putExtra("params", params);

        startActivity(switchPage);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        offline = (Button) findViewById(R.id.offline);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        username = (TextView) findViewById(R.id.User_name);
        Bio = (EditText) findViewById(R.id.Bio);
        myNextTrip = (TextView) findViewById(R.id.my_next_trip);
        friendList = (TextView) findViewById(R.id.previewTrip3);
        uploadPhoto = (Button) findViewById(R.id.upload_photo);
        removeSave = (Button) findViewById(R.id.RemoveSession);
        uploadPhoto = (Button) findViewById(R.id.upload_photo);



        username.setText(GlobalData.sessionKey);



        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Profile_page.this, Chat.class));

            }
        });


        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPhoto();


            }
        });



        removeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageIO tempIO = new StorageIO();

                tempIO.delete("/sessionID");
                tempIO.delete("/password");



            }
        });


        offline.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                StorageIO tempIO = new StorageIO();


                tempIO.save( "/sessionID", GlobalData.username);
                tempIO.save( "/password", GlobalData.sessionKey);
                String temp = tempIO.getString("/sessionID");

                username.setText(temp + "is stored");



            }
        });



///*
        //DataGetter.setImageView(profilePic, "/user/" + GlobalData.username + "/getProfilePic");
//        DataGetter.get("/user/" + GlobalData.username + "/getProfilePic", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("Image in string form Request Sucesses  for " , response.toString());
//
//                Bitmap b = imageUtils.StringToBitMap(response);
//
//
//                setProfilePic(b);
//
//
//            }
//        });
//        Uri i = Uri.parse(GlobalData.serverURL + "/user/" + GlobalData.username + "/getProfilePic");
//
//        profilePic.setImageURI(i);
//
//        Bitmap x = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();

        ///*
        DataGetter.setImageView(profilePic, "/user/" + GlobalData.username + "/getProfilePic");
        DataGetter.get("/user/" + GlobalData.username + "/getProfilePic", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Image in string form Request Sucesses  for " , response.toString());

                Bitmap b = imageUtils.StringToBitMap(response);
                profilePic.setImageBitmap(b);
                Bitmap x = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
                setProfilePic(b);


            }
        });







//
//        DataGetter.setTextView(username, "/username");
//        DataGetter.setTextView(Bio, "/Bio");
//        DataGetter.setTextView(myNextTrip, "/myNextTrip");
//        DataGetter.setTextView(friendList, "/Friend_list");
//*/
    }

    private void setProfilePic(final Bitmap b) {

                profilePic.setImageBitmap(b);

    }


    //where the User's information will be loaded

    /**
     * Getter function to return friend list based on current session key
     * @param sessionKey
     * @return
     */
    public Bitmap getProfilePic(String sessionKey, JSONObject obj) throws JSONException {

        String profilePicture = obj.getString("profilePic");
        Bitmap bitmap = imageUtils.StringToBitMap(profilePicture);

        //profilePic.setImageBitmap(bitmap); //set profile pic
        return bitmap;

    }



}
