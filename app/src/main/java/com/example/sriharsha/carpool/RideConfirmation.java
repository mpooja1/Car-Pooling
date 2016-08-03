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

public class RideConfirmation extends KinveyActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);
        final Button hreturn = (Button)findViewById(R.id.homeReturn);
        final Button logout = (Button)findViewById(R.id.logoutBtn);
        Bundle extras = getIntent().getExtras();
        String text = extras.getString("RideDetails");
        textView = (TextView)findViewById(R.id.text_RideDetails);
        textView.setText(" ");
        String[] RideDetails= text.split("-");
        String carno =RideDetails[1].trim();
        String source =RideDetails[3].trim();
        String destination =RideDetails[5].trim();
        String time =RideDetails[7].trim();
                RideInfo ride = new RideInfo();

                Query myQuery = getClient().query();
                myQuery.equals("Source", source.toString());
                myQuery.equals("Destination", destination.toString());
                myQuery.equals("RideTime", time.toString());
                myQuery.equals("CarNumber", carno.toString());

      /*  Toast.makeText(RideConfirmation.this,source, Toast.LENGTH_SHORT).show();
        Toast.makeText(RideConfirmation.this,destination, Toast.LENGTH_SHORT).show();
        Toast.makeText(RideConfirmation.this, time, Toast.LENGTH_SHORT).show();
        Toast.makeText(RideConfirmation.this,carno, Toast.LENGTH_SHORT).show();*/


                final AsyncAppData<RideInfo> myData = getClient().appData("RideInfo", RideInfo.class);
                myData.get(myQuery, new KinveyListCallback<RideInfo>() {

                    @Override
                    public void onSuccess(RideInfo[] rideInfos) {
                        //Toast.makeText(RideConfirmation.this, String.valueOf(rideInfos.length), Toast.LENGTH_SHORT).show();
                        if (rideInfos.length > 0) {
                            textView.setText("\nRIDE DETAILS\nDriver Details -" + rideInfos[0].getUserID() + "\nPHONE NO-" + rideInfos[0].getPhone_Num() +
                                    "\nCAR NO- " + rideInfos[0].getCar_Num() + "\nSource- " + rideInfos[0].getSource() + "\nDestination- "
                                    + rideInfos[0].getDestination() + "\nTime-" + rideInfos[0].getRideTime() + "\n\n\nPlease call driver if any problem..");
                            rideInfos[0].setRideStatus("Booked");
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
