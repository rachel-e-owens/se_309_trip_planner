package com.coms309.a309front_end.screens;

import com.android.volley.Response;
import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.DataGetter;
import com.coms309.a309front_end.utils.GlobalData;
import com.coms309.a309front_end.utils.InternetInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class NewTrip extends AppCompatActivity {

    EditText name, description, person, location, date;
    Button addPerson, addLocation, Submit, addPhoto;
    TextView listOfPeople, listOfLocations;


    HashMap<String, String> peopleMap, locationMap, dateMap;

    int numberOfPeople, numberOfLocations;

    String photo = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);



        name = findViewById(R.id.TripName);
        description = findViewById(R.id.TripDiscription);
        person= findViewById(R.id.PersonToAdd);
        location= findViewById(R.id.LocationToAdd);
        date= findViewById(R.id.DateToAdd);
        addPerson= findViewById(R.id.AddPerson);
        addLocation = findViewById(R.id.AddLocation);
        Submit= findViewById(R.id.submit);
        listOfPeople= findViewById(R.id.ListOfPeople);
        listOfLocations= findViewById(R.id.ListOfLocations);
        addPhoto = findViewById(R.id.addPhoto);

        peopleMap = new HashMap<>();
        locationMap = new HashMap<>();
        dateMap = new HashMap<>();

        numberOfPeople = 0;
        numberOfLocations = 0;


        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson();
            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLocation();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPhoto();
            }
        });



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });







    }


    private void addPerson(){


        peopleMap.put(String.valueOf(numberOfPeople), person.getText().toString().trim());

        numberOfPeople++;



        String whoIsInTheTrip = "You have invited\n";

        for(int i =0; i < numberOfPeople; i++){
            whoIsInTheTrip += peopleMap.get(String.valueOf(i)) + ", ";
        }

        listOfPeople.setText(whoIsInTheTrip);

    }


    private void AddLocation(){

        locationMap.put(String.valueOf(numberOfLocations), location.getText().toString().trim());
        dateMap.put(String.valueOf(numberOfLocations), date.getText().toString().trim());

        numberOfLocations++;



        String locationsAndDates = "You are going to\n";

        for(int i =0; i < numberOfLocations; i++){
            locationsAndDates += locationMap.get(String.valueOf(i)) + " on " + dateMap.get(String.valueOf(i)) + ", \n";
        }

        listOfLocations.setText(locationsAndDates);



    }

    private void AddPhoto(){

        Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();

    }


    private void submit(){


        HashMap<String, String> params = new HashMap<>();


        params.put("leader", GlobalData.username);

        params.put("photo", this.photo);



        params.put("name", name.getText().toString().trim());

        params.put("description", description.getText().toString().trim());


        params.put("numberOfPeople", String.valueOf(numberOfPeople));

        for(int i = 0; i < numberOfPeople; i++){
            params.put("person" + String.valueOf(i), peopleMap.get(String.valueOf(i)));
        }


        params.put("numberOfLocations", String.valueOf(numberOfLocations));

        for(int i = 0; i < numberOfLocations; i++){
            params.put("location" + String.valueOf(i), locationMap.get(String.valueOf(i)));
            params.put("date" + String.valueOf(i), dateMap.get(String.valueOf(i)));
        }



        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Trip added", Toast.LENGTH_LONG).show();
            }

        };


        DataGetter.postMap(getApplicationContext(), params, "/user/" + GlobalData.username +"/addTrip/", listener);


    }





}