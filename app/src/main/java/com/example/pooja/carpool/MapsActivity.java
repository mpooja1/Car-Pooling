package com.example.pooja.carpool;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,OnMarkerClickListener {

    private GoogleMap mMap;
    public Geocoder gMap;
    public Map<LatLng, String> hm = new HashMap<LatLng, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
          //      .getMap();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng Portland = new LatLng(45.5, -123);
        mMap.addMarker(new MarkerOptions().position(Portland).title("Marker in Portland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Portland));
        mMap.setOnMarkerClickListener(this);
    }

        public  void buttonOnClick(View v) {
            mMap.clear();
            final EditText sourceText = (EditText)findViewById(R.id.textDestination);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("RideInfo");
            Toast.makeText(getApplicationContext(),sourceText.getText().toString(),Toast.LENGTH_SHORT).show();

            query.whereEqualTo("Destination", sourceText.getText().toString());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {



                        for (ParseObject object:
                                objects) {
                            Double longitude = getlon(object.getString("Source"));
                            Double latitude = getlat(object.getString("Source"));
                            Toast.makeText(getApplicationContext(),longitude.toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),latitude.toString(),Toast.LENGTH_SHORT).show();
                            LatLng goosehollow = new LatLng(latitude,longitude );
                            hm.put(goosehollow,object.getString("Source"));
                            mMap.addMarker(new MarkerOptions().position(goosehollow).title("Marker in " + object.getString("Source")));
                        }
                        // String current_destination = objects.get(0).getString("Destination");
                        //Log.d("score", "Error: " + e.getMessage());
                    } else {
                        //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
          //  LatLng Beaverton = new LatLng(45.4, -122.8);
         //   LatLng Hilsboro = new LatLng(45.5, -110);
         //   LatLng Psu = new LatLng(45.5, -122.6);
         //   mMap.addMarker(new MarkerOptions().position(Beaverton).title("Marker in Beaverton"));
         //   mMap.addMarker(new MarkerOptions().position(Hilsboro).title("Marker in Hilsboro"));
         //   mMap.addMarker(new MarkerOptions().position(Psu).title("Marker in Psu"));
          //  gMap = new Geocoder(getApplicationContext(), Locale.ENGLISH);
           // List<Address> newlist =  gMap.getFromLocationName("1630 SW Clay Street, Portland, Oregon",1);
         //   Double longitude = getlat("1630 SW Clay Street, Portland, Oregon");
          //  Double latitude = getlon("1630 SW Clay Street, Portland, Oregon");
         //   Button button = (Button) v;
          //  ((Button) v).setText(longitude + " ," + latitude);

        }

    public double getlat(String address) {
        Geocoder coder = new Geocoder(this);
        try {
            double longitude;
            double latitude =0.0;
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for(Address add : adresses){
                //Controls to ensure it is right address such as country etc.
                    longitude = add.getLongitude();
                    latitude = add.getLatitude();
            }
            return latitude;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getlon(String address) {
        Geocoder coder = new Geocoder(this);
        try {
            double longitude = 0.0;
            double latitude =0.0;
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for(Address add : adresses){
                //Controls to ensure it is right address such as country etc.
                longitude = add.getLongitude();
                latitude = add.getLatitude();
            }
            return longitude;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(getApplicationContext(),
                "Marker Clicked: " + marker.getPosition(), Toast.LENGTH_LONG)
                .show();

        Log.i("GoogleMapActivity", "onMarkerClick");
       // Button button = (Button) v;
      //  ((Button) v).setText("" +marker.getPosition().latitude);
        for (Map.Entry<LatLng, String> entry : hm.entrySet()) {
            LatLng key = entry.getKey();
            String value = entry.getValue();
            Log.i(""+key.latitude, ""+key.longitude);
            Log.i(""+marker.getPosition().longitude, ""+marker.getPosition().latitude);
            if ((key.latitude== marker.getPosition().latitude) && (key.longitude == marker.getPosition().longitude))
            {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("RideInfo");
                query.whereEqualTo("Destination", value);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        "in else ", Toast.LENGTH_LONG)
                                        .show();
                            } else {

                                Intent myIntent = new Intent(getApplicationContext(), SelectedDriverInfo.class);
                                myIntent.putExtra("sendName","Tejaswini");
                                startActivityForResult(myIntent, 0);
                                finish();
                                // String current_destination = objects.get(0).getString("Destination");
                                //Log.d("score", "Error: " + e.getMessage());
                                //Log.d("score", "Retrieved " + scoreList.size() + " scores");


                            }


                    }


            });
                //matched string is 'value'
                //move to next screen with value
            }

        }

        return false;
    }



}
