package com.example.partikhmal;

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

public class AppointmentList extends AppCompatActivity {

    ListView listView;
    ActionButton createAppointment;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        DBController db = new DBController(this);

        listView = findViewById(R.id.appointmentList);
        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        CustomListAdapter adapter = new CustomListAdapter(db.getAllAppointments(Integer.valueOf(preferences.getString("uid", ""))), this);
        int vaccCount = db.getNumberOfVaccinations(Integer.valueOf(preferences.getString("uid", "")));
        Toast.makeText(AppointmentList.this, "vaccCount = " + vaccCount, Toast.LENGTH_SHORT).show();



        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView lblID = (TextView)view.findViewById(R.id.appointmentID);
                String id = lblID.getText().toString();

                TextView lblStatus = (TextView) view.findViewById(R.id.lblStatus);
                String status = lblStatus.getText().toString();

                Intent intent = new Intent(AppointmentList.this, ViewAppointment.class);
                intent.putExtra("appointmentID",id);
                intent.putExtra("vaccStatus", status);
                startActivity(intent);
            }
        });


        createAppointment = (ActionButton) findViewById(R.id.createAppointment);
        if(vaccCount >= 2){
            createAppointment.setVisibility(View.GONE);
        }
        else{
            createAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AppointmentList.this, AppointmentForm.class);
                    startActivity(intent);
                }
            });
        }

        home = (Button) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this, dashboard.class);
                startActivity(intent);
            }
        });

    }
}