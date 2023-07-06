package com.example.partikhmal;


import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE=101010;
    Button loginBtn,biometricLoginButton;
    EditText email,password;
    TextView registerNow,fpMessage;
    ImageView fingerPrintIcon;
    DBController DB;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn=findViewById(R.id.loginBtn);
        email= findViewById(R.id.emailLogin);
        password=findViewById(R.id.passwordLogin);
        fpMessage=findViewById(R.id.fpMessage);
        registerNow= findViewById(R.id.textRegister);

        fingerPrintIcon= findViewById(R.id.fpImage);

        DB = new DBController(this);


        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);


        String name = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("name","");
        String emailtext = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("email","");
        String useFP= getSharedPreferences("settings", Context.MODE_PRIVATE).getString("fp","");
        //String useFP= "false";
        email.setText(emailtext);

        if(useFP.equals("enable")){
            fpMessage.setText("Use fingerprint login for this email: "+ emailtext);

            BiometricManager biometricManager = BiometricManager.from(this);

            switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(this,"This device doesn't have biometric sensor",Toast.LENGTH_LONG).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(this,"Sensor not available",Toast.LENGTH_LONG).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                    Toast.makeText(this,"Please setup fingerprint first on your device if you want to use fingerprint function" ,Toast.LENGTH_LONG).show();
                    break;
            }

            executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(MainActivity.this,
                    executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode,
                                                  @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(),
                            "Authentication error: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(),
                            "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, dashboard.class);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric login for my app")
                    .setSubtitle("Log in using your biometric credential")
                    .setNegativeButtonText("Use account password")
                    .build();

            // Prompt appears when user clicks "Log in".
            // Consider integrating with the keystore to unlock cryptographic operations,
            // if needed by your app.

            fingerPrintIcon.setOnClickListener(view -> {
                biometricPrompt.authenticate(promptInfo);
            });

        }else{

            fingerPrintIcon.setVisibility(View.GONE);
            fpMessage.setVisibility(View.GONE);

        }





        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailString= email.getText().toString();
                String PasswordString = password.getText().toString();


                if(emailString != null && password !=null){

                    Cursor result = DB.findUser(emailString);

                    if(result.getCount()==0){
                        Toast.makeText(MainActivity.this, "User with this email doesn't exist! ", Toast.LENGTH_LONG).show();
                    }else{
                      Toast.makeText(MainActivity.this, "something" + result.getCount(), Toast.LENGTH_LONG).show();

                      while(result.moveToNext()){
                          Toast.makeText(MainActivity.this, " password" + result.getString(6), Toast.LENGTH_LONG).show();

                            if(  result.getString(6).equals(PasswordString)){

                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("uid", result.getString(0) );
                                editor.putString("name",result.getString(1) );
                                editor.putString("email",result.getString(2) );
                                editor.putString("userIC",result.getString(4));
                                editor.putString("fp", "disable");

                                editor.commit();

                                Intent intent = new Intent(MainActivity.this, enableFP.class);

                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                }
            }
        });

    }
}