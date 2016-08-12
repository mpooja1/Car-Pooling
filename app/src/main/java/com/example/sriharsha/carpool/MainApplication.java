/**
 * Copyright (c) 2016 Pooja, SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */

package com.example.sriharsha.carpool;
import android.app.Application;

import com.kinvey.android.Client;

/**
 * Created by sriharsha on 7/30/2016.
 */

/**
 * This class establishes the conneciton with the Kinvey mBass
 */
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
