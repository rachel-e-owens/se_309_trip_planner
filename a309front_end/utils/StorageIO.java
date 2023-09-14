package com.coms309.a309front_end.utils;


import java.io.*;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.JsonReader;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.*;
import static android.os.Environment.getStorageDirectory;  //getExternalStorageState;//     getExternalStorageDirectory;

/**
 * THe class that deals with all of the file IO for the program
 * It was designed in order to function simular to the internet interface
 * that way it is easy to change between them. This class should
 * probably not be accessed directly, but rather used through the DataGetter class
 */
//TODO attempt to make this class store a list of requests to make that
// way you can que up stuff while offline and then send it all when you get back online
public class StorageIO {


    private static String StoragePath = "/data/data/com.coms309.a309front_end/cache";


    /**
     * Methiod to retrieve a string from memory one of the major functions other functions in this class calss
     * @param path The path to the file you want
     * @return The string contained in the file or null if there is no string or an error occured.
     */
    public String getString(String path){

        String returnString = null;


        try {
            FileInputStream fin = new FileInputStream(StoragePath + path);
            InputStreamReader reader = new InputStreamReader(fin);

            int c;
            String temp = "";
            while( (c = reader.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            reader.close();
            returnString = temp;
        }
        catch(Exception e){

            e.printStackTrace();
            return null;
        };

        return returnString;
    }


    /**
     * Methiod to delete the file
     * @param path THe path to the file that you wish to delete
     * @return returns true if deleted, false if there was some problem in the delete call and null if it errors out
     */
    public Boolean delete(String path){

        Boolean returnBool = false;


        try {
            File file = new File("/data/data/com.coms309.a309front_end/cache" + path);
            returnBool = file.delete();

        }
        catch(Exception e){

            e.printStackTrace();
            return null;
        };

        return returnBool;
    }


    /**
     * Function to save data to a file. This is one of the main functions that the other functions in this class call
     * @param path The path within the system to use. It can not navigate up and it must use and existing directlory
     * @param data The string that you wish to store
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void save(String path, String data) {


        String text = data;
        FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream( StoragePath + path);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fos);

            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * A function to emulate the abbility to post a map of data. Allows you to store the posted value to text files
     * @param data The data to store
     * @param path  The place to store the text
     * @return A string containing any value that may be stored in a file of path + returned
     */
    public String postText(Map<String, String> data, String path) {

        Iterator<String> iter = data.keySet().iterator();

        JSONObject  obj =new JSONObject();

        while (iter.hasNext()){

            String key = iter.next();

            try {
                obj.put(key, data.get(key));
            }catch (JSONException e ){
                e.printStackTrace();
            }



        }

        save(path, obj);

        return getString(path+"returned");


    }



    /**
     *
     * @param path
     * @return
     * @author https://www.java2novice.com/java-file-io-operations/read-write-object-from-file/
     */
    private JSONObject getJson(String path){

        JSONObject returnJSON = null;
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream(path);
            objIs = new ObjectInputStream(fileIs);
            returnJSON = (JSONObject) objIs.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){

            }
        }
        return returnJSON;

    }


    /**
     *
     * @param path
     * @param data
     * @author https://www.java2novice.com/java-file-io-operations/read-write-object-from-file/
     */
    public void save(String path, JSONObject data){


        OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream(path);
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(data);
            objOps.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(objOps != null) objOps.close();
            } catch (Exception ex){

            }
        }


    }


    /**
     * This function adds the paramters to the end of the file path so the file name contains the parameters
     * and then calls getJSON to get the JSON file that exists there
     * @param params The map of paramters that needs to be added to get the file name
     * @param path the path to the file
     * @return JSON Object that lives at the location spesifid by the path and params
     */
    public JSONObject postMap( Map<String, String> params, String path) {

        String pathAndParams = path;


        Iterator<String> iter = params.keySet().iterator();

        while(iter.hasNext()){
            String key = iter.next();

            pathAndParams += "&" + key + "=" + params.get(key);

        }


        return getJSON(pathAndParams);
    }


    /**
     * Methiod to set a image view based on a string stored in a file
     * @param imageView
     * @param path
     */
    public void setImageView(ImageView imageView, String path) {

        String s = getString(path);

        imageView.setImageBitmap(imageUtils.StringToBitMap(s));

    }



    public JSONObject getJSON(String path) {
        return getJson(path);
    }



    public void setTextView(TextView textVeiw, String path) {

        String s = getString(path);

        textVeiw.setText(s);

    }






}
