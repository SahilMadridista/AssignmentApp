package com.example.craftwoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button logoutButton;
    private TextView usernametext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        logoutButton = (Button)findViewById(R.id.logoutbutton);
        firebaseAuth = FirebaseAuth.getInstance();

        usernametext = (TextView)findViewById(R.id.yourusername);

        /*String username = getIntent().getStringExtra("username");
        usernametext = (TextView)findViewById(R.id.yourusername);
        usernametext.setText(username);*/

        //If there is no user logged in then open login activity

        usernametext.setText(firebaseAuth.getCurrentUser().getEmail());

        if(firebaseAuth.getCurrentUser() == null){

            //Start the login activity

            finish();

            startActivity(new Intent(HomePage.this,MainActivity.class));

        }

        //By above IF statement we'll be into login activity

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        logoutButton.setOnClickListener(this);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel =
                        new NotificationChannel("My Notifications","My Notifications",
                        NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }



    }

    //OnCreate ends here

    //Log out method starts

    private void Logtheuserout(){

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePage.this,MainActivity.class));

    }

    //Log out method ends

    @Override
    public void onClick(View view) {

        if(view == logoutButton){

            Logtheuserout();

        }

    }
}
