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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AbViewsActivity extends AppCompatActivity {

    TextView abDetailTitle, abDetailDesc;
    ImageView abDetailImage;
    FloatingActionButton abDeleteBtn;
    private DatabaseHelper ayurDatabase;
    FloatingActionMenu abFabm;
    TextView isAdmin;
    String abKey = "";
    String abImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ab_views);

        abDetailTitle = findViewById(R.id.aBDetailTitle);
        abDetailDesc = findViewById(R.id.aBDetailDesc);
        abDetailImage = findViewById(R.id.aBDetailImage);
        abDeleteBtn = findViewById(R.id.abDeleteButton);
        isAdmin = findViewById(R.id.adminab);
        abFabm = findViewById(R.id.fababAdmin);

        ayurDatabase = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {
            abDetailDesc.setText(bundle.getString("Description"));
            abDetailTitle.setText(bundle.getString("Topic"));
            abKey = bundle.getString("Key");
            abImageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(abDetailImage);

        }
        abDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ath Beheth Data");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(abImageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(abKey).removeValue();
                        Toast.makeText(AbViewsActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AthBethethActivity.class));
                        finish();
                    }
                });
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
            abFabm.showMenu(true);

        } else if (isAdmin.getText().equals("0")) {
            abFabm.hideMenu(true);
        }
    }
}