package com.coms309.a309front_end.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Map;

public class DataGetter {


    static public InternetInterface internet = new InternetInterface();

    static public StorageIO local = new StorageIO();


    /**
     * sends the given param data to the backend server or file system depending on if the app is online
     * @param applicationContext
     * @param params The data to be stored/sent
     * @param path  Path to store data on device or send to on the server
     * @param listener What to happen on the responce
     */
    public static void postMap(Context applicationContext, Map<String, String> params, String path, Response.Listener<JSONObject> listener){

        if(GlobalData.online){
            internet.postMap( params,  path, listener);

        } else {
            JSONObject data = local.postMap( params,  path);
            listener.onResponse(data);
        }

    }


    /**
     * Function to take a image and set it to the image returned from a get request
     * @param imageView image view to set
     * @param path path to go on the server to find the image
     * @author Nicholas
     */
    public static void setImageView(final ImageView imageView, final String path){

        if(GlobalData.online){
            internet.setImageView(imageView, path);

        } else {
            local.setImageView(  imageView, path);
        }

    }




    /**
     * A simple get request to retrive a json object from the server
     * @param path the path to go on the server
     */
    public static void getJSON( String path, Response.Listener<JSONObject> listener){
        if(GlobalData.online){
            internet.getJSON(path, listener);

        } else {
            JSONObject data = local.getJSON( path);
            listener.onResponse(data);
        }

    }
    /**
     * A simple get request to patch a json object to the server
     * @param path the path to go on the server
     */
    public static void patchMap(Context applicationContext, Map<String, String> params, String path, Response.Listener<JSONObject> listener){
        if(GlobalData.online){
            internet.patchMap( params,  path, listener);

        } else {
            JSONObject data = local.postMap( params,  path);
            listener.onResponse(data);
        }

    }


    /**
     * Function to take a textview and set it to the image returned from a get request
     * @param textVeiw textView to set
     * @param path path to go on the server to find the text
     * @author Nicholas
     */
    public static void setTextView(final TextView textVeiw, String path){

        if(GlobalData.online){
            internet.setTextView(textVeiw, path);

        } else {
            local.setTextView(  textVeiw, path);
        }

    }




    /**
     * Function that sends a get request to the server url + path and does the given responce
     * @param path The path on the server to the location
     * @author Nicholas
     */
    public static void get(String path, Response.Listener<String> listener){
        if(GlobalData.online){
            internet.get(path, listener);

        } else {
            listener.onResponse(local.getString(path));
        }


    }






    /**
     * post function that sends a post request to the server url + the given path with
     * the passed in responce listener.
     * @param params parameters to send to the server
     * @param path path to add to the servers ip/domain name
     * @author Nicholas (modifed from Rachel's code)
     */
    public static void postText(Map<String, String> params, String path, Response.Listener<String> listener) {

        if (GlobalData.online) {
            internet.postText(params, path, listener);

        } else {
            listener.onResponse(local.postText(params, path));
        }
    }













}
