package com.s22010120.ayur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;

public class UserProfileActivity extends AppCompatActivity {

    DatabaseHelper ayurDatabase;
    EditText  userFNameView, userLNameView, userEmailView;
    FrameLayout userDeleteAccBtn, userUpdateBtn;
    ImageView userPic;
    Uri userUri;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        //initialize id
        userFNameView = findViewById(R.id.userFirstNameView);
        userLNameView = findViewById(R.id.userLastNameView);
        userEmailView = findViewById(R.id.userEmailView);
        userDeleteAccBtn = findViewById(R.id.userDeleteAccountBtn);
        userUpdateBtn = findViewById(R.id.userUpdateAccountBtn);
        userPic = findViewById(R.id.user);
        //sharePreference for user Iamge
        preferences = getSharedPreferences("UserImg", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String encodeSaveImage = preferences.getString("Image", "");
        byte[] decodeString = Base64.decode(encodeSaveImage, Base64.DEFAULT);
        Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        if(decodeBitmap != null){
            userPic.setImageBitmap(decodeBitmap);
        }

        userDeleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }
        });
        //image get uri
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            userUri = data.getData();
                            userPic.setImageURI(userUri);

                        }else{
                            Toast.makeText(UserProfileActivity.this,"No any Image " +
                                    "Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
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

                //img save
                BitmapDrawable bitmapDrawable = (BitmapDrawable) userPic.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                String encodeImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                editor.putString("Image", encodeImage);
                editor.apply();
                Toast.makeText(UserProfileActivity.this, "Save Image", Toast.LENGTH_SHORT).show();


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