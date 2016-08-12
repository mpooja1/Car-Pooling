/**
 * Copyright (c) 2016 Pooja, SriHarsha
 * This code is available under the "MIT License".
 * Please see the file LICENSE in this distribution
 * for license terms.
 */
package com.example.sriharsha.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

/**
 * This class used to update information of user as like password , car no and mobile no
 */
public class UpdateActivity extends KinveyActivity {

    String user;

    /**
     * This method is used to set layout for update screen that allows user to update information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getClient().user().isUserLoggedIn();

        user = getClient().user().getUsername();
        final Button update = (Button) findViewById(R.id.btn_UpdateProfile);
        final Button cancel = (Button) findViewById(R.id.btn_UpdateCancel);
        TextView textView =(TextView)findViewById(R.id.textview_UserID);
        textView.setText(user.toString());
        final EditText password = (EditText)findViewById(R.id.editText_Password);
        final EditText Phone = (EditText)findViewById(R.id.editText_PhoneNo);
        final EditText CarNo = (EditText)findViewById(R.id.editText_CarNo);

        User_Profile profile = new User_Profile();
        profile.setUserID(user);
        final AsyncAppData<User_Profile> myData = getClient().appData("User_Profile", User_Profile.class);
        myData.save(profile, new KinveyClientCallback<User_Profile>() {

                    @Override
                    public void onSuccess(User_Profile user_profile) {
                        Log.v("TAG", "Profile Created");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v("TAG", "Profile Creation Failed");
                    }
                });


            cancel.setOnClickListener(new View.OnClickListener()

                                      {

                                          @Override
                                          public void onClick(View v) {
                                              Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                                              startActivityForResult(myIntent, 0);
                                              finish();
                                          }

                                      }

            );


           update.setOnClickListener(new View.OnClickListener()

                                     {

                                         @Override
                                         public void onClick(View v) {

                                             Query myQuery = getClient().query();
                                             myQuery.equals("User_id", user.toString());
                                             final AsyncAppData<User_Profile> myData = getClient().appData("User_Profile", User_Profile.class);
                                             myData.get(myQuery, new KinveyListCallback<User_Profile>() {
                                                 @Override
                                                 public void onSuccess(User_Profile[] user_profiles) {
                                                     if (user_profiles.length > 0) {
                                                         user_profiles[0].setUserID(user.toString());
                                                         user_profiles[0].setUserPassword(password.getText().toString());
                                                         user_profiles[0].setCar_Num(CarNo.getText().toString());
                                                         user_profiles[0].setPhone_Num(Phone.getText().toString());
                                                         // Object callback = null;
                                                         // getClient().user().update((KinveyClientCallback<User>) callback);
                                                         AsyncAppData<User_Profile> myData = getClient().appData("User_Profile", User_Profile.class);
                                                         myData.save(user_profiles[0], new KinveyClientCallback<User_Profile>() {

                                                             @Override
                                                             public void onSuccess(User_Profile user_profile) {
                                                                 Toast.makeText(UpdateActivity.this, "Updated..", Toast.LENGTH_SHORT).show();
                                                                 Log.v("TAG", "Profile updated");
                                                             }

                                                             @Override
                                                             public void onFailure(Throwable throwable) {
                                                                 Log.v("TAG", "Profile updation failed");

                                                             }

                                                         });
                                                     } else {
                                                         //print error
                                                         Toast.makeText(UpdateActivity.this, "Somthing went wrong..", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(Throwable throwable) {
                                                     Log.v("TAG", "Profile loading failed");

                                                 }
                                             });


                                             Intent myIntent = new Intent(v.getContext(), HomeActivity.class);

                                             startActivityForResult(myIntent, 0);

                                             finish();
                                         }

                                     }

           );

       }
    }
