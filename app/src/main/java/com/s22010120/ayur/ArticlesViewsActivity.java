package com.s22010120.ayur;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ArticlesViewsActivity extends AppCompatActivity {

    TextView detailTitle, detailDesc;
    ImageView detailImage;
    FloatingActionButton arDeleteBtn, arEditBtn;
    private DatabaseHelper ayurDatabase;
    FloatingActionMenu arFabm;
    TextView isAdmin;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articles_views);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        arDeleteBtn = findViewById(R.id.arDeleteButton);
        arFabm = findViewById(R.id.fabarAdmin);
        isAdmin = findViewById(R.id.adminar);
        arEditBtn = findViewById(R.id.arEditButton);

        ayurDatabase = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();

        Fragment fragment = new HomeFragment();



        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Topic"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }

        arDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ayurvedha Articles");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(ArticlesViewsActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), HomeInterfaceActivity.class));
                        arDeleteBtn.setVisibility(View.GONE);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.arMain,fragment).commit();
                        finish();
                    }
                });
            }
        });

        arEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticlesViewsActivity.this, ArticleUpdateActivity.class)
                        .putExtra("Topic", detailTitle.getText().toString())
                        .putExtra("Description", detailDesc.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });


        UisAdmin();

    }
    public void UisAdmin() {

        Cursor cursor = ayurDatabase.getUserData();

        if (cursor.moveToFirst()) {
            isAdmin.setText(cursor.getString(5));

        }
        if (isAdmin.getText().equals("1")) {
            arFabm.showMenu(true);

        } else if (isAdmin.getText().equals("0")) {
            arFabm.hideMenu(true);
        }
    }
}