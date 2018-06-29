package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static com.google.android.gms.analytics.internal.zzy.e;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean signupModeActive = true;
    TextView ChangeSignupModeTextView;
    @Override
    public void onClick(View view) {

        if ( view.getId() == R.id.ChangeSignupModeTextView)
        {
            Button loginButton =(Button) findViewById(R.id.loginButton);

            if(signupModeActive)
            {
                signupModeActive = false;
                loginButton.setText("LogIn");
                ChangeSignupModeTextView.setText("or SignUp");
            }
            else
            {
                signupModeActive = true;
                loginButton.setText("SignUp");
                ChangeSignupModeTextView.setText("or LogIn");
            }
        }
    }

    public void logIn(View view)
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        if(usernameEditText.getText().toString().matches("")|| passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this , "A username and password is required",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(signupModeActive) {

                ParseUser user = new ParseUser();

                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("SignUp", "successful");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user !=null)
                        {
                            Log.i("LogIn","Successful");
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ParseUser.getCurrentUser().logOut();


        ChangeSignupModeTextView = (TextView) findViewById(R.id.ChangeSignupModeTextView);

        ChangeSignupModeTextView.setOnClickListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
