package com.s22010120.ayur;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private DatabaseHelper ayurDatabase;
    private FloatingActionButton fab ;
    private TextView isAdmin ;
    private ImageView user;
    List<ArticleDataClass> dataList;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //initialize id
        recyclerView = rootView.findViewById(R.id.recycleView);
        user = rootView.findViewById(R.id.user);
        fab = rootView.findViewById(R.id.fabButton);
        isAdmin = rootView.findViewById(R.id.adminTitle);

        Context appContext = requireActivity().getApplicationContext();
        //call databaseHelper
        ayurDatabase = new DatabaseHelper(appContext);
        // Creates a new GridLayoutManager with 1 column and sets it as the layout manager for recyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.loading_progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        // Creates a new instance of ArticleAdapter and sets it as the adapter for recyclerView
        ArticleAdapter adapter = new ArticleAdapter(requireActivity(), dataList);
        recyclerView.setAdapter(adapter);
        // Retrieves a reference to the Ayurvedha Articles node in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Ayurvedha Articles");
        dialog.show();

        // Sets up EventListener on databaseReference to listen for data changes in Firebase Realtime Database
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                // Iterates through each child snapshot to retrieve ArticleDataClass objects
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ArticleDataClass articleDataClass = itemSnapshot.getValue(ArticleDataClass.class);
                    articleDataClass.setKey(itemSnapshot.getKey());
                    dataList.add(articleDataClass);
                }

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //handles for errors

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), UserActivity.class);
                startActivity(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ArticleUploadActivity.class);
                startActivity(intent);
            }
        });


        UisAdmin();


        return rootView;
    }

    public void UisAdmin(){
        Cursor cursor = ayurDatabase.getUserData();

        if(cursor.moveToFirst()){
            isAdmin.setText(cursor.getString(5));

        }
        if(isAdmin.getText().equals("1")){
            fab.show();

        }else if(isAdmin.getText().equals("0")){
            fab.hide();
        }


    }


}
