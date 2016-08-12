/**
 * Copyright (c) 2016 Pooja, SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */

package com.example.sriharsha.carpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class provides display for main screen
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is used to display main screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
