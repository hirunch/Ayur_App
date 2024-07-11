package com.s22010120.ayur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity{

    TextView profileBtn, stepCount, userName, aboutViewBtn;
    FrameLayout loginOutBtn;
    SharedPreferences sp;
    ImageView userprofile;
    SharedPreferences.Editor editor;
    CheckBox biometricsCheck;
    DatabaseHelper ayurDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        //initialize id
        profileBtn = (TextView) findViewById(R.id.profileViewBtn);
        loginOutBtn = findViewById(R.id.loginOutBtn);
        biometricsCheck = findViewById(R.id.biometricsCheck);
        userName = findViewById(R.id.usename);
        userprofile = findViewById(R.id.userPic);
        aboutViewBtn = (TextView) findViewById(R.id.about_view);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);

        SharedPreferences preferences = getSharedPreferences("ShName", Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean("checkBoxKey", false);
        biometricsCheck.setChecked(isChecked);

        biometricsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("checkBoxKey", isChecked);
                editor.apply();
            }
        });

        //profile vew button call
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        //aboutVew Button
        aboutViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        //sharedPreference
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();


        //login out button
        loginOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOut();
            }
        });

        //view data function call
        viewData();
    }

    private void loginOut() {
        editor.clear();
        editor.apply();
        startActivity(new Intent(UserActivity.this, LoginInterfaceActivity.class));
        finish();
    }

    public void viewData(){
        Cursor cursor = ayurDatabase.getUserData();

        if(cursor.moveToFirst()){
            userName.setText(cursor.getString(1));

        }
    }

}

