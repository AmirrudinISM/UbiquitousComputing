package com.example.vaccinationreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AppointmentForm extends AppCompatActivity {
    Spinner locationSelect;
    Spinner timeSelect;
    EditText txtDateSelect;
    DatePickerDialog dateSelect;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentform);

        //get the spinner from the xml.
        locationSelect = findViewById(R.id.spinner);

        //create a list of items for the spinner.
        String[] vaccinationPoints = new String[]{"KLCC", "Stadium Bukit Jalil", "PWTC"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccinationPoints);

        //set the spinners adapter to the previously created one.
        locationSelect.setAdapter(adapter);

        txtDateSelect = findViewById(R.id.reservationDate);
        txtDateSelect.setInputType(InputType.TYPE_NULL);
        txtDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dateSelect = new DatePickerDialog(AppointmentForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int inYear, int inMonth, int inDay) {
                        txtDateSelect.setText(inDay + "/" + (inMonth+1) + "/" + inYear);
                    }
                }, year, month, day);
                dateSelect.show();
            }
        });

        //get the spinner from the xml.
        timeSelect = findViewById(R.id.spinnerTime);

        //create a list of items for the spinner.
        String[] vaccinationTimes = new String[]{"09:00AM", "12:00PM", "03:00PM"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccinationTimes);

        //set the spinners adapter to the previously created one.
        timeSelect.setAdapter(adapterTime);

        submitButton = (Button) findViewById(R.id.submitAppointment);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = locationSelect.getSelectedItem().toString();
                String date = String.valueOf(txtDateSelect.getText());
                String time = timeSelect.getSelectedItem().toString();
                DBHelper dbHelper = new DBHelper(AppointmentForm.this);
                SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
                int uid = Integer.valueOf(preferences.getString("uid", ""));
                dbHelper.insertAppointmentData(date,time, location, uid);
                Intent intent = new Intent(AppointmentForm.this,AppointmentList.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}