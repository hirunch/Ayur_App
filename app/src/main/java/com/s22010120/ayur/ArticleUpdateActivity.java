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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import java.text.DateFormat;
import java.util.Calendar;

public class ArticleUpdateActivity extends AppCompatActivity {

    ImageView updateArImage;
    Button updateArBtn;
    EditText updateArTitle, updateArDesc;
    String topic, desc, arImageUrl, key, oldArImageUrl;
    Uri arUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_update);

        updateArImage = findViewById(R.id.articleUpdateImage);
        updateArTitle = findViewById(R.id.articleUpdateTopic);
        updateArDesc = findViewById(R.id.articleUpdateDesc);
        updateArBtn =  findViewById(R.id.saveUpdateButton);

        Fragment fragment = new HomeFragment();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            arUri = data.getData();
                            updateArImage.setImageURI(arUri);
                        }else{
                            Toast.makeText(ArticleUpdateActivity.this, "Not Any Image Selected",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Glide.with(this).load(bundle.getString("Image")).into(updateArImage);
            updateArDesc.setText(bundle.getString("Description"));
            updateArTitle.setText(bundle.getString("Topic"));
            key = bundle.getString("Key");
            oldArImageUrl = bundle.getString("Image");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Ayurvedha Articles").child(key);

        updateArImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK);
                photoPick.setType("image/*");
                activityResultLauncher.launch(photoPick);
            }
        });

        updateArBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArData();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.arMain,fragment).commit();
                finish();
            }
        });
    }

    public void updateArData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Ayurvedha " +
                "Articles").child(arUri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(ArticleUpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(arUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                arImageUrl = urlImage.toString();
                uploadUpdateArticle();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadUpdateArticle(){

        topic = updateArTitle.getText().toString().trim();
        desc = updateArDesc.getText().toString().trim();

        DataClass dataClass = new DataClass(topic, desc, arImageUrl);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReference(oldArImageUrl);
                    reference.delete();
                    Toast.makeText(ArticleUpdateActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ArticleUpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}