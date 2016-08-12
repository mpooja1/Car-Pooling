/**
 * Copyright (c) 2016 Pooja,SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */
package com.example.sriharsha.carpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;

/**
 * This class is used to display ride confirmation message and confirm ride in database and send emails accordingly
 */
public class RideConfirmation extends KinveyActivity {
TextView textView;

    /**
     * This method is used to set layout for ride details screen
     * It books the selected ride from database , display it on screen and send email
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);
        final Button hreturn = (Button)findViewById(R.id.homeReturn);
        final Button logout = (Button)findViewById(R.id.logoutBtn);
        Bundle extras = getIntent().getExtras();
        final String pickupAddress = extras.getString("PickupLocation");
        String text = extras.getString("RideDetails");
        textView = (TextView)findViewById(R.id.text_RideDetails);
        TextView get_place = (TextView)findViewById(R.id.textView9);
        textView.setText(" ");
        String[] RideDetails = text.split("-");
        String carno =RideDetails[1].trim();
        String source =RideDetails[3].trim();
        String destination =RideDetails[5].trim();
        String time =RideDetails[7].trim();
        //SearchActivity sa = new SearchActivity();
        //final String pickupAddress = sa.address.toString();
                RideInfo ride = new RideInfo();

                Query myQuery = getClient().query();
                myQuery.equals("Source", source.toString());
                myQuery.equals("Destination", destination.toString());
                myQuery.equals("RideTime", time.toString());
                myQuery.equals("CarNumber", carno.toString());

                final AsyncAppData<RideInfo> myData = getClient().appData("RideInfo", RideInfo.class);
                myData.get(myQuery, new KinveyListCallback<RideInfo>() {

                    @Override
                    public void onSuccess(RideInfo[] rideInfos) {

                        if (rideInfos.length > 0) {
                            textView.setText("\nRIDE DETAILS\n\nDriver Details -" + rideInfos[0].getUserID() + "\nPHONE NO-" + rideInfos[0].getPhone_Num() +
                                    "\nCAR NO- " + rideInfos[0].getCar_Num() + "\nSource- " + rideInfos[0].getSource() + "\nDestination- "
                                    + rideInfos[0].getDestination() + "\nTime-" + rideInfos[0].getRideTime() + "\n\n\nPlease contact the driver for any ride queries.");
                            rideInfos[0].setRideStatus("Booked");

                            //RideInfo rideInfo = new RideInfo();
                        String    to = rideInfos[0].getUserID();
                         String to2 = getClient().user().toString();
                          String  rt = getClient().user().toString();
                           String body = " from "+rideInfos[0].getSource().toString()+" to "+rideInfos[0].getDestination().toString()+" at time "+rideInfos[0].getRideTime().toString() +"\n\n"+pickupAddress;

                            RideInfo ride = new RideInfo();
                            ride.setTo(to);
                            ride.setTo2(to2);
                            ride.setBody(body);
                            ride.setReplyTo(rt);
                            ride.setSubject("Ride Details");
                            ride.setSource(rideInfos[0].getSource().toString());
                            ride.setDestination(rideInfos[0].getDestination().toString());
                            ride.setRideTime(rideInfos[0].getRideTime().toString());
                            ride.setPhone_Num(rideInfos[0].getPhone_Num().toString());
                            ride.setUserID(getClient().user().toString());
                            ride.setCar_Num(rideInfos[0].getCar_Num().toString());
                            ride.setRideStatus("Booked");

                            AsyncAppData<RideInfo> myData2 = getClient().appData("RideInfo", RideInfo.class);
                            myData2.save(ride, new KinveyClientCallback<RideInfo>() {
                                @Override
                                public void onSuccess(RideInfo rideInfo) {
                                  //  Toast.makeText(RideConfirmation.this, "Ride Sucessfulllly Booked..", Toast.LENGTH_SHORT).show();
                                    Log.v("TAG", "Ride booked");
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.v("TAG", "Faailed to book");
                                }
                            });


                            AsyncAppData<RideInfo> myData1 = getClient().appData("RideInfo", RideInfo.class);
                            myData1.save(rideInfos[0], new KinveyClientCallback<RideInfo>() {

                                @Override
                                public void onSuccess(RideInfo rideInfo) {

                                    Toast.makeText(RideConfirmation.this, "Ride Sucessfully Booked..", Toast.LENGTH_SHORT).show();
                                    Log.v("TAG", "Ride booked");
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.v("TAG", "Filed to book");
                                }
                            });
                        } else {
                            textView.setText("Something missing");
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });


        hreturn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });


        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });

    }
}
