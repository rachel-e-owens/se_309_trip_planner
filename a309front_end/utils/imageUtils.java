package com.coms309.a309front_end.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.sip.SipSession;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.coms309.a309front_end.R;

import java.io.ByteArrayOutputStream;

public class imageUtils {


    /* Outside resources
http://www.itsalif.info/content/android-volley-tutorial-http-get-post-put
https://www.simplifiedcoding.net/android-volley-tutorial/
https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa

 */

    /**
     * Function to take the string returned by the server and change it to a bitmap
     * @param encodedString The encoded bitmap in string form
     * @return Bitmap that was encoded in the string
     * @author sachin10 found by Nicholas at https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
     */
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }





    /**
     * Function to convert a bitmap to a string, meant to allow us to send bitmaps to the server
     * @param bitmap the bitmap to turn into the string
     * @return String of the encoded bitmap
     * @author sachin10 found by Nicholas at https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
     */
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public static View.OnClickListener getListner(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        };
    }








}
