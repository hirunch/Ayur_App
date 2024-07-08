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

public class OsuInfoUploadActivity extends AppCompatActivity {

    ImageView osuImage;
    Button osuSaveBtn;
    EditText osuTopic, osuDesc, osuDate;
    String osuImageURL;
    Uri osuuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_osu_info_upload);

        osuImage = findViewById(R.id.osuImage);
        osuTopic = findViewById(R.id.osuTopic);
        osuDesc = findViewById(R.id.osuDesc);
        osuDate = findViewById(R.id.osuDate);
        osuSaveBtn = findViewById(R.id.osuSaveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            osuuri = data.getData();
                            osuImage.setImageURI(osuuri);

                        }else{
                            Toast.makeText(OsuInfoUploadActivity.this,"No any Image " +
                                    "Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        osuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        osuSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOsuData();
            }
        });

    }

    public void saveOsuData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(
                "Osu Data").child(osuuri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(OsuInfoUploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        storageReference.putFile(osuuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                osuImageURL = urlImage.toString();
                uploadOsuData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadOsuData(){
        String osuTopicUp = osuTopic.getText().toString();
        String osuDescUp = osuDesc.getText().toString();
        String osuDateUp = osuDate.getText().toString();

        OsuInfoDataClass osuInfoDataClass = new OsuInfoDataClass(osuTopicUp, osuDescUp,osuDateUp, osuImageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Osu Data").child(currentDate)
                .setValue(osuInfoDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OsuInfoUploadActivity.this, "Osu Data Uploaded"
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OsuInfoUploadActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}