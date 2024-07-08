package com.s22010120.ayur;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class AbEditingActivity extends AppCompatActivity {

    ImageView abUpdateImage;
    Button abUpdateBtn;
    EditText abUpdateTopic, abUpdateDesc, abUpdateDate;
    String topic, desc, date;
    String abImageUrl;
    String abKey, abOldImageUrl;
    Uri abUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ab_editing);

        abUpdateTopic = findViewById(R.id.abUpdateTopic);
        abUpdateDesc = findViewById(R.id.abUpdateDesc);
        abUpdateDate = findViewById(R.id.abUpdateDate);
        abUpdateImage = findViewById(R.id.abUpdateImage);
        abUpdateBtn = findViewById(R.id.abUpdateButton);

        //select image get uri

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            abUri = data.getData();
                            abUpdateImage.setImageURI(abUri);
                        }else{
                            Toast.makeText(AbEditingActivity.this, "Not Selected Image", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Glide.with(AbEditingActivity.this).load(bundle.getString("Image")).into(abUpdateImage);
            abUpdateTopic.setText(bundle.getString("Topic"));
            abUpdateDesc.setText(bundle.getString("Description"));
            abUpdateDate.setText(bundle.getString("Date"));
            abKey = bundle.getString("Key");
            abOldImageUrl = bundle.getString("Image");

        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Ath Beheth Data").child(abKey);

        //image pick internal Storage
        abUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        abUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAbData();
            }
        });
    }

    //update data saving function
    public void updateAbData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Ath Beheth Data")
                .child(abUri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(AbEditingActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(abUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                abImageUrl = urlImage.toString();
                abUpdateDataUpload();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

    }

    //uploading update data
    public void abUpdateDataUpload(){
        topic = abUpdateTopic.getText().toString().trim();
        desc = abUpdateDesc.getText().toString().trim();
        date = abUpdateDate.getText().toString();

        AthBehethDataClass athBehethDataClass = new AthBehethDataClass(topic, desc, date, abImageUrl);

        databaseReference.setValue(athBehethDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().
                            getReferenceFromUrl(abOldImageUrl);
                    reference.delete();
                    Toast.makeText(AbEditingActivity.this, "Data Updating",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AbEditingActivity.this, "Data Not Updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AbEditingActivity.this, e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}