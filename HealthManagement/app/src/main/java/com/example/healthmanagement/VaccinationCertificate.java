package com.example.healthmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VaccinationCertificate extends AppCompatActivity {
    TextView lblName;
    TextView lblIC;
    TextView lblVacc1;
    TextView lblAppointmentDateTime1;
    TextView lblFacility1;
    TextView lblVacc2;
    TextView lblAppointmentDateTime2;
    TextView lblFacility2;
    Button returnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        //get all Views
        lblName = (TextView) findViewById(R.id.tvName);
        lblIC = (TextView) findViewById(R.id.tvIC);
        lblVacc1 = (TextView) findViewById(R.id.tvVaccine1);
        lblAppointmentDateTime1 = (TextView) findViewById(R.id.tvDateTime1);
        lblFacility1 = (TextView) findViewById(R.id.tvFacility1);
        lblVacc2 = (TextView) findViewById(R.id.tvVaccine2);
        lblAppointmentDateTime2 = (TextView) findViewById(R.id.tvDateTime2);
        lblFacility2 = (TextView) findViewById(R.id.tvFacility2);

        //get User's name & IC from sharedPreference
        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        lblName.setText(preferences.getString("name", ""));
        lblIC.setText(preferences.getString("userIC",""));

        //gets card layout containing completed Appointment info
        LinearLayout cardVac= findViewById(R.id.cardVac);
        LinearLayout cardVac2= findViewById(R.id.cardVac2);

        //query DB for user's Appointments
        DBController db = new DBController(this);
        ArrayList<Appointment> list = db.getAllAppointments(Integer.valueOf(preferences.getString("uid", "")));
        //arrayList to contain COMPLETED Appointments
        ArrayList<Appointment> filteredList = new ArrayList<>();

        //Following KKM's requirements, 2 doses are needed to be considered fully vaccinated.
        //No vaccination card is displayed if the user has no completed vaccination appointments
        //display if the user completes 1 or more Appointments
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getStatus().equals("COMPLETE")){
                filteredList.add(list.get(i));
            }
        }

        //displays vaccination data if any are complete.
        //hide information card if there are none.
        if(filteredList.size() == 2){
            lblVacc1.setText(filteredList.get(0).getVaccineName());
            lblAppointmentDateTime1.setText(filteredList.get(0).getAppointmentDate()+ ", " + filteredList.get(0).getAppointmentTime());
            lblFacility1.setText(filteredList.get(0).getFacilityName());

            lblVacc2.setText(filteredList.get(1).getVaccineName());
            lblAppointmentDateTime2.setText(filteredList.get(1).getAppointmentDate()+ ", " + filteredList.get(1).getAppointmentTime());
            lblFacility2.setText(filteredList.get(1).getFacilityName());
        }
        else if(filteredList.size() == 1){
            lblVacc1.setText(filteredList.get(0).getVaccineName());
            lblAppointmentDateTime1.setText(filteredList.get(0).getAppointmentDate()+ ", " + filteredList.get(0).getAppointmentTime());
            lblFacility1.setText(filteredList.get(0).getFacilityName());
            cardVac2.setVisibility(View.GONE);
        }
        else {
            cardVac.setVisibility(View.GONE);
            cardVac2.setVisibility(View.GONE);
        }


        returnHome = (Button)findViewById(R.id.homeButton);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VaccinationCertificate.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}