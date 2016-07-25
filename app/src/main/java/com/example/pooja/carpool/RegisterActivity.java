package com.example.pooja.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText Name;
    EditText PhoneNum;
    EditText EmailID;
    EditText Password;
    EditText CPassword;
    Button RegButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name =(EditText) findViewById(R.id.editTextName);
        PhoneNum = (EditText) findViewById(R.id.editTextPhNum);
        EmailID  = (EditText) findViewById(R.id.editTextEmailID);
        Password = (EditText) findViewById(R.id.pwd);

        RegButton = (Button) findViewById(R.id.buttonRegister);
        RegButton.setActivated(false);




        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject userProfile = new ParseObject("UserProfile");
                userProfile.put("Name",Name.getText().toString());
                userProfile.put("Password",Password.getText().toString());
                //Toast.makeText(getApplicationContext(), Password.getText(), Toast.LENGTH_SHORT).show();
                userProfile.put("PhoneNumber",PhoneNum.getText().toString());
                userProfile.put("Email",EmailID.getText().toString());
                userProfile.put("Address","abcdefgh");
                userProfile.put("Gender","M");
                userProfile.put("Driver",1);
                userProfile.put("CarCapacity",20);
                userProfile.put("LicencePlate","xyz");
                userProfile.saveInBackground();
                Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
                finish();


            }
        });



    }

    public boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");

            return false;
        }

        return true;
    }

}
