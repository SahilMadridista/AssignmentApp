package com.example.craftwoo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TwitterHomePage extends AppCompatActivity {

    private TextView usernametext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_home_page);

        usernametext = (TextView)findViewById(R.id.yourusernametext);

        String username = getIntent().getStringExtra("username");

        usernametext.setText(username);





    }
}
