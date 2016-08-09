package com.example.sriharsha.carpool;

/**
 * Created by sriharsha on 7/30/2016.
 */
import android.app.Application;

import com.kinvey.android.Client;


public class MainApplication extends Application {
    private Client client;

    @Override
    public void onCreate() {
        super.onCreate();
        defineClient();
    }

    private void defineClient() {
        client = new Client.Builder("kid_SyYWFh5d",//APP_ID
                "0b1538e57dfb46da87b5da42516501ff",//APP_SECRET
                getApplicationContext()).build();
    }

    public Client getClient(){
        return client;
    }
}
