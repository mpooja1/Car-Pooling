package com.example.pooja.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoginActivity extends AppCompatActivity {

    private TextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private Button mRegisterButton;
    String dName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.buttonRegister);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfile");
                query.whereEqualTo("Email", mEmailView.getText().toString());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {

                        if (object == null) {
                            Toast.makeText(getApplicationContext(), "Email not in the record", Toast.LENGTH_SHORT).show();

                            //Log.d("score", "The getFirst request failed.");
                        } else {
                            if (mPasswordView.getText().toString().equals(object.getString("Password"))) {
                                dName = object.getString("Name");
                                Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                myIntent.putExtra("tempName", dName);
                                startActivityForResult(myIntent, 0);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                });

            }



        });



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), RegisterActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
    }

}
