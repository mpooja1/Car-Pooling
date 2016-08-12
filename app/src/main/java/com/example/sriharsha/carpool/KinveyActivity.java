/**
 * Copyright (c) 2016 Pooja, SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */

package com.example.sriharsha.carpool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kinvey.android.Client;

/**
 * This class is used to create connection with Kinvey database and get details of currently logged in user
 */
public class KinveyActivity extends AppCompatActivity {
    /**
     * This method is used to get client details of currently logged in user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This method is used to get client details of currently logged in user
     * @return client details
     */
    public Client getClient(){
        return ((MainApplication)getApplication()).getClient();
    }
}