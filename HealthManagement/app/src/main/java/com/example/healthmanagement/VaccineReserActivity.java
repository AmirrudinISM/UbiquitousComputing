package com.example.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import org.w3c.dom.Text;

public class VaccineReserActivity extends AppCompatActivity {

    ListView listView;
    ActionButton createAppointment;
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_reserv);

        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        //query the DB to get all user's appointments
        DBController db = new DBController(this);
        CustomListAdapter adapter = new CustomListAdapter(db.getAllAppointments(Integer.valueOf(preferences.getString("uid", ""))), this);


        listView = findViewById(R.id.appointmentList);
        listView.setAdapter(adapter);
        //allows the user to view & set vaccination status when tapping on one of the Appointments in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView lblID = (TextView)view.findViewById(R.id.appointmentID);
                String id = lblID.getText().toString();

                TextView lblStatus = (TextView) view.findViewById(R.id.lblStatus);
                String status = lblStatus.getText().toString();

                //passes the AppointmentID & status of the Appointment status to the ViewAppointment activity
                Intent intent = new Intent(VaccineReserActivity.this, ViewAppointment.class);
                intent.putExtra("appointmentID",id);
                intent.putExtra("vaccStatus", status);
                startActivity(intent);
            }
        });


        //gets the number of completed Appointments.
        int vaccCount = db.getNumberOfVaccinations(Integer.valueOf(preferences.getString("uid", "")));
        createAppointment = (ActionButton) findViewById(R.id.createAppointment);
        //disables the Appointment creation button when user has completed two or more Appointments
        if(vaccCount >= 2){
            createAppointment.setVisibility(View.GONE);
        }
        else{
            createAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(VaccineReserActivity.this, AppointmentForm.class);
                    startActivity(intent);
                }
            });
        }

        home = (Button) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VaccineReserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}