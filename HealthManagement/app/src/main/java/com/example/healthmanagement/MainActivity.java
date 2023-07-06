package com.example.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;


import com.example.healthmanagement.VaccinationCertificate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //Declare variables
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.NavigationBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu);

    }

    //Callout the variable and id
    Menu1F menu1F = new Menu1F();
    Settings2F settings2F = new Settings2F();
    VaccinationCertificate profile = new VaccinationCertificate();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Switch case for navigation bar
        switch (item.getItemId()) {
            case R.id.menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, menu1F).commit();
                return true;

            case R.id.Settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, settings2F).commit();
                return true;

            case R.id.Profile:
                startActivity(new Intent(getApplicationContext(), VaccinationCertificate.class));
                return true;
        }
        return false;
    }

}