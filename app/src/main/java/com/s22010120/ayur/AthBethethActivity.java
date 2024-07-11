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

public class AthBethethActivity extends AppCompatActivity {

    FloatingActionButton fabAthBehethAddBtn;
    private DatabaseHelper ayurDatabase;
    private TextView isAdmin;
    RecyclerView recyclerViewAb;
    List<AthBehethDataClass> abDataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ath_betheth);
        //initialize id
        fabAthBehethAddBtn = findViewById(R.id.fabathbehethBtn);
        isAdmin = findViewById(R.id.adminAthbehethTitle);
        recyclerViewAb = findViewById(R.id.recycleViewAthbeheth);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        // Creates a new GridLayoutManager with 1 column and sets it as the layout manager for recyclerViewAb
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AthBethethActivity.this,1);
        recyclerViewAb.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(AthBethethActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        abDataList = new ArrayList<>();
        // Creates a new instance of AbDataAdapter and sets it as the adapter for recyclerViewAb
        AbDataAdapter abAdapter = new AbDataAdapter(AthBethethActivity.this, abDataList);
        recyclerViewAb.setAdapter(abAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Ath Beheth Data");
        dialog.show();

        // Sets up a ValueEventListener on databaseReference to listen for data changes in Firebase Realtime Database
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                abDataList.clear();
                // Iterates through each child snapshot to retrieve AthBehethDataClass objects
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    AthBehethDataClass athBehethDataClass = itemSnapshot.getValue(AthBehethDataClass.class);
                    athBehethDataClass.setAbKey(itemSnapshot.getKey());
                    abDataList.add(athBehethDataClass);
                }
                // Notifies the adapter that the data set has changed
                abAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handles any errors that occur during data retrieval

            }
        });

        fabAthBehethAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AthBethethActivity.this, AthBehethUploadActivity.class);
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
            fabAthBehethAddBtn.show();

        }else if(isAdmin.getText().equals("0")){
            fabAthBehethAddBtn.hide();
        }
    }
}