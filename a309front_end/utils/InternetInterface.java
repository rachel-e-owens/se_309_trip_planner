package com.coms309.a309front_end.utils;

import android.content.*;
import android.graphics.Bitmap;
import android.util.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.*;




/**
 * This is a static public class that does all of the requests to the internet for the app
 */
public class InternetInterface extends AppCompatActivity { //made this extend AppCompatActivity so can use "this" in Singleton add to queue


    protected void onCreate() {




    }






    /**
     * Function to take a textview and set it to the image returned from a get request
     * @param textVeiw textView to set
     * @param path path to go on the server to find the text
     * @author Nicholas
     */
    public void setTextView(final TextView textVeiw, String path){

        if(  path == null){
            VolleyLog.d("attempted to navigate to a null path in setTextView");

            return;
        }
        if(  textVeiw == null){
            VolleyLog.d("attempted to navigate to a null textVeiw in setTextView");

            return;
        }




        final String url = GlobalData.serverURL + path;



        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("String Request win for "+ textVeiw.getId() + " from " + url, response.toString());
                textVeiw.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String Request falure for "+ textVeiw.getId() + " from " + url, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "temp tag");
        //Singleton.getInstance(this).addToRequestQueue(strReq);

    }





    //TODO modify to not tak a listener
    /**
     * A simple get request to retrive a json object from the server
     * @param path the path to go on the server
     * @return
     */
    public void getJSON(String path, Response.Listener<JSONObject> listener){
        if(  path == null){
            VolleyLog.d("attempted to navigate to a null path in getJSON");

            return;
        }

        final String url = GlobalData.serverURL + path;


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url,
                null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("JSON Request fail\t" + "Error: " + error.getMessage(), "Error: " + error.getMessage());
                VolleyLog.d(url);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonReq, "temp tag");
        //Singleton.getInstance(this).addToRequestQueue(strReq);


    }

    /**
     * A simple get request to retrive a json object from the server
     * @param path the path to go on the server
     * @return
     */
    public void patchJSON(String path, Response.Listener<JSONObject> listener){
        if(  path == null){
            VolleyLog.d("attempted to navigate to a null path in getJSON");

            return;
        }

        final String url = GlobalData.serverURL + path;


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.PATCH, url,
                null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("JSON Request fail\t" + "Error: " + error.getMessage(), "Error: " + error.getMessage());
                VolleyLog.d(url);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonReq, "temp tag");
        //Singleton.getInstance(this).addToRequestQueue(strReq);


    }




    /**
     * Function to take a image and set it to the image returned from a get request
     * @param imageView image view to set
     * @param path path to go on the server to find the image
     * @author Nicholas
     */
    public void setImageView(final ImageView imageView, final String path){
        if(  path == null){
            VolleyLog.d("attempted to navigate to a null path in setImageView");

            return;
        }


        final String url = GlobalData.serverURL + path;


        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Image in string form Request Sucesses for "+ imageView.getId() + " from " + url, response.toString());

                Bitmap b = imageUtils.StringToBitMap(response);
                imageView.setImageBitmap(b);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Image in string form Request falure for "+ imageView.getId() + " from " + url, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);

    }






    /**
     * Function that sends a get request to the server url + path and does the given responce
     * @param path The path on the server to the location
     * @author Nicholas
     */
    public void get(String path, Response.Listener<String> listener){
        if(  path == null){
            VolleyLog.d("attempted to navigate to a null path in get");

            return;
        }

        String url = GlobalData.serverURL + path;


        StringRequest strReq = new StringRequest(Request.Method.GET, url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String Request fail", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "temp tag");
        //Singleton.getInstance(this).addToRequestQueue(strReq);



    }





    /**
     * post function that sends a post request to the server url + the given path with
     * the passed in responce listener, It also takes a appContext feild to create error messages on screen
     * @param appContext context of the app to show error messages
     * @param params parameters to send to the server
     * @param path path to add to the servers ip/domain name
     * @author Nicholas (modifed from Rachel's code)
     */
//    @Override
    public String postText(final Context appContext, Map<String, String> params, String path, Response.Listener<String> listener){

        //create a clone of the params to pass to the post request
        final Map<String, String> passParams = new HashMap<String, String>(params);

        final String url = GlobalData.serverURL + path;

        final String returnString[] = new String[1];


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nicholas's standeror format
                        VolleyLog.d("Post String Request fail", "Error: " + error.getMessage());
                        VolleyLog.d(url);
//                        textObjext.setText("error" + error.getMessage());

                        //hold over from rachel's code
                        Toast.makeText(appContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return passParams;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, "temp tag");

        return returnString[0];

    }



    /**
     * post function that sends a post request to the server url + the given path with
     * the passed in responce listener.
     * @param params parameters to send to the server
     * @param path path to add to the servers ip/domain name
     * @author Nicholas (modifed from Rachel's code)
     */
    public String postText(Map<String, String> params, String path, Response.Listener<String> listener){

        //create a clone of the params to pass to the post request
        final Map<String, String> passParams = new HashMap<String, String>(params);

        String url = GlobalData.serverURL + path;

        final String returnString[] = new String[1];



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nicholas's standeror format
                        VolleyLog.d("Post String Request fail", "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return passParams;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, "temp tag");

        return returnString[0];

    }


    /**
     * A methiod to send a map of paramaters to the server
     * @param params The map of strings that contains the data to be sent to the server
     * @param path  the path on the server to send to
     * @param listener Listener<JSONObject> to exicute on the reponce
     */
    public void postMap( Map<String, String> params, String path, Response.Listener<JSONObject> listener){

        //create a clone of the params to pass to the post request
        final Map<String, String> passParams = new HashMap<String, String>(params);

        final String url = GlobalData.serverURL + path;




        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(passParams) , listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nicholas's standeror format
                        VolleyLog.d("Post JSON Request fail " + error.getMessage(), "Error: " + error.getMessage());
                        VolleyLog.d("To url location: " + url , "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return passParams;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonRequest, "temp tag");



    }


    /**
     * A methiod to send a map of paramaters to the server
     * @param params The map of strings that contains the data to be sent to the server
     * @param path  the path on the server to send to
     * @param listener Listener<JSONObject> to exicute on the reponce
     */
    public void patchMap( Map<String, String> params, String path, Response.Listener<JSONObject> listener){

        //create a clone of the params to pass to the post request
        final Map<String, String> passParams = new HashMap<String, String>(params);

        final String url = GlobalData.serverURL + path;




        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PATCH, url, new JSONObject(passParams) , listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nicholas's standeror format
                        VolleyLog.d("Post JSON patch fail " + error.getMessage(), "Error: " + error.getMessage());
                        VolleyLog.d("To url location: " + url , "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return passParams;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonRequest, "temp tag");



    }












}
