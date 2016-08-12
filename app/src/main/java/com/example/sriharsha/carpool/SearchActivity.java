/**
 * Copyright (c) 2016 Pooja, SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */
package com.example.sriharsha.carpool;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;


/**
 * This class is used to search rides available to specific destination
 * It takes the current location details and allows user to book selected ride
 */
public class SearchActivity extends KinveyActivity {
    private TextView get_place;
    String address;
    int PLACE_PICKER_REQUEST =1;

    /**
     * This method is used to set layout for search screen to be displayed
     * It displays all the available ride to destination
     * It allows users to set pickup location
     * It allows user to book ride
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Button pickLocation = (Button)findViewById(R.id.btn_pick);
        final EditText Destination = (EditText) findViewById(R.id.edit_Dest);

        final Button SearchRide = (Button) findViewById(R.id.btn_Search);
        final Button BookRide = (Button) findViewById(R.id.bookBtn);
       // BookRide.setVisibility(BookRide.GONE);

        final Button cancel = (Button)findViewById(R.id.btn_SCancel);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });

        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMap();
            }


        });

        assert SearchRide != null;
        SearchRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                RideInfo ride = new RideInfo();
                Query myQuery = getClient().query();
                myQuery.equals("RideStatus","Available");
                myQuery.equals("Destination",Destination.getText().toString());
                AsyncAppData<RideInfo> myData = getClient().appData("RideInfo", RideInfo.class);
                myData.get(myQuery, new KinveyListCallback<RideInfo>() {
                    @Override
                    public void onSuccess(RideInfo[] rideInfos) {
                        Log.v("TAG", "received " + rideInfos.length + " events");
                        addRadioButton(rideInfos);
                        BookRide.setVisibility(BookRide.VISIBLE);
                      //  RadioGroup rg1 = new RadioGroup();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v("TAG","Failed");
                    }
                });
            }
        });

        BookRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayConfirmation();
            }
        });

    }


    /**
     * This method is used to display maps and show current pickup location on screen
     */
    private void displayMap(){
        get_place = (TextView)findViewById(R.id.textView9);
        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This method is used to set address of pickup location
     * @param address address of pick up location
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * This method is used to get address of pick up location
     * @return address
     */
    public String getAddress(){
        return address;
    }

    /**
     * This method is  used to get lpickup location from maps and set it to address
     * @param requestCode request code
     * @param resultCode result code
     * @param data data fetched
     */
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                 address = String.format("Pickup Location: %s", place.getAddress());
                get_place.setText(address);
                setAddress(address);

            }
        }
    }

    /**
     * This method is used to select ride that are available to specific destination in the form of radio buttons
     */
    private void displayConfirmation(){

        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.RadioButtonGroup);
        if(radioGroup.getChildCount()>0) {
            RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedtext = radioButton.getText().toString();
            Toast.makeText(SearchActivity.this, selectedtext, Toast.LENGTH_SHORT).show();

            //Toast.makeText(SearchActivity.this, text[5], Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, RideConfirmation.class);
            myIntent.putExtra("RideDetails",selectedtext);
            myIntent.putExtra("PickupLocation",address);
            startActivityForResult(myIntent, 0);
            finish();

        }
        else
        {
            Toast.makeText(SearchActivity.this, "No Rides available to book.. Please try again", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method is used to display all the rides available to specific destination
     * @param rideInfos all the rides that are available
     */
    private void addRadioButton(RideInfo[] rideInfos) {

        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.RadioButtonGroup);
        RadioGroup.LayoutParams rprms;
        radioGroup.removeAllViews();

        if(rideInfos.length>0)
        {
            for(int i=0;i<(rideInfos.length);i++){
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText("CAR NO- " + rideInfos[i].getCar_Num() + " -Source- " + rideInfos[i].getSource() + " -Destination- " + rideInfos[i].getDestination() + " -Time- " + rideInfos[i].getRideTime());
                radioButton.setId(i);
                rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                radioGroup.addView(radioButton, rprms);
            }
        }
        else
        {
            Toast.makeText(SearchActivity.this, "No Rides available to destination.. Please try again", Toast.LENGTH_SHORT).show();
        }
    }
 }
