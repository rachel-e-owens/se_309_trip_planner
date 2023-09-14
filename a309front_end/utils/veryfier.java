package com.coms309.a309front_end.utils;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to implement verification for user inputs
 */
public class veryfier {


    //validate the email address from https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.find();
    }

    //validate phone number from https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    public static final Pattern PHONE_REGEX = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
    public static boolean validatePhone(String phoneNumber) {
        Matcher matcher = PHONE_REGEX.matcher(phoneNumber);
        return matcher.find();
    }


    public boolean passwordVerifiery(String password) {
        return true;
    }


    public boolean emailVerifiery(String email) {
        return true;
    }


    public boolean phoneVerifiery(String phoneNumber) {
        return true;
    }


    public boolean nameVerifiery(String name) {
        return true;
    }


    public boolean usernameVerifiery(String username) {
        return true;
    }

    /**
     * Getter function to return a user if they exist
     * @param username
     * @param password
     * @return
     */
    public static JSONObject getUser(String username, String password) {
        return null;
    }



}
