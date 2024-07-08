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

public class AthBehethUploadActivity extends AppCompatActivity {

    ImageView abImage;
    Button abSaveBtn;
    EditText abTopic, abDesc, abDate;
    String abImageURL;
    Uri aburi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ath_beheth_upload);

        abImage = findViewById(R.id.aBImage);
        abTopic = findViewById(R.id.aBTopic);
        abDesc = findViewById(R.id.aBDesc);
        abDate = findViewById(R.id.abDate);
        abSaveBtn = findViewById(R.id.aBSaveButton);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            aburi = data.getData();
                            abImage.setImageURI(aburi);

                        }else{
                            Toast.makeText(AthBehethUploadActivity.this,"No any Image " +
                                    "Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        abImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });
        abSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAbData();
            }
        });

    }

    public void saveAbData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(
                "Ath Beheth Data").child(aburi.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(AthBehethUploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        storageReference.putFile(aburi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                abImageURL = urlImage.toString();
                uploadAbData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadAbData(){

        String aBTopicUp = abTopic.getText().toString();
        String aBDescUp = abDesc.getText().toString();
        String aBDateUp = abDate.getText().toString();

        AthBehethDataClass athBehethDataClass = new AthBehethDataClass(aBTopicUp, aBDescUp, aBDateUp, abImageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Ath Beheth Data").child(currentDate)
                .setValue(athBehethDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AthBehethUploadActivity.this, "Ath Beheth Data Uploaded"
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AthBehethUploadActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}