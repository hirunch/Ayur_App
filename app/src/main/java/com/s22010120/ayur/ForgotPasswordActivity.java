package com.s22010120.ayur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText userResetEmail, userResetPassword, userResetConfirmPsw;
    Button resetPasswordBtn;
    DatabaseHelper ayurDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        //initialize id
        userResetEmail = findViewById(R.id.uResetEmail);
        userResetPassword = findViewById(R.id.uResetPassword);
        resetPasswordBtn = findViewById(R.id.uResetBtn);
        userResetConfirmPsw = findViewById(R.id.uResetPasswordConf);

        //call reset function
        resetPassword();

    }

    //password reset function
    public void resetPassword(){

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userResetEmail.getText().toString().equals("") || userResetPassword.getText()
                        .toString().equals("")){
                    Toast.makeText(ForgotPasswordActivity.this,"Please Fill and Try Again",
                            Toast.LENGTH_LONG).show();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(userResetEmail.getText().toString())
                        .matches()) {
                    userResetEmail.setError("Please Enter Valid Email Address");

                } else if (userResetPassword.getText().toString().length()<6) {
                    userResetPassword.setError("PLease Enter 6 Characters");

                } else if (!userResetPassword.getText().toString().equals(userResetConfirmPsw.getText()
                        .toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password Not Matching",
                            Toast.LENGTH_SHORT ).show();

                } else{
                    boolean isReset = ayurDatabase.resetPassword(userResetEmail.getText().toString(),
                            userResetPassword.getText().toString());
                    if(isReset){
                        Toast.makeText(ForgotPasswordActivity.this,"Password Reset Successfully",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this,
                                LoginInterfaceActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(ForgotPasswordActivity.this,"Error try again",
                                Toast.LENGTH_SHORT).show();
                        userResetPassword.getText().clear();
                        userResetConfirmPsw.getText().clear();

                    }
                }


            }
        });
    }
}