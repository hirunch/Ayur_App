package com.s22010120.ayur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DepartmentsMapActivity extends AppCompatActivity {

    Button depWest ;
    Button depCentral;
    Button depNorthCentral;
    Button depSouth;
    Button depSabara;
    Button depNorthWest;
    Button depUva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deparmtents_map);

        depWest = findViewById(R.id.depWest);
        depCentral = findViewById(R.id.depCentral);
        depNorthCentral = findViewById(R.id.depNorthCentral);
        depSouth = findViewById(R.id.depSouth);
        depSabara = findViewById(R.id.depSabaragamuwa);
        depNorthWest = findViewById(R.id.depNorthWest);
        depUva = findViewById(R.id.depUva);

        depWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 6.868457660823437;
                double longitude = 79.9167557819512;
                String uri = String.format(Locale.US, "geo:6.868457660823437, 79.9167557819512?q= " +
                        "Department+of+Ayurveda", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });

        depCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 7.427496083837366;
                double longitude = 80.71140279721665;
                String uri = String.format
                        (Locale.US, "geo:7.427496083837366, 80.71140279721665?q=" +
                                "Department+of+Ayurveda,+Central+Province+ආයුර්වේද+" +
                                "දෙපාර්තමේන්තුව+-+මධ්\u200Dයම+පලාත", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });

        depNorthCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 8.45029918207039;
                double longitude = 80.43125144101418;
                String uri = String.format
                        (Locale.US, "geo:8.45029918207039, 80.43125144101418?q=" +
                                "Department+of+Ayurveda+North+central+province", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });

        depSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 6.16916190421423;
                double longitude = 80.22243805382331;
                String uri = String.format
                        (Locale.US, "geo:6.16916190421423, 80.22243805382331?q=" +
                                "Department+of+Ayurveda+-+southern+province", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });


        depSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 6.16916190421423;
                double longitude = 80.22243805382331;
                String uri = String.format
                        (Locale.US, "geo:6.16916190421423, 80.22243805382331?q=" +
                                "Department+of+Ayurveda+-+southern+province", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });


        depSabara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 6.764104523217294;
                double longitude = 80.38723296923654;
                String uri = String.format
                        (Locale.US, "geo:6.764104523217294, 80.38723296923654?q=" +
                                "Ayurveda+Department+-+Sabaragamuwa", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });


        depNorthWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 7.581623495114599;
                double longitude = 80.35427398615388;
                String uri = String.format
                        (Locale.US, "geo:7.581623495114599, 80.35427398615388?q=" +
                                "Department+of+ayurveda+-+North+western+province", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });

        depUva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update with the desired location coordinates
                double latitude = 6.895004960622416;
                double longitude = 80.95302884548857;
                String uri = String.format
                        (Locale.US, "geo:6.895004960622416, 80.95302884548857?q=" +
                                "Department+of+Provincial+Ayurveda", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

            }
        });



    }

};