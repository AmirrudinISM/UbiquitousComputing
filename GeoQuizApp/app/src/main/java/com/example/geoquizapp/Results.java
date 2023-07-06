package com.example.geoquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    private TextView nameDisp;
    private TextView scoreDisp;
    private Button home;
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        nameDisp = findViewById(R.id.finalName);
        String name = getIntent().getStringExtra("Name");
        nameDisp.setText(name+ "'s Score:");

        scoreDisp = findViewById(R.id.finalScore);
        score = getIntent().getIntExtra("Score",0);
        scoreDisp.setText(score + "/2");

        home = (Button)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Results.this, Login.class);
                startActivity(intent);
            }
        });
    }
}