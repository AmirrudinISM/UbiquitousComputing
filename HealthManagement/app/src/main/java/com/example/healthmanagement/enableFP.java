package com.example.healthmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class enableFP extends AppCompatActivity {

    Switch enableSwitch;
    Button saveBtn;
    TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_fp);

        //initialize Sharedpreference
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //assign widget to the variable
        enableSwitch= findViewById(R.id.enableSwitch);
        saveBtn= findViewById(R.id.savebtn);
        emailText= findViewById(R.id.emailText);


        //set sharedpreference to the variable
        String email=  getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("email","");
        String uid=  getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("uid","");

        // set email  from variable to the textView
        emailText.setText(email);


        //onclick listener to trigger a function to save fingerprint setting to the sharedpreference
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(enableSwitch.isChecked()){
                    editor.putString("fp", "enable");
                    editor.commit();
                }else{
                    editor.putString("fp", "disable");
                    editor.commit();
                }
                String fp=  getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("fp","");

                //navigate to main dashboard
                Intent intent = new Intent(enableFP.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}