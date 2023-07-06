package com.example.partikhmal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dashboard extends AppCompatActivity {
    Button viewAppointments;
    Button healthFacilities;
    Button viewCert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewAppointments = (Button) findViewById(R.id.btnAppointment);
        viewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, AppointmentList.class);
                startActivity(intent);
            }
        });

        healthFacilities = (Button) findViewById(R.id.button4);
        healthFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, HealthFacilities.class);
                startActivity(intent);
            }
        });

        DBController db = new DBController(this);
        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        int vaccCount = db.getNumberOfVaccinations(Integer.valueOf(preferences.getString("uid", "")));

        viewCert = (Button) findViewById(R.id.button5);
        if (vaccCount < 2){
            viewCert.setVisibility(View.GONE);
        }
        viewCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, VaccinationCertificate.class);
                startActivity(intent);
            }
        });
    }
}