package com.coms309.a309front_end.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.InternetInterface;
import com.coms309.a309front_end.utils.imageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Post extends AppCompatActivity {


    /*
    /trips/posts/postID
    posts all have unique ID
     */

    //To create a new post send to
    // "{username}/trips/{tripid}/post"


    /*
    TO set the image
    for post, you can just update the post cause i didn't make a separate command so:
    @PatchMapping("{username}/posts")
    and you just have to put the post id, and the picture path in the body
    keys
     id : id
     picture : picture

     */


    String postID;
    String parent;
    Button save;

    TextView post, metaData, title;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        setPassedInValues();

        post = findViewById(R.id.post);
        metaData = findViewById(R.id.Meta_data);
        title = findViewById(R.id.Title);
        picture = findViewById(R.id.Pic);
        save = findViewById(R.id.save);


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPhoto();


            }
        });


        if(false && GlobalData.online && !postID.equals("-1")) {
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    set(response);
                }
            };


            DataGetter.getJSON("/trips/posts/" + postID , listener);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();
            }
        });



    }


    /**
     * This should send an update to the server to allow the author to edit the trip
     */
    private void saveEdit() {

        String path = "/" + GlobalData.username + "/posts";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("id", postID);

        params.put("title", title.getText().toString());

        params.put("body", post.getText().toString());


        try {
            params.put("picture", imageUtils.BitMapToString(((BitmapDrawable) picture.getDrawable()).getBitmap()));
        } catch (Exception e ){
            Toast.makeText(getApplicationContext(), "please select an image", Toast.LENGTH_LONG).show();
            return;
        }


//        id : id
//        picture : picture

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_LONG).show();
            }
        };



        //Change to the
        if(postID.equals("-1")){
            //{username}/trips/{tripid}/post
            path = "/" + GlobalData.username + "/trips/" + this.parent+ "/post";

            //TODO how to get latitude and longitude

//            params.put("latitude", HOW TO GET LAT);
//
//            params.put("longitude", );


        }



         DataGetter.postMap(getApplicationContext(), params, path, listener);






    }


    //TODO if it is the author I need to allow them to edit the post
    //TODO I need to figure out the image
    private void set(JSONObject data){

        /*
            This is in the same JSONARRAY format

            title
            body
            author
            date
            latitude
            longitude
            picture
            trip - full trip object
             */

        try {
            post.setText(data.getString("data"));
            String meta = "By " + data.getString("author") + "created on " + data.getString("data")
                    + " at " + data.getString("latitude") + ", " + data.getString("longitude");

            metaData.setText(meta);

            title.setText(data.getString("title"));

            Bitmap image = imageUtils.StringToBitMap(data.getString("picture"));

            picture.setImageBitmap(image);

            //TODO I need the ability to get the image asociated with the data

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    //this gets called in onCreate...but want it to happen onClick
    public void selectPhoto() {

        //may need to add in permissions to access user camera library
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try{
                picture.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage));
                ImageView imageView = findViewById(R.id.loadableImageView);
                imageView.setImageBitmap(GlobalData.lastImage);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }




    private void setPassedInValues() {


        Intent intent = getIntent();
        if(intent == null){
            return;
        }

        Bundle b = intent.getExtras();

        if(b.containsKey("postID")){
            postID = b.getString("postID");
        }

        if(b.containsKey("parent")){
            parent = b.getString("parent");
        }


    }



}