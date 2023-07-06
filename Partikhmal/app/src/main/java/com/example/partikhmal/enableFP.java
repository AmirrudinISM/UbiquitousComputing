package com.example.partikhmal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class enableFP extends AppCompatActivity {

    Switch enableSwitch;
    Button saveBtn;
    TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_fp);

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        enableSwitch= findViewById(R.id.enableSwitch);
        saveBtn= findViewById(R.id.savebtn);
        emailText= findViewById(R.id.emailText);

        String email=  getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("email","");
        String uid=  getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("uid","");


        emailText.setText("Email"+email);


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


                Intent intent = new Intent(enableFP.this, dashboard.class);
                startActivity(intent);
            }
        });


    }
}