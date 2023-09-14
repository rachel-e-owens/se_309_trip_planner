package com.coms309.a309front_end.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.imageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class UploadPicture extends AppCompatActivity {



    String path;

    String key;

    Button send;

    Map params;




    public void uploadPhoto(View view) {
        selectPhoto();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_picture);


        addPassedInVals();





        findViewById(R.id.loadpic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });


        send = findViewById(R.id.Send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendImage();

            }
        });
        //call upload function immediately
        //uploadPhoto();


        //selectPhoto();

    }

    private void addPassedInVals() {
        Intent intent = getIntent();

        if(intent != null){
            Bundle b = intent.getExtras();

            Set<String> keys = b.keySet();

            if(b.containsKey("path")){
                path = b.getString("path");
            }

            if(b.containsKey("key")){
                key = b.getString("key");
            }

            if(b.containsKey("params")){
                params = (Map) b.get("params");
            }
        }

    }


    //TODO need to move the sending of data to other classes
//
    private void sendImage() {

        if(GlobalData.lastImage == null){
            Toast.makeText(getApplicationContext(), "please select an image", Toast.LENGTH_LONG).show();
            return;
        }

        String encodedImage = imageUtils.BitMapToString(GlobalData.lastImage);

        if(params == null) {
            HashMap<String, String> params = new HashMap<>();
        }
        if(key == null){
            key = "default_picture_sending_key";
        }
        if(path == null){
            path = GlobalData.serverURL;
        }

        params.put(key, encodedImage);


        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_LONG).show();
            }
        };


        DataGetter.patchMap(getApplicationContext(), params, this.path, listener);

    }



    //this gets called in onCreate...but want it to happen onClick
    public void selectPhoto() {

        //may need to add in permissions to access user camera library
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            return;
        }

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try{
                GlobalData.lastImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.loadableImageView);
                imageView.setImageBitmap(GlobalData.lastImage);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }








}
