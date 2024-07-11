package com.s22010120.ayur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteAccountActivity extends AppCompatActivity {

    Button deleteAccountBtn;
    EditText deleteEmail, deletePassword;
    SharedPreferences sp;
    SharedPreferences.Editor editor , deleteAuth;
    DatabaseHelper ayurDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        //database
        ayurDatabase = new DatabaseHelper(this);
        //initialize id
        deleteEmail = findViewById(R.id.uDeleteEmail);
        deletePassword = findViewById(R.id.uDeletePassword);
        deleteAccountBtn = findViewById(R.id.uDeleteBtn);

        //sharedPreference
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();
        // Retrieves an instance of SharedPreferences with the name "ShName" and private mode
        SharedPreferences preferences = getSharedPreferences("ShName", Context.MODE_PRIVATE);
        // Retrieves an editor for modifying the SharedPreferences
        deleteAuth =preferences.edit();
        //call delete function
        deleteAccount();
    }

    public void deleteAccount(){

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uDeletedEmail = deleteEmail.getText().toString();
                String uDeletePassword = deletePassword.getText().toString();
                Integer deleteData  = ayurDatabase.DeleteAccount(uDeletedEmail, uDeletePassword);

                if(uDeletedEmail.equals("") || uDeletePassword.equals("")){
                    Toast.makeText(DeleteAccountActivity.this, "Please Fill it",
                            Toast.LENGTH_SHORT).show();

                }else {

                    if(deleteData>0){
                        editor.clear();
                        editor.apply();
                        deleteAuth.clear();
                        deleteAuth.apply();
                        Toast.makeText(DeleteAccountActivity.this, "Account Delete Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeleteAccountActivity.this,
                                LoginInterfaceActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(DeleteAccountActivity.this, "Error Try Again",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}