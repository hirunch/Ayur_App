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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OsuInfoViewsActivity extends AppCompatActivity {

    TextView osuDetailTitle, osuDetailDesc;
    ImageView osuDetailImage;
    FloatingActionButton osuDeleteBtn;
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

        osuDetailTitle = findViewById(R.id.osuDetailTitle);
        osuDetailDesc = findViewById(R.id.osuDetailDesc);
        osuDetailImage = findViewById(R.id.osuDetailImage);
        osuDeleteBtn = findViewById(R.id.osuDeleteButton);
        osuFabm = findViewById(R.id.osufab);
        isAdmin = findViewById(R.id.adminosu);

        ayurDatabase = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            osuDetailDesc.setText(bundle.getString("Description"));
            osuDetailTitle.setText(bundle.getString("Topic"));
            osuKey = bundle.getString("Key");
            osuImageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(osuDetailImage);


        }
        osuDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Osu Data");
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