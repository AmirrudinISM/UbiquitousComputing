package com.example.academio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText txtUsername;
    private EditText txtPassword;
    private Switch rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        rememberMe = (Switch) findViewById(R.id.rememberMe);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        SharedPreferences preferences = getSharedPreferences("switch", MODE_PRIVATE);
        String switchStatus = preferences.getString("remember", "");

        if(switchStatus.equals("true")){
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("switch", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String userName = txtUsername.getText().toString();
                editor.putString("userName", userName);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = getSharedPreferences("switch", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if(compoundButton.isChecked()){
                    editor.putString("remember", "true");
                }
                else{
                    editor.putString("remember", "false");
                }
                editor.apply();
            }
        });
    }
}