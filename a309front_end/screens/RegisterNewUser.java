package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.veryfier;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.InternetInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RegisterNewUser extends AppCompatActivity {
    EditText registerName, registerUsername, registerEmail, registerPhone, registerPassword;
    String name, username, email, phone, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        registerName = (EditText) findViewById(R.id.editTextName);
        registerUsername = (EditText) findViewById(R.id.editTextUsername);
        registerEmail = (EditText) findViewById(R.id.editTextEmail);
        registerPhone = (EditText) findViewById(R.id.editTextPhone); //this may need to be changed to only accept ints?
        registerPassword = (EditText) findViewById(R.id.editTextPassword);


        findViewById(R.id.sendRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send Registration to the server
                try {
                    registerUser(registerName, registerUsername, registerEmail, registerPhone, registerPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    //Code Tutorial found at https://www.simplifiedcoding.net/android-volley-tutorial/
    public void registerUser(EditText enteredName, EditText enteredUsername, EditText enteredEmail, EditText enteredPhone, EditText enteredPassword) throws JSONException {
        username       = enteredUsername.getText().toString().trim();
        password       = enteredPassword.getText().toString().trim();
        name           = enteredName.getText().toString().trim();
        email          = enteredEmail.getText().toString().trim();
        phone    = enteredPhone.getText().toString().trim();


        //make sure entries aren't left blank
        if (TextUtils.isEmpty(name)) {
            registerName.setError("Enter your name");
            registerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            registerUsername.setError("Please enter username");
            registerUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            registerEmail.setError("Enter a password");
            registerEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            registerPassword.setError("Enter a password");
            registerPassword.requestFocus();
            return;
        }

        if (!veryfier.validateEmail(email)) {
            registerEmail.setError("Please enter a valid email address");
            registerEmail.requestFocus();
            return;
        }

        if (!veryfier.validatePhone(phone)) {
            registerPhone.setError("Please enter a valid phone number");
            registerPhone.requestFocus();
            return;
        }

//        //Rachel - if username taken
//        if (userNameTaken(username).getBoolean("usernameTaken")) {
//
//            Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_LONG).show();
//            return;
//
//        }

        //Create parameter map
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", username);
        params.put("password", password);
//        params.put("name", name);
//        params.put("email", email);
//        params.put("phoneNumber", phone);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Please login with new account", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterNewUser.this, Login.class));
            }
        };



        DataGetter.postMap(getApplicationContext(), params, "/user", listener);


    }

    /**
     * Function to determine if username has already been taken
     * @param username
     * @return
     */
    public JSONObject userNameTaken(String username) {
        return null;
    }


}