package com.example.healthmanagement;

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

import androidx.appcompat.app.AppCompatActivity;

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

        //create a list of name of vaccination centers for the spinner.
        String[] vaccinationPoints = new String[]{"-- Please select a vaccination center --",
                "Ideal Convention Center, Shah Alam",
                "Axiata Arena, Bukit Jalil",
                "PPV World Trade Center Kuala Lumpur",
                "Soka Gakkai, Klang",
                "Kuala Lumpur Convention Center"};

        //create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccinationPoints);

        //set the spinners adapter to the previously created one.
        locationSelect.setAdapter(adapter);

        txtDateSelect = findViewById(R.id.reservationDate);
        txtDateSelect.setInputType(InputType.TYPE_NULL);

        //creates datePicker dialogue that displays date selection in the form of a calendar
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

        //create a list of times for the spinner.
        String[] vaccinationTimes = new String[]{
                "-- Please select an appointment time --",
                "09:00AM",
                "10:00AM",
                "11:00AM",
                "12:00PM",
                "03:00PM",
                "04:00PM"};

        //spinner adapter for appointment times
        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccinationTimes);

        //set the adapter for the time spinner
        timeSelect.setAdapter(adapterTime);

        submitButton = (Button) findViewById(R.id.submitAppointment);

        //displays warning messages in the form of Toast if there are no time, date, or vacciantion centers selected
        //submits data only if all fields are filled.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDateSelect.getText().toString().equals("") || locationSelect.getSelectedItemPosition() == 0 || timeSelect.getSelectedItemPosition() == 0){
                    if(txtDateSelect.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Please select an appointment date", Toast.LENGTH_LONG).show();
                    }
                    if(locationSelect.getSelectedItemPosition() == 0){
                        Toast.makeText(getApplicationContext(), "Please select a vaccination center", Toast.LENGTH_LONG).show();
                    }
                    if(timeSelect.getSelectedItemPosition() == 0){
                        Toast.makeText(getApplicationContext(), "Please select an appointment time", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    String location = locationSelect.getSelectedItem().toString();
                    String date = String.valueOf(txtDateSelect.getText());
                    String time = timeSelect.getSelectedItem().toString();
                    DBController dbHelper = new DBController(AppointmentForm.this);
                    SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
                    int uid = Integer.parseInt(preferences.getString("uid", ""));
                    dbHelper.insertAppointmentData(date,time, location, uid);
                    Intent intent = new Intent(AppointmentForm.this,VaccineReserActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}