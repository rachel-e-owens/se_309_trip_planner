package com.coms309.a309front_end.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coms309.a309front_end.Admin_Trip_Planner;
import com.coms309.a309front_end.R;

import java.util.Random;

public class Main_Page extends AppCompatActivity {
    //TODO have download an image to dispaly
    //TODO have the image displayed be the downloaded one
    //TODO need to implement the recycle view

    friend_post_preview friend_post_previews[] =  new friend_post_preview[3];
    my_trip_preview my_trip_previews[] = new my_trip_preview[4];


    Button editProfile, openFriendList, createNewTripButton, VeiwPastTripHeader;
    TextView WelcomeBanner, stats;

    private static int tripID;

    public static int getTripID() {
        return tripID;
    }








    /*
    /users/username/password
    name
    admin - bool
    email
    phone
    sKey -nothing here really
    bio
    userName
    profilePic
    tripTable - an array of Json objects



     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


//        Create all of the friend previews
        for( int i =0; i < 3; i++){
            friend_post_previews[i] = new friend_post_preview();
        }

        friend_post_previews[0] = friend_post_previews[0].populate((ImageView) findViewById(R.id.tripImage1), (TextView) findViewById(R.id.tripText1), "something");
        friend_post_previews[1] = friend_post_previews[1].populate((ImageView) findViewById(R.id.tripImage2), (TextView) findViewById(R.id.tripText2), "something");
        friend_post_previews[2] = friend_post_previews[2].populate((ImageView) findViewById(R.id.tripImage3), (TextView) findViewById(R.id.tripText3), "something");


        //TODO is there a way to do this in a loop?
        //Create all of the trip Previews
        for( int i =0; i < 4; i++){
            my_trip_previews[i] = new my_trip_preview();
        }

        my_trip_previews[0] = my_trip_previews[0] .populate((Button) findViewById(R.id.veiwTrip1), (TextView) findViewById(R.id.previewTrip1), "something");
        my_trip_previews[1] = my_trip_previews[1].populate((Button) findViewById(R.id.veiwTrip2), (TextView) findViewById(R.id.previewTrip2), "something");
        my_trip_previews[2] = my_trip_previews[2].populate((Button) findViewById(R.id.veiwTrip3), (TextView) findViewById(R.id.previewTrip3), "something");
        my_trip_previews[3] = my_trip_previews[3].populate((Button) findViewById(R.id.veiwTrip4), (TextView) findViewById(R.id.previewTrip4), "something");



        editProfile = (Button) findViewById(R.id.editProfile);
        openFriendList = (Button) findViewById(R.id.openFreindList);
        createNewTripButton = (Button) findViewById(R.id.createNewTripButton);
        VeiwPastTripHeader = (Button) findViewById(R.id.VeiwPastTripHeader);


         WelcomeBanner = (TextView) findViewById(R.id.WelcomeBanner);
         stats = (TextView) findViewById(R.id.stats);


//        InternetInterface.setTextView(WelcomeBanner, "/welcomeBanner");
//        InternetInterface.setTextView(stats, "/user/" + GlobalData.username + "/stats");
//




        //TODO button listener for
//        R.id.editProfile;
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Page.this, Profile_page.class));
                Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();

            }
        });

        //TODO button listener for
//        R.id.createNewTripButton;
        createNewTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Main_Page.this, NewTrip.class));
                startActivity(new Intent(Main_Page.this, Admin_Trip_Planner.class));
                Random rand = new Random(System.currentTimeMillis());
                tripID = rand.nextInt();
            }
        });


        //TODO button listener for
//        R.id.openFreindList;
        openFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Page.this, FriendList.class));
                Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();

            }
        });


        //TODO button listener for
//        R.id.VeiwPastTripHeader;
        VeiwPastTripHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Page.this, All_trips.class));

            }
        });









    }


    private class friend_post_preview{
        //array for most recent friend posts
        public ImageView uploadPhoto;
        public TextView previewTrip;


        //TODO need to change this into just friends preview
        //TODO change this into ryycle view content
        protected friend_post_preview populate(ImageView inputUploadPhotoe, TextView inputPreviewTrip, String name){
            uploadPhoto = inputUploadPhotoe;
            previewTrip = inputPreviewTrip;

//            InternetInterface.setImageView(uploadPhoto, "/posts/" + name + "/previewImage");
//            InternetInterface.setTextView(previewTrip, "/posts/" + name+ "/previewText");

            return this;
        }


    }




    //TODO change this into ryycle view content
    private class my_trip_preview {
        //data for most recent/current trips
        public Button trip;
        public TextView tripText;


        public my_trip_preview populate(Button tripButton, TextView inputTripText, String name){
            trip = tripButton;
            tripText = inputTripText;

//            InternetInterface.setTextView(trip, "/user/Trips/" + name + "/title");
//            InternetInterface.setTextView(tripText, "/user/Trips/" + name+ "/previewText");
            //TODO create listener for the button that takes you to the trip page

            return this;
        }




    }

}


