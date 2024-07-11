package com.s22010120.ayur;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class OsuInfoActivity extends AppCompatActivity {

    FloatingActionButton fabOsuAddBtn;
    private DatabaseHelper ayurDatabase;
    private TextView isAdmin;
    RecyclerView recyclerViewOsu;
    List<OsuInfoDataClass> osuInfoDataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_osu_info);
        //initialize id
        fabOsuAddBtn = findViewById(R.id.fabOsuBtn);
        isAdmin = findViewById(R.id.adminOsuTitle);
        recyclerViewOsu = findViewById(R.id.recycleViewOsu);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        // Creates a new GridLayoutManager with 1 column and sets it as the layout manager for recyclerViewOsu
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OsuInfoActivity.this, 1);
        recyclerViewOsu.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(OsuInfoActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        osuInfoDataList = new ArrayList<>();
        // Creates a new instance of osuInfoAdapter and sets it as the adapter for recyclerViewOsu
        OsuInfoAdapter osuInfoAdapter = new OsuInfoAdapter(OsuInfoActivity.this, osuInfoDataList);
        recyclerViewOsu.setAdapter(osuInfoAdapter);
        // Retrieves a reference to the Osu Data node in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Osu Data");
        dialog.show();

        // Sets up EventListener on databaseReference to listen for data changes in Firebase Realtime Database
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                osuInfoDataList.clear();

                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    OsuInfoDataClass osuInfoDataClass = itemSnapshot.getValue(OsuInfoDataClass.class);
                    osuInfoDataClass.setOsuKey(itemSnapshot.getKey());
                    osuInfoDataList.add(osuInfoDataClass);
                }
                osuInfoAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //handles for errors
            }
        });


        fabOsuAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OsuInfoActivity.this, OsuInfoUploadActivity.class);
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
            fabOsuAddBtn.show();

        }else if(isAdmin.getText().equals("0")){
            fabOsuAddBtn.hide();
        }
    }
}