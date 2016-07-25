package com.example.pooja.carpool;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by pooja on 7/22/2016.
 */
public class ParseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dvgbIXDG7QSv5Xj87PBtBfSai0g0dR0x0d1DA74D", "WzSM69YkFK5Olh3aEzghZA0ODfxvyknvfvD8CgVU");

    }


}