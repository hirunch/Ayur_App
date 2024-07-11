package com.s22010120.ayur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class BangalowsActivity extends AppCompatActivity {

    Button haldummullaDetails;
    Button haldumullaLocation;
    Button pattipolaDetails;
    Button pattipolaLocation;
    Button kataragamaBngDetails;
    Button kataragamaBngLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bangalows);
        //initialize id
        haldummullaDetails = findViewById(R.id.haldummullaDetalis);
        haldumullaLocation = findViewById(R.id.haldummullaLocation);

        haldummullaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ayurveda.gov.lk/haldummulla-ayurvedic-circuit-bangalow/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

        haldumullaLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double latitude = 6.792024216055955;
                double longitude = 80.89508892128252;

                String uri = String.format
                        (Locale.US, "geo:6.792024216055955, 80.89508892128252 ?q=" +
                                "Haldummulla+Ayurveda+Herbal+Garden", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);
            }
        });

        pattipolaDetails = findViewById(R.id.pattipolaDetalis);
        pattipolaLocation = findViewById(R.id.pattipolaLocation);


        pattipolaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ayurveda.gov.lk/pattipola-ayurvedic-circuit-bangalow/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

        pattipolaLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double latitude = 6.863810876202861;
                double longitude = 80.82940041037835;

                String uri = String.format
                        (Locale.US, "geo:6.863810876202861, 80.82940041037835 ?q=" +
                                "Ministry+of+Indigenous+Medicine+Circuit+Bungalow", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);
            }
        });

        kataragamaBngDetails = findViewById(R.id.kataragamaBngDetalis);
        kataragamaBngLocation = findViewById(R.id.kataragamaBngLocation);



        kataragamaBngDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ayurveda.gov.lk/katharagama-ayurvedic-circuit-bangalow/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        kataragamaBngLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double latitude = 6.413269692499609;
                double longitude = 81.33013848465744;

                String uri = String.format
                        (Locale.US, "geo:6.413269692499609, 81.33013848465744 ?q=" +
                                "Uva+provincial+council+ayurvedic+circuit+bungalow", latitude, longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);
            }
        });

    }
}