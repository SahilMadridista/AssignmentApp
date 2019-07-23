package com.example.craftwoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registeruser , userloginbutton;
    private long backpressedtime;
    private EditText emailedittext , passwordedittext;
    private Toast backtoast;
    private CheckBox logincheckbox;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        twitterLoginButton = (TwitterLoginButton)findViewById(R.id.twitterlogin);
        logincheckbox = (CheckBox)findViewById(R.id.logincheckbox);
        emailedittext = (EditText)findViewById(R.id.emailedittext);
        passwordedittext = (EditText)findViewById(R.id.passwordedittext);
        userloginbutton = (Button)findViewById(R.id.loginbutton);
        registeruser = (Button)findViewById(R.id.reisterbutton);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                //Success Method start

                TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                login(session);



                //Success method ends

            }

            @Override
            public void failure(TwitterException exception) {

                //Fail method start

                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();


                //Fail method ends

            }
        });

        if(firebaseAuth.getCurrentUser()!=null){

            //Stay on home page of the app

            finish();

            startActivity(new Intent(getApplicationContext(),HomePage.class));

        }





        //This part is for the " Create account " button to go to signup activity

        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(register);
            }
        });

        //Ended the " Create account " button step


        //This part is for checkbox

        logincheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    passwordedittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordedittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        userloginbutton.setOnClickListener(this);

        //Checkbox part is over

    }

    public void login(TwitterSession session){
        String username = session.getUserName();
        Intent intent = new Intent(MainActivity.this,TwitterHomePage.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    //OnCreate ENDED HERE ,

    /*public void login(TwitterSession session){

        String username = session.getUserName();
        Intent intent = new Intent(MainActivity.this,HomePage.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        twitterLoginButton.onActivityResult(requestCode,resultCode,data);

    }

    // Now our generated methods will start.


    //This is for double press back button to exit

    @Override
    public void onBackPressed() {
        if(backpressedtime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            super.onBackPressed();
            finish();
        }
        else {
            backtoast= Toast.makeText(MainActivity.this,"Press back again to exit",Toast.
                    LENGTH_SHORT);
            backtoast.show();

        }

        backpressedtime = System.currentTimeMillis();

    }

    //Backpressed method ended here


    //User Log in method starts here

    private void LogtheUserin(){

        String loginemail = emailedittext.getText().toString().trim();
        String loginpassword = passwordedittext.getText().toString().trim();

        if(TextUtils.isEmpty(loginemail)){

            //Empty email

            Toast.makeText(MainActivity.this,"Please enter Email",
                    Toast.LENGTH_SHORT).show();

            return;

            //execution stops

        }

        if(TextUtils.isEmpty(loginpassword)){

            //Empty password

            Toast.makeText(MainActivity.this,"Please enter Password",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        //Still inside Log in method

        progressDialog.setMessage("Logging the user in , It will take a moment");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(loginemail,loginpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.cancel();

                if(task.isSuccessful()){

                    //User is logged in

                    finish();

                    startActivity(new Intent(getApplicationContext(),HomePage.class));

                    Toast.makeText(MainActivity.this,"You are Logged in",Toast.LENGTH_SHORT)
                            .show();


                } else{

                    //User is not logged in

                    Toast.makeText(MainActivity.this,"Some error occured , Please Try again !!",Toast.LENGTH_SHORT)
                            .show();

                }

            }
        });


    }

    //User Log in method ends here



    @Override
    public void onClick(View view) {

        if(view == userloginbutton ){

            LogtheUserin();

            //This method is for log in the user into our app

        }

    }

}

//End of Code , Muchas Gracias !!
