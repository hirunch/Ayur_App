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

public class OthersHelpActivity extends AppCompatActivity {
    private FloatingActionButton fabOhBtn;
    private TextView isAdmin;
    RecyclerView recyclerViewOh;
    private DatabaseHelper ayurDatabase;
    List<OHelpsDataClass> ohDataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_others_help);
        //initialize id
        fabOhBtn = findViewById(R.id.fabOhBtn);
        isAdmin = findViewById(R.id.adminOhTitle);
        recyclerViewOh = findViewById(R.id.recycleViewOh);
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(this);
        // Creates a new GridLayoutManager with 1 column and sets it as the layout manager for recyclerViewOh
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OthersHelpActivity.this,1);
        recyclerViewOh.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(OthersHelpActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        ohDataList = new ArrayList<>();
        // Creates a new instance of OHelpsAdapter and sets it as the adapter for recyclerViewOh
        OHelpsAdapter oHelpsAdapter = new OHelpsAdapter(OthersHelpActivity.this, ohDataList);
        recyclerViewOh.setAdapter(oHelpsAdapter);
        // Retrieves a reference to the Other Helps Data node in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Other Helps Data");
        dialog.show();

        // Sets up EventListener on databaseReference to listen for data changes in Firebase Realtime Database
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ohDataList.clear();

                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    OHelpsDataClass oHelpsDataClass = itemSnapshot.getValue(OHelpsDataClass.class);
                    oHelpsDataClass.setOhKey(itemSnapshot.getKey());
                    ohDataList.add(oHelpsDataClass);
                }
                oHelpsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fabOhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OthersHelpActivity.this, OtherHelpsUploadActivity.class);
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
            fabOhBtn.show();

        }else if(isAdmin.getText().equals("0")){
            fabOhBtn.hide();
        }
    }
}