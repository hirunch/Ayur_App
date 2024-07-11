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

public class OsuInfoViewsActivity extends AppCompatActivity {

    TextView osuDetailTitle, osuDetailDesc, osuDetailDate;
    ImageView osuDetailImage;
    FloatingActionButton osuDeleteBtn, osuEditBtn;
    private DatabaseHelper ayurDatabase;
    FloatingActionMenu osuFabm;
    TextView isAdmin;
    String osuKey="";
    String osuImageUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_osu_info_views);
        //initialize id
        osuDetailTitle = findViewById(R.id.osuDetailTitle);
        osuDetailDesc = findViewById(R.id.osuDetailDesc);
        osuDetailImage = findViewById(R.id.osuDetailImage);
        osuDetailDate = findViewById(R.id.osuDetailDate);
        osuDeleteBtn = findViewById(R.id.osuDeleteButton);
        osuEditBtn = findViewById(R.id.osuEditButton);
        osuFabm = findViewById(R.id.osufab);
        isAdmin = findViewById(R.id.adminosu);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        // Retrieves the extras (bundle) from the intent that started this activity
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            // Sets the description, title, and date text from the bundle into respective TextViews
            osuDetailDesc.setText(bundle.getString("Description"));
            osuDetailTitle.setText(bundle.getString("Topic"));
            osuDetailDate.setText(bundle.getString("Date"));
            osuKey = bundle.getString("Key");
            osuImageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(osuDetailImage);


        }
        osuDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieves a reference to the Osu Data node in the Firebase Realtime Database
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Osu Data");
                // Retrieves an instance of FirebaseStorage and creates a StorageReference using the imageUrl
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(osuImageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(osuKey).removeValue();
                        Toast.makeText(OsuInfoViewsActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), OsuInfoActivity.class));
                        finish();

                    }
                });
            }
        });

        //osu article editing button
        osuEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an intent to start the OsuInfoEditingActivity and adds topic, description, date, image URL, and key as extras
                Intent intent = new Intent(OsuInfoViewsActivity.this, OsuInfoEditingActivity.class)
                        .putExtra("Topic", osuDetailTitle.getText().toString())
                        .putExtra("Description", osuDetailDesc.getText().toString())
                        .putExtra("Date", osuDetailDate.getText().toString())
                        .putExtra("Image", osuImageUrl)
                        .putExtra("Key", osuKey);
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
            osuFabm.showMenu(true);

        }else if(isAdmin.getText().equals("0")){
            osuFabm.hideMenu(true);
        }
    }
}