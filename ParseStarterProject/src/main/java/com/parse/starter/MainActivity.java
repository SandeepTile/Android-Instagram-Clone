/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Boolean signUpModeActive=true;

  TextView changeSignUpmodeTextView;

  @Override
  public void onClick(View view) {

    if(view.getId()==R.id.changeSignupModeTextView){

      Button signupButton=(Button)findViewById(R.id.signupButton);

      if (signUpModeActive){

        signUpModeActive=false;
        signupButton.setText("Login");
        changeSignUpmodeTextView.setText("Or, Signup");

      }else {

        signUpModeActive=true;
        signupButton.setText("Signup");
        changeSignUpmodeTextView.setText("Or, Login");
      }


    }
  }

  public void signUp(View view){

    EditText usernameEditText=(EditText)findViewById(R.id.usernameEditText);
    EditText passwordEditText=(EditText)findViewById(R.id.passwordEditText);

    if (usernameEditText.getText().toString().matches("")||(passwordEditText.getText().toString().matches(""))){

      Toast.makeText(this, "A username and password required", Toast.LENGTH_LONG).show();
    }else {


      if(signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {

              Toast.makeText(MainActivity.this, "Signup Successful", Toast.LENGTH_LONG).show();
            } else {

              Log.i("SignUp", "Failed" + e.getMessage());
            }
          }
        });
      }else {

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if (user!=null){

              Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    changeSignUpmodeTextView=(TextView)findViewById(R.id.changeSignupModeTextView);

    changeSignUpmodeTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}