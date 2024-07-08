package com.s22010120.ayur;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class OsuInfoEditingActivity extends AppCompatActivity {

    ImageView osuUpdateImage;
    Button osuUpdateBtn;
    EditText osuUpdateTopic, osuUpdateDesc, osuUpdateDate;
    String topic, desc, date;
    String osuImageUrl;
    String key, oldOsuImageUrl;
    Uri osuUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_osu_info_editing);

        osuUpdateTopic = findViewById(R.id.osuUpdateTopic);
        osuUpdateDesc = findViewById(R.id.osuUpdateDesc);
        osuUpdateDate = findViewById(R.id.osuUpdateDate);
        osuUpdateImage = findViewById(R.id.osuUpdateImage);
        osuUpdateBtn = findViewById(R.id.osuUpdateButton);

        //selected image get uri
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            osuUri = data.getData();
                            osuUpdateImage.setImageURI(osuUri);
                        }else {
                            Toast.makeText(OsuInfoEditingActivity.this, "Not Selected " +
                                    "Image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Glide.with(OsuInfoEditingActivity.this).load(bundle.getString("Image")).into(osuUpdateImage);
            osuUpdateTopic.setText(bundle.getString("Topic"));
            osuUpdateDesc.setText(bundle.getString("Description"));
            osuUpdateDate.setText(bundle.getString("Date"));
            key = bundle.getString("Key");
            osuImageUrl = bundle.getString("Image");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Osu Data").child(key);

        //image pick device storage
        osuUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        //update button
        osuUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOsuData();
            }
        });

    }
    //updating function
    public void updateOsuData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Osu Data")
                .child(osuUri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(OsuInfoEditingActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(osuUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                osuImageUrl = urlImage.toString();
                uploadUpdateOsuData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    //saved data uploading function
    public void uploadUpdateOsuData(){

        topic = osuUpdateTopic.getText().toString().trim();
        desc = osuUpdateDesc.getText().toString().trim();
        date = osuUpdateDate.getText().toString();

        OsuInfoDataClass osuInfoDataClass = new OsuInfoDataClass(topic, desc, date, osuImageUrl);

        databaseReference.setValue(osuInfoDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl
                            (oldOsuImageUrl);
                    reference.delete();
                    Toast.makeText(OsuInfoEditingActivity.this, "Osu Data Updating",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(OsuInfoEditingActivity.this, "Error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OsuInfoEditingActivity.this, e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}