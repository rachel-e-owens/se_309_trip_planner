package com.coms309.a309front_end.utils;

import com.android.volley.Response;

import java.util.HashMap;






public class registerUser {


    public veryfier v;

    public registerUser(){
        v = new veryfier();
    }


    //Code Tutorial found at https://www.simplifiedcoding.net/android-volley-tutorial/
    public String registerUser(String username, String password, String name, String email, String phoneNumber) {

        String error = "0";


        //make sure entries aren't left blank
        if (username.length() < 3) {
            error = addError("The username must be more then 3 characters", error);
        }
        if (v.passwordVerifiery(password)) {
            error = addError("The password must be stronger", error);
        }


        if (v.emailVerifiery(email)) {
            error = addError("Please add your real name", error);
        }

        if (v.nameVerifiery(name)) {
            error = addError("Please add a real email", error);
        }

        if (v.phoneVerifiery(phoneNumber)) {
            error = addError("we need a real phone Number", error);
        }


        //Create parameter map
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", username);
        params.put("password", password);
        params.put("name", name);
        params.put("email", email);
        params.put("phoneNumber", phoneNumber);

        Response.Listener<String> listener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

            }
        };

        DataGetter.postText(params, "/user/add", listener);



        if (error.equals("0")) {
            return "0";
        }

        return error;
    }

    private String addError(String s, String error) {

        return String.valueOf(Integer.parseInt(error.substring(0,0)) +1 ) + error.substring(1) + ", " + s;

    }


}


