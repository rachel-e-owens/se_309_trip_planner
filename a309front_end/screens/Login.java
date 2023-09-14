package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Login extends AppCompatActivity {

    EditText userName, password;
    private TextView bugDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check if the user is already logged in on the device and if they are go to profile page
        if (checkForStoredSession()){
            startActivity(new Intent(Login.this, Main_Page.class));
        }

        userName = (EditText) findViewById(R.id.userNameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        bugDetector = (TextView) findViewById(R.id.bugTextView);

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                startActivity(new Intent(Login.this, RegisterNewUser.class));
            }
        });



        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //login(userName.getText().toString(), password.getText().toString());
                   startActivity(new Intent(Login.this, Main_Page.class)); //testing for Rachel
                    //startActivity(new Intent(Login.this, Chat.class)); //testing for Rachel


                }




            });





    }



    //TODO make this a get request
    /**
     * Method to send a request to the server to see if the user exists and get a session key if they do
     * @author Nicholas
     */
    public void login(final String enteredUsername, String enteredPassword) {


        //create map to pass the params with
        //TODO the format of the params may have to be different bassed on backend
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userName", enteredUsername);
        params.put("password", enteredPassword);



        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (!response.getString("userName").equals("")) {

                        GlobalData.sessionKey = response.getString("password");
                        GlobalData.username = enteredUsername;
                        startActivity(new Intent(Login.this, Main_Page.class));

                    } else {

                        bugDetector.setText(response.toString());

                        Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };





        DataGetter.getJSON( "/users/" + userName.getText().toString() + "/" + password.getText().toString(), listener);




        if(GlobalData.debug) {
            GlobalData.username = "hay";
            GlobalData.sessionKey = "gerard";
            startActivity(new Intent(Login.this, Main_Page.class));
        }

    }


    /**
     * Function checks if they have a saved session on the app
     * @return true if there is a stored session
     */
    private Boolean checkForStoredSession() {

        Boolean returnBool = false;


        StorageIO tempIO = new StorageIO();
        String id = tempIO.getString("/sessionID");
        String password = tempIO.getString("/password");



        if( id != null){
            GlobalData.username = id;
            GlobalData.sessionKey = password;
            returnBool = true;
        }


        return returnBool;

    }

    /**
     * Method to check if user exists
     * @param username
     * @param password
     * @return
     * @throws JSONException
     */
    public void userExists(String username, String password) throws JSONException {
        JSONObject user = veryfier.getUser(username, password);

        //setup user
    }



}

