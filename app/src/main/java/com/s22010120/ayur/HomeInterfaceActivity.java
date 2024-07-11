package com.s22010120.ayur;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.concurrent.Executor;

public class HomeInterfaceActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CategoriesFragment categoriesFragment = new CategoriesFragment();
    VideosFragment videosFragment = new VideosFragment();
    LocationsFragment locationsFragment = new LocationsFragment();
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_interface);
        //initialize id
        bottomNavigationView = findViewById(R.id.bottomNav);
        relativeLayout = findViewById(R.id.homeI);

        //fragments adding here

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment)
                            .commit();

                } else if (menuItem.getItemId() == R.id.categories) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, categoriesFragment)
                            .commit();

                } else if (menuItem.getItemId() == R.id.videos) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, videosFragment)
                            .commit();

                } else if (menuItem.getItemId() == R.id.locations) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, locationsFragment)
                            .commit();

                } else {
                    return false;
                }
                return true;
            }
        });

    //biometrics error finds
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Fingerprint Not in this device", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "Not Assined Fingerprint", Toast.LENGTH_SHORT).show();
                break;

        }

        //call authentication function
        showBiometricPrompt();


        }


    //user get value sharePreference and authentication validate
    private void showBiometricPrompt() {
        SharedPreferences preferences = getSharedPreferences("ShName", Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean("checkBoxKey", false);
        Executor executor = ContextCompat.getMainExecutor(this);

        if (isChecked) {
            biometricPrompt = new BiometricPrompt(HomeInterfaceActivity.this, executor,
                    new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    relativeLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    relativeLayout.setVisibility(View.INVISIBLE);
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Chamix Project")
                    .setDescription("Biometrics Authentification").setDeviceCredentialAllowed(true).build();

            biometricPrompt.authenticate(promptInfo);
        }

    }
}





