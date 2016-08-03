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
import com.kinvey.android.Client;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

public class OfferActivity extends KinveyActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        getClient().user().isUserLoggedIn();

        user = getClient().user().getUsername();

        final TextView sourceText = (TextView)findViewById(R.id.editOfr_Source);
        final TextView destinationText = (TextView)findViewById(R.id.editOfr_Dest);
        final TextView timeText = (TextView)findViewById(R.id.editOfr_Time);
        final TextView phText = (TextView)findViewById(R.id.edit_Phone);
        final TextView CarNo = (TextView)findViewById(R.id.editText_CarNo);
        final Button offerRide = (Button)findViewById(R.id.btn_ofr);
        final Button cancel = (Button)findViewById(R.id.btn_Cancel);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });


        offerRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                RideInfo rideInfo = new RideInfo();

              /*Client kinveyClient = new Client.Builder("kid_SyYWFh5d",//APP_ID
                      "0b1538e57dfb46da87b5da42516501ff",//APP_SECRET
                      getApplicationContext()).build();*/
                RideInfo ride = new RideInfo();
                ride.setSource(sourceText.getText().toString());
                ride.setDestination(destinationText.getText().toString());
                ride.setRideTime(timeText.getText().toString());
                ride.setPhone_Num(phText.getText().toString());
                ride.setUserID(user);
                ride.setCar_Num(CarNo.getText().toString());
                ride.setRideStatus("Available");
                AsyncAppData<RideInfo> myData = getClient().appData("RideInfo", RideInfo.class);
                myData.save(ride, new KinveyClientCallback<RideInfo>() {

                    @Override
                    public void onSuccess(RideInfo r) {

                        Log.v("TAG", "Saved " + r.getUserID());
                        Toast.makeText(OfferActivity.this, "Ride Offered..", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e("TAG", "failed to save", error);
                    }
                });
            }
        });
    }
}
