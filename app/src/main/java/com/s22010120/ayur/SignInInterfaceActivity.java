package com.s22010120.ayur;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignInInterfaceActivity extends AppCompatActivity {

    private Button perSignBtn;
    private Button perLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signininterface);

        perSignBtn = findViewById(R.id.preSignBtn);
        perLoginBtn = findViewById(R.id.preLoginBtn);

        //navigate Register Page
        perSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignInInterfaceActivity.this,
                        RegisterInterfaceActivity.class);
                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation(SignInInterfaceActivity.this);
                startActivity(intent, options.toBundle());

            }
        });

        //navigate Login Page
        perLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInInterfaceActivity.this,
                        LoginInterfaceActivity.class);
                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation(SignInInterfaceActivity.this);

                startActivity(intent, options.toBundle());
            }

        });


    }
}