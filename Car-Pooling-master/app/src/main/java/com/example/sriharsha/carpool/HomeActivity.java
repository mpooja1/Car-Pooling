package com.example.sriharsha.carpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button search;
    private Button ofrRide;
    private Button logout;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search = (Button)findViewById(R.id.btn_search);
        ofrRide =(Button)findViewById(R.id.btn_ofrRide);
        update =(Button)findViewById(R.id.btn_Update);
        logout = (Button)findViewById(R.id.btn_Logout);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });

        ofrRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), OfferActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), UpdateActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });



    }
}
