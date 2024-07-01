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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OtherHelpsViewsActivity extends AppCompatActivity {

    private TextView ohDetailTopic, ohDetailDec;
    private ImageView ohDetailImage;
    FloatingActionButton ohDeleteBtn;
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

        ohDetailImage = findViewById(R.id.ohDetailImage);
        ohDetailTopic = findViewById(R.id.ohDetailTitle);
        ohDetailDec = findViewById(R.id.ohDetailDesc);
        ohDeleteBtn = findViewById(R.id.ohDeleteButton);
        ohFabm = findViewById(R.id.ohFam);
        isAdmin = findViewById(R.id.adminOhfab);

        ayurDatabase = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            ohDetailDec.setText(bundle.getString("Description"));
            ohDetailTopic.setText(bundle.getString("Topic"));
            ohKey = bundle.getString("Key");
            ohImageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(ohDetailImage);

        }

        ohDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Other Helps Data");
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