package com.example.pooja.carpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;


public class RideOffer extends AppCompatActivity {
    String dName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_offer);



        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            dName = extras.getString("tempName");
        }

        final TextView sourceText = (TextView)findViewById(R.id.sText);
        final TextView destinationText = (TextView)findViewById(R.id.dText);
        final TextView timeText = (TextView)findViewById(R.id.tText);
        final Button offerRide = (Button)findViewById(R.id.offer);
        offerRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getApplicationContext(),dName,Toast.LENGTH_SHORT).show();
                ParseObject rideInfo = new ParseObject("RideInfo");
                rideInfo.put("Driver", dName);
                rideInfo.put("Source", sourceText.getText().toString());
                rideInfo.put("Destination", destinationText.getText().toString());
                rideInfo.put("Time", timeText.getText().toString());
                rideInfo.saveInBackground();

            }
        });





    }

}
