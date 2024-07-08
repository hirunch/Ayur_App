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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ohEditActivity extends AppCompatActivity {
    ImageView ohUpdateImage;
    Button ohUpdateBtn;
    EditText ohUpdateTopic, ohUpdateDesc, ohUpdateDate;
    String topic, desc, date;
    String ohImageUrl;
    String ohKey, oldOhImageUrl;
    Uri ohUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oh_edit);

        ohUpdateTopic = findViewById(R.id.ohUpdateTopic);
        ohUpdateDesc = findViewById(R.id.ohUpdateDesc);
        ohUpdateDate = findViewById(R.id.ohUpdateDate);
        ohUpdateImage = findViewById(R.id.ohUpdateImage);
        ohUpdateBtn = findViewById(R.id.ohUpdateButton);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            ohUri = data.getData();
                            ohUpdateImage.setImageURI(ohUri);

                        }else{
                            Toast.makeText(ohEditActivity.this, "Not Selected Image",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Glide.with(ohEditActivity.this).load(bundle.getString("Image")).into(ohUpdateImage);
            ohUpdateTopic.setText(bundle.getString("Topic"));
            ohUpdateDesc.setText(bundle.getString("Description"));
            ohUpdateDate.setText(bundle.getString("Date"));
            ohKey = bundle.getString("Key");
            oldOhImageUrl = bundle.getString("Image");

        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Other Helps Data").child(ohKey);

        ohUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        //update data save
        ohUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOhUpdateData();
            }
        });


    }

    public void saveOhUpdateData(){

        storageReference = FirebaseStorage.getInstance().getReference().
                child("Other Helps Data").child(ohUri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(ohEditActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(ohUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                ohImageUrl = urlImage.toString();
                uploadUpdateOhData();
                dialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    //upload Edit Data
    public void uploadUpdateOhData(){
        topic = ohUpdateTopic.getText().toString().trim();
        desc = ohUpdateDesc.getText().toString().trim();
        date = ohUpdateDate.getText().toString();

        OHelpsDataClass oHelpsDataClass = new OHelpsDataClass(topic, desc, date, ohImageUrl);

        databaseReference.setValue(oHelpsDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldOhImageUrl);
                    reference.delete();
                    Toast.makeText(ohEditActivity.this, "Data Updating", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ohEditActivity.this, "Not Updating", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ohEditActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}