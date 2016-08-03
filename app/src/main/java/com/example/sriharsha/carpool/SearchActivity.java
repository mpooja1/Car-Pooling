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

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;


/**
 * This is a searchactivity
 */
public class SearchActivity extends KinveyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

    private void displayConfirmation(){

        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.RadioButtonGroup);
        if(radioGroup.getChildCount()>0) {
            RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedtext = radioButton.getText().toString();
            Toast.makeText(SearchActivity.this, selectedtext, Toast.LENGTH_SHORT).show();

            //Toast.makeText(SearchActivity.this, text[5], Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, RideConfirmation.class);
            myIntent.putExtra("RideDetails",selectedtext);
            startActivityForResult(myIntent, 0);
            finish();

        }
        else
        {
            Toast.makeText(SearchActivity.this, "No Rides available to book.. Please try again", Toast.LENGTH_SHORT).show();
        }

    }

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
