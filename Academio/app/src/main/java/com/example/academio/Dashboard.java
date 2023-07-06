package com.example.academio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Dashboard extends AppCompatActivity {
    ListView listView;
    Button btnLogout;
    Button btnSettings;
    TextView lblUserName;
    ArrayList<String> credentialProgramArray = new ArrayList<String>(Arrays.asList("Diploma in Information Technology", "Bachelor of IT (Hons) in Software Engineering", "Master in Computer Science"));
    ArrayList<String> credentialGraduationYearArray = new ArrayList<String>(Arrays.asList("2012","2016","2018"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences preferences = getSharedPreferences("switch", MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        lblUserName = (TextView) findViewById(R.id.lblUsername);
        lblUserName.append(userName);

        listView = findViewById(R.id.credentialListView);
        CustomListAdapter adapter = new CustomListAdapter(credentialProgramArray,credentialGraduationYearArray,this);
        listView.setAdapter(adapter);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("switch", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}