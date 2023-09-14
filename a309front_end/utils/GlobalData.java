package com.coms309.a309front_end.utils;

import android.graphics.Bitmap;

import com.android.volley.toolbox.Volley;

public class GlobalData {
    //TODO make sure this is the password
    static public String sessionKey = "";
    /* http://10.0.2.2:8080 is the local host for a simulated app
    http://coms-309-rp-03.cs.iastate.edu:8080/users/

     */

    //static public String serverURL = "http://coms-309-rp-03.cs.iastate.edu:8080/users";
    //static public String serverURL = "http://10.0.2.2:8080/users"; //Our local host
    static public String serverURL = "http://10.24.226.19:8080"; //our servers ip

    static public boolean debug = false;
    static public  String username = "";

    static public boolean online = true;

    static public Bitmap lastImage;

    //static public IO getter;

}
