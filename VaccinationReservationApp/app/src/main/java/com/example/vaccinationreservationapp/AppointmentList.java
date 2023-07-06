package com.example.vaccinationreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.scalified.fab.ActionButton;

import java.util.ArrayList;

public class AppointmentList extends AppCompatActivity {

    ListView listView;
    ActionButton createAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        DBHelper db = new DBHelper(this);

        listView = findViewById(R.id.appointmentList);

        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        CustomListAdapter adapter = new CustomListAdapter(db.getAllAppointments(Integer.valueOf(preferences.getString("uid", ""))), this);
        listView.setAdapter(adapter);


        String name = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("name","");
        String emailtext = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("email","");

        createAppointment = (ActionButton) findViewById(R.id.createAppointment);
        createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this, AppointmentForm.class);
                startActivity(intent);
            }
        });

    }
}