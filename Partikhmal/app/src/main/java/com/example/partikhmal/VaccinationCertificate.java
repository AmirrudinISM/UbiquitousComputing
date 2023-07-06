package com.example.partikhmal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_certificate);

        lblName = (TextView) findViewById(R.id.tvName);
        lblIC = (TextView) findViewById(R.id.tvIC);
        lblVacc1 = (TextView) findViewById(R.id.tvVaccine1);
        lblAppointmentDateTime1 = (TextView) findViewById(R.id.tvDateTime1);
        lblFacility1 = (TextView) findViewById(R.id.tvFacility1);
        lblVacc2 = (TextView) findViewById(R.id.tvVaccine2);
        lblAppointmentDateTime2 = (TextView) findViewById(R.id.tvDateTime2);
        lblFacility2 = (TextView) findViewById(R.id.tvFacility2);

        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        lblName.setText(preferences.getString("name", ""));
        lblIC.setText(preferences.getString("userIC",""));

        DBController db = new DBController(this);
        ArrayList<Appointment> list = db.getAllAppointments(Integer.valueOf(preferences.getString("uid", "")));
        ArrayList<Appointment> filteredList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getStatus().equals("COMPLETE")){
                filteredList.add(list.get(i));
            }
        }

        lblVacc1.setText(filteredList.get(0).getVaccineName());
        lblAppointmentDateTime1.setText(filteredList.get(0).getAppointmentDate()+ ", " + filteredList.get(0).getAppointmentTime());
        lblFacility1.setText(filteredList.get(0).getFacilityName());

        lblVacc2.setText(filteredList.get(1).getVaccineName());
        lblAppointmentDateTime2.setText(filteredList.get(1).getAppointmentDate()+ ", " + filteredList.get(1).getAppointmentTime());
        lblFacility2.setText(filteredList.get(1).getFacilityName());
    }
}