package com.example.craftwoo;

import androidx.annotation.NonNull;
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

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox registercheckbox;
    private TextView signintextview;
    private EditText registeremail,registerpassword;
    private Button registeruserbutton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressDialog = new ProgressDialog(this);

        registercheckbox = (CheckBox)findViewById(R.id.registercheckbox);
        registeremail = (EditText)findViewById(R.id.registeremail);
        registerpassword = (EditText)findViewById(R.id.registerpassword);
        registeruserbutton = (Button)findViewById(R.id.registeruser);
        signintextview = (TextView)findViewById(R.id.textviewforsignin);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){

            //Stay on home page of the app

            finish();

            startActivity(new Intent(getApplicationContext(),HomePage.class));

        }

        //Click on the text view to go to the log in activity

        signintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });

        // Ended : Click on the text view to go to the log in activity



        //Sign up wala check box

        registercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    registerpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    registerpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //Ended : Sign up wala check box


        registeruserbutton.setOnClickListener(this);
        signintextview.setOnClickListener(this);


    }



    private void registerUser(){

        //Start of registerUser method

        String email = registeremail.getText().toString().trim();
        String password = registerpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            //Empty email

            Toast.makeText(Main2Activity.this,"Please enter Email",Toast.LENGTH_SHORT).show();

            return;

            //execution stops

        }

        if(TextUtils.isEmpty(password)){

            //Empty password

            Toast.makeText(Main2Activity.this,"Please enter Password",Toast.LENGTH_SHORT).show();

            return;
        }

        //Still inside registerUser method

        progressDialog.setMessage("Saving Data , Please wait a moment...");
        progressDialog.show();



        //Yaha se hota hai sign up shuru


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Now we have to cancel the progresdialog

                        progressDialog.cancel();

                        //Progressdialog cancelled

                        if(task.isSuccessful()){

                            finish();

                            startActivity(new Intent(getApplicationContext(),HomePage.class));


                            //User is regisered

                            Toast.makeText(Main2Activity.this,
                                    "Congrats !! Data Saved , You are logged in",
                                    Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                            startActivity(intent);


                        } else {

                            //User is not registered

                            Toast.makeText(Main2Activity.this,
                                    "Could not register , Please try again !!",
                                    Toast.LENGTH_SHORT).show();


                        }

                    }
                });

        //Firebase sign up yha khatam



    } //End of registerUser method





    @Override
    public void onClick(View view) {

        if(view == registeruserbutton){
            registerUser();
        }

        if(view == signintextview){


            //Open Sign in activity


            startActivity(new Intent(Main2Activity.this,MainActivity.class));


        }

    }
}
