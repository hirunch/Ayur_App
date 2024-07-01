package com.s22010120.ayur;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfileActivity extends AppCompatActivity {

    DatabaseHelper ayurDatabase;
    EditText  userFNameView, userLNameView, userEmailView;
    FrameLayout userDeleteAccBtn, userUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        //database call
        ayurDatabase = new DatabaseHelper(this);
        userFNameView = findViewById(R.id.userFirstNameView);
        userLNameView = findViewById(R.id.userLastNameView);
        userEmailView = findViewById(R.id.userEmailView);
        userDeleteAccBtn = findViewById(R.id.userDeleteAccountBtn);
        userUpdateBtn = findViewById(R.id.userUpdateAccountBtn);


        userDeleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }
        });


        //call view data and update details function
        viewData();
        updateDetails();
    }

    public void viewData(){

        Cursor result = ayurDatabase.getUserData();

        if(result.moveToFirst()){
            userFNameView.setText(result.getString(1));
            userLNameView.setText(result.getString(2));
            userEmailView.setText(result.getString(3));

        }


    }

    public void updateDetails(){

        userUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isUpdate = ayurDatabase.UpdateUserData(userFNameView.getText().toString(),
                        userLNameView.getText().toString(),userEmailView.getText().toString());

                if(isUpdate){
                    Toast.makeText(UserProfileActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(UserProfileActivity.this, "Data No Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}