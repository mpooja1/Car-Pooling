package com.example.sriharsha.carpool;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;

public class SearchActivity extends KinveyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText Destination = (EditText) findViewById(R.id.edit_Dest);

        final Button SearchRide = (Button) findViewById(R.id.btn_Search);

        assert SearchRide != null;
        SearchRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


              /*Client kinveyClient = new Client.Builder("kid_SyYWFh5d",//APP_ID
                      "0b1538e57dfb46da87b5da42516501ff",//APP_SECRET
                      getApplicationContext()).build();*/
                RideInfo ride = new RideInfo();

                Query myQuery = getClient().query();
                myQuery.equals("Destination",Destination.getText().toString());
                AsyncAppData<RideInfo> myData = getClient().appData("RideInfo", RideInfo.class);
                myData.get(myQuery, new KinveyListCallback<RideInfo>() {
                    @Override
                    public void onSuccess(RideInfo[] rideInfos) {
                        Log.v("TAG", "received "+ rideInfos.length + " events");
                      //  RadioGroup rg1 = new RadioGroup();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v("TAG","Failed");

                    }
                });
            }
        });
    }
    }
