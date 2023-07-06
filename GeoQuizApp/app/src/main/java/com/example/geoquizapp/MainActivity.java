package com.example.geoquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView status;
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = (TextView) findViewById(R.id.statusDisplay);
        String name = getIntent().getStringExtra("Name");
        status.setText("USER: " + name + ", SCORE: " + score);


        mTrueButton = (Button)findViewById(R.id.btn_true);
        mFalseButton= (Button)findViewById(R.id.btn_false);

        mTrueButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                score++;
                Toast.makeText(MainActivity.this,
                        R.string.correct_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        R.string.incorrect_toast, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Name",name);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });
    }
}