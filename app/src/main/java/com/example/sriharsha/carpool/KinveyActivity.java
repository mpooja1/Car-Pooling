package com.example.sriharsha.carpool;

/**
 * Created by sriharsha on 7/30/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kinvey.android.Client;


public class KinveyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Client getClient(){
        return ((MainApplication)getApplication()).getClient();
    }
}