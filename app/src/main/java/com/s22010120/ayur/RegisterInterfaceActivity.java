package com.s22010120.ayur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterInterfaceActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, rePassword, confirmAdmin;
    private CheckBox adminCheck;
    private Button uRegisterBtn;
    private TextView loginTxt;
    DatabaseHelper ayurDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_interface);
        //intialize id
        loginTxt = findViewById(R.id.loginTxt);
        //User Register details
        uRegisterBtn = findViewById(R.id.uRegister);
        firstName = findViewById(R.id.uFirstName);
        lastName = findViewById(R.id.uLastName);
        email = findViewById(R.id.uEmail);
        password = findViewById(R.id.uPassword);
        rePassword = findViewById(R.id.uRPassword);
        confirmAdmin = findViewById(R.id.confirmAdmin);
        adminCheck = findViewById(R.id.adminCheck);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);

        //loginTxt
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterInterfaceActivity.this,
                        LoginInterfaceActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //call register user function
        registerUser();

    }

    //user register function and validate and all details adding database
    public void registerUser() {

        uRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uPassword = password.getText().toString();
                String uRePassword = rePassword.getText().toString();
                String uFName = firstName.getText().toString();
                String uLName = lastName.getText().toString();
                String uEmail = email.getText().toString();


                if (!uPassword.equals(uRePassword)) {
                    Toast.makeText(RegisterInterfaceActivity.this, "Password Not Matching!",
                            Toast.LENGTH_SHORT).show();


                } else if (uFName.equals("") || uLName.equals("") || uEmail.equals("") ||
                        uPassword.equals("")) {
                    Toast.makeText(RegisterInterfaceActivity.this, "Please Fill All Deatails",
                            Toast.LENGTH_SHORT).show();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
                    email.setError("Enter Valid Email");

                } else if (uPassword.length()<6) {
                    password.setError("Enter must be 6 characters");

                } else if (adminCheck.isChecked()){

                    String adminVerify = confirmAdmin.getText().toString();

                    if (adminVerify.equals("4546")){

                        boolean isInserted = ayurDatabase.dataInsert(firstName.getText().toString(),
                                lastName.getText().toString(), email.getText().toString(), password.getText().toString(), adminCheck.isChecked());

                        if (isInserted) {
                            Toast.makeText(RegisterInterfaceActivity.this, "Registration Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterInterfaceActivity.this,
                                    LoginInterfaceActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(RegisterInterfaceActivity.this, "Registration UnSuccessfully",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(RegisterInterfaceActivity.this, "Code Is Incorrect",
                                Toast.LENGTH_SHORT).show();

                    }


                }else{
                    boolean isInserted = ayurDatabase.dataInsert(firstName.getText().toString(),
                            lastName.getText().toString(), email.getText().toString(), password.getText().toString(), adminCheck.isChecked());

                    if (isInserted) {
                        Toast.makeText(RegisterInterfaceActivity.this, "Registration Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterInterfaceActivity.this,
                                LoginInterfaceActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(RegisterInterfaceActivity.this, "Registration UnSuccessfully",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }
}