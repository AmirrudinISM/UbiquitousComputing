package com.example.healthmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


import androidx.appcompat.app.AppCompatActivity;

public class ViewAppointment extends AppCompatActivity {

    TextView facilityName;
    TextView appointmentDate;
    TextView appointmentTime;
    TextView vaccineName;
    Spinner vaccineOptions;
    Button btnComplete;
    TextView cancelLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        DBController db = new DBController(this);

        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);

        int appointmentID = Integer.parseInt(getIntent().getStringExtra("appointmentID"));
        String vaccStatus = getIntent().getStringExtra("vaccStatus");
        Log.d("vaccStatus = ", vaccStatus);

        Cursor cursor = db.getAppointment(appointmentID);

        while (cursor.moveToNext()){
            facilityName = (TextView) findViewById(R.id.lblFacilityDetailItem);
            //gets facility from DB query
            facilityName.setText(cursor.getString(0));

            appointmentDate = (TextView) findViewById(R.id.lblDateDetailItem);
            //gets date from DB query
            appointmentDate.setText(cursor.getString(1));

            appointmentTime = (TextView) findViewById(R.id.lblTimeDetailItem);
            //gets time from DB query
            appointmentTime.setText(cursor.getString(2));

            vaccineName = (TextView) findViewById(R.id.lblVaccineDetailItem);
            //gets vaccine name
            vaccineName.setText(cursor.getString(3));
        }

        vaccineOptions = (Spinner) findViewById(R.id.spinnerVaccine);
        String[] vaccines = new String[]{"-- Please select vaccine --","Pfizer", "SinoVac", "Astra-Zeneca", "CansinoRoyale", "Johnson & Johnson"};

        //create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccines);
        vaccineOptions.setAdapter(adapter);

        //get button and clickable textview to COMPLETED and CANCEL Appointment respectively
        btnComplete = (Button) findViewById(R.id.btnCompleteVaccination);
        cancelLink = (TextView) findViewById(R.id.cancelHyperlink);

        //disables the COMPLETE button and CANCEL textView if the status of the current Appointment is COMPLETE or CANCELED
        if(vaccStatus.equals("Status: COMPLETE")|| vaccStatus.equals("Status: CANCELED")){
            btnComplete.setVisibility(View.GONE);
            cancelLink.setVisibility(View.GONE);
            vaccineOptions.setVisibility(View.GONE);
        }
        //update the currrent Appointment to COMPLETE or CANCEL
        else{
            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vaccineOptions.getSelectedItemPosition() == 0){
                        Toast.makeText(getApplicationContext(), "Please select vaccine", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String selectedVaccine = vaccineOptions.getSelectedItem().toString();
                        db.updateAppointment(appointmentID,selectedVaccine,"COMPLETE",Integer.valueOf(preferences.getString("uid", "")));
                        Intent intent = new Intent(ViewAppointment.this, VaccinationCertificate.class);
                        startActivity(intent);
                    }

                }
            });
            cancelLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.updateAppointment(appointmentID,"","CANCELED",Integer.valueOf(preferences.getString("uid", "")));
                    Intent intent = new Intent(ViewAppointment.this, VaccineReserActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}