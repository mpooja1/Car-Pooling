package com.example.pooja.carpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by pooja on 7/22/2016.
 */
public class SelectedDriverInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_driver_info);
        final TextView driverName = (TextView) findViewById(R.id.dName);
        final TextView driverGender = (TextView) findViewById(R.id.dGender);
        final TextView driverPhone = (TextView) findViewById(R.id.dNumber);
        final TextView carNo = (TextView) findViewById(R.id.cNumber);
        String passedDriverName = "Nikhil Marda";
        String passedDriverPhone = "+1 (503)-888-5555";
        String dName = " ";
        // String returned;
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            dName = extras.getString("sendName");
            Log.i("dName:",dName);
        }


        // Enable Local Datastore.

        //ParseObject UserProfile = new ParseObject("UserProfile");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfile");
        query.whereEqualTo("Name", dName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {

                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", "Retrieved the object.");
                    driverName.setText("Name:  " + object.getString("Name"));
                    driverPhone.setText("Contact No.:  " + object.getString("PhoneNumber"));
                    driverGender.setText("Gender  "+object.getString("Gender"));

                    //String temp = ""+object.getString("Driver");

                    if(object.getInt("Driver")==1) {
                        carNo.setText("License Plate #  " + object.getString("LicencePlate"));
                    }

                }
            }
        });

        //   String returned = object.getString("Name");
        //  driverName.setText(returned);
    }




}


