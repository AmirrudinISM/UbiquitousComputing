package com.example.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BulletinActivity extends AppCompatActivity {

    //Declare variables
    private Button readbtn1,readbtn2,readbtn3,readbtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);



        //Callout the variable and id
        readbtn1 = (Button) findViewById(R.id.readbtn1);
        readbtn2 = (Button) findViewById(R.id.readbtn2);
        readbtn3 = (Button) findViewById(R.id.readbtn3);
        readbtn4 = (Button) findViewById(R.id.readbtn4);

        //Onclick function to the arcticle
        readbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIndivBullArtivity();
            }
        });
        readbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIndivBullArtivity();
            }
        });
        readbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIndivBullArtivity();
            }
        });
        readbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIndivBullArtivity();
            }
        });

    }

    //Start arcticle activity
    public void openIndivBullArtivity(){
        Intent intent = new Intent(this, IndivBullArtivity.class);
        startActivity(intent);
    }
}