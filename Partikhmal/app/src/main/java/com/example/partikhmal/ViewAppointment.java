package com.example.partikhmal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

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
        String[] vaccines = new String[]{"Pfizer", "SinoVac", "Astra-Zeneca", "CansinoRoyale", "Johnson & Johnson"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccines);
        vaccineOptions.setAdapter(adapter);

        btnComplete = (Button) findViewById(R.id.btnCompleteVaccination);
        cancelLink = (TextView) findViewById(R.id.cancelHyperlink);

        if(vaccStatus.equals("Status: COMPLETE")|| vaccStatus.equals("Status: CANCELED")){
            btnComplete.setVisibility(View.GONE);
            cancelLink.setVisibility(View.GONE);
            vaccineOptions.setVisibility(View.GONE);
        }
        else{
            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selectedVaccine = vaccineOptions.getSelectedItem().toString();
                    db.updateAppointment(appointmentID,selectedVaccine,"COMPLETE");
                    Intent intent = new Intent(ViewAppointment.this, AppointmentList.class);
                    startActivity(intent);
                }
            });
            cancelLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.updateAppointment(appointmentID,"","CANCELED");
                    Intent intent = new Intent(ViewAppointment.this, AppointmentList.class);
                    startActivity(intent);
                }
            });
        }

    }
}