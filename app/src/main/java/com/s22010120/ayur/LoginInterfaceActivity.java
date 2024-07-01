package com.s22010120.ayur;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginInterfaceActivity extends AppCompatActivity {

    private Button uLoginBtn;
    private  EditText userEmail, userPassword;
    private TextView forgetBtn, signUpTxt;
    private CheckBox rememberUserLog;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DatabaseHelper ayurDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_interface);

        ayurDatabase = new DatabaseHelper(this);

        uLoginBtn = findViewById(R.id.uLoginBtn);
        userEmail = findViewById(R.id.uLoginEmail);
        userPassword = findViewById(R.id.uLoginPassword);
        forgetBtn = findViewById(R.id.forgetPasswordBtn);
        rememberUserLog = findViewById(R.id.rememberLog);
        signUpTxt = findViewById(R.id.signUpTxt);


        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();
        boolean isLogin = sp.getBoolean("IS_LOGGED", false);
        if(isLogin){
            startActivity(new Intent(LoginInterfaceActivity.this, HomeInterfaceActivity.class));
            finish();
        }

        //forgotPassword button
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginInterfaceActivity.this,
                        ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        //signupTxtButton
        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInterfaceActivity.this,
                        RegisterInterfaceActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //login button
        userLogin();

    }

    //user login function and details validate
    public void userLogin(){

        uLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmailAdrs = userEmail.getText().toString();
                String upasw = userPassword.getText().toString();

                if(uEmailAdrs.equals("") || upasw.equals("")){
                    Toast.makeText(LoginInterfaceActivity.this,"Please Enter Email " +
                                    "Address and Password",
                            Toast.LENGTH_LONG).show();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(uEmailAdrs).matches()) {
                    userEmail.setError("Please Enter Valid Email Address");

                } else if (upasw.length()<6) {
                    userPassword.setError("PLease Enter 6 Characters");

                } else {
                    boolean checkUserLogin  = ayurDatabase.checkLogin(uEmailAdrs, upasw);

                    if(checkUserLogin){
                        if(rememberUserLog.isChecked()){

                            editor.putString("email", uEmailAdrs);
                            editor.putString("password", upasw);
                            editor.putBoolean("IS_LOGGED",true);
                            editor.apply();
                            Toast.makeText(LoginInterfaceActivity.this,"Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginInterfaceActivity.this,
                                    HomeInterfaceActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginInterfaceActivity.this,"Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginInterfaceActivity.this,
                                    HomeInterfaceActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }else {
                        Toast.makeText(LoginInterfaceActivity.this, "Email or Password " +
                                        "Incorrect",
                                Toast.LENGTH_SHORT).show();
                        userEmail.getText().clear();
                        userPassword.getText().clear();
                    }
                }
            }
        });
    }
}