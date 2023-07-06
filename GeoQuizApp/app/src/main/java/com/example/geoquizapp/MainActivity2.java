package com.example.geoquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private Button nsw;
    private Button sa;
    private Button vic;
    private Button nt;
    private TextView status;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        status = (TextView) findViewById(R.id.statusDisplay2);
        String name = getIntent().getStringExtra("Name");
        score = getIntent().getIntExtra("Score",0);
        status.setText("USER: " + name + ", SCORE: " + score);

        nsw = (Button) findViewById(R.id.nsw);
        sa = (Button) findViewById(R.id.sa);
        vic = (Button) findViewById(R.id.vic);
        nt = (Button) findViewById(R.id.nt);

        nsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score++;
                Toast.makeText(MainActivity2.this,
                        R.string.correct_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity2.this, Results.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity2.this,
                        R.string.incorrect_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity2.this, Results.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

        vic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity2.this,
                        R.string.incorrect_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity2.this, Results.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

        nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity2.this,
                        R.string.incorrect_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity2.this, Results.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

    }
}