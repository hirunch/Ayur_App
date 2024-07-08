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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class OtherHelpsUploadActivity extends AppCompatActivity {

    ImageView otherHImage;
    Button otherHSaveBtn;
    EditText otherHTopic, otherHDesc, otherHDate;
    String otherHImageURL;
    Uri otherHuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_helps_upload);

        otherHImage = findViewById(R.id.otherHImage);
        otherHTopic = findViewById(R.id.otherHTopic);
        otherHDesc = findViewById(R.id.otherHDesc);
        otherHDate = findViewById(R.id.ohDate);
        otherHSaveBtn = findViewById(R.id.otherHSaveButton);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            otherHuri = data.getData();
                            otherHImage.setImageURI(otherHuri);

                        }else{
                            Toast.makeText(OtherHelpsUploadActivity.this,"No any Image " +
                                    "Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        otherHImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        otherHSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOtherHelpsData();
            }
        });



    }
    public void saveOtherHelpsData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(
                "Other Helps Data").child(otherHuri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(OtherHelpsUploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        storageReference.putFile(otherHuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                otherHImageURL = urlImage.toString();
                uploadOtherHData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadOtherHData(){

        String ohTopicUp = otherHTopic.getText().toString();
        String ohDescUp = otherHDesc.getText().toString();
        String ohDateUp = otherHDate.getText().toString();

        OHelpsDataClass oHelpsDataClass = new OHelpsDataClass(ohTopicUp, ohDescUp, ohDateUp, otherHImageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Other Helps Data").child(currentDate)
                .setValue(oHelpsDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OtherHelpsUploadActivity.this, "Other Helps Data Uploaded"
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OtherHelpsUploadActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}