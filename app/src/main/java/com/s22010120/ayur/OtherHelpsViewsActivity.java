package com.s22010120.ayur;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OtherHelpsViewsActivity extends AppCompatActivity {

    private TextView ohDetailTopic, ohDetailDec, ohDetailDate;
    private ImageView ohDetailImage;
    FloatingActionButton ohDeleteBtn, ohEditBtn;
    private DatabaseHelper ayurDatabase;
    FloatingActionMenu ohFabm;
    TextView isAdmin;
    String ohKey="";
    String ohImageUrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_helps_views);
        //initialize id
        ohDetailImage = findViewById(R.id.ohDetailImage);
        ohDetailTopic = findViewById(R.id.ohDetailTitle);
        ohDetailDec = findViewById(R.id.ohDetailDesc);
        ohDetailDate = findViewById(R.id.ohDetailDate);
        ohEditBtn = findViewById(R.id.ohEditButton);
        ohDeleteBtn = findViewById(R.id.ohDeleteButton);
        ohFabm = findViewById(R.id.ohFam);
        isAdmin = findViewById(R.id.adminOhfab);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        // Retrieves the extras (bundle) from the intent that started this activity
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            // Sets the description, title, and date text from the bundle into respective TextViews
            ohDetailDec.setText(bundle.getString("Description"));
            ohDetailTopic.setText(bundle.getString("Topic"));
            ohDetailDate.setText(bundle.getString("Date"));
            ohKey = bundle.getString("Key");
            ohImageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(ohDetailImage);

        }

        ohDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieves a reference to the Other Helps Data node in the Firebase Realtime Database
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Other Helps Data");
                // Retrieves an instance of FirebaseStorage and creates a StorageReference using the imageUrl
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(ohImageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(ohKey).removeValue();
                        Toast.makeText(OtherHelpsViewsActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), OthersHelpActivity.class));
                        finish();

                    }
                });
            }
        });

        //Other Helps Data edit button
        ohEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an intent to start the ohEditActivity and adds topic, description, date, image URL, and key as extras
                Intent intent = new Intent(OtherHelpsViewsActivity.this, ohEditActivity.class)
                        .putExtra("Topic", ohDetailTopic.getText().toString())
                        .putExtra("Description", ohDetailDec.getText().toString())
                        .putExtra("Date", ohDetailDate.getText().toString())
                        .putExtra("Image", ohImageUrl)
                        .putExtra("Key", ohKey);

                startActivity(intent);
            }
        });

        UisAdmin();

    }
    public void UisAdmin(){

        Cursor cursor = ayurDatabase.getUserData();

        if(cursor.moveToFirst()){
            isAdmin.setText(cursor.getString(5));

        }
        if(isAdmin.getText().equals("1")){
            ohFabm.showMenu(true);

        }else if(isAdmin.getText().equals("0")){
            ohFabm.hideMenu(true);
        }
    }
}