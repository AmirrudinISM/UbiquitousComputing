package com.example.healthmanagement;

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

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

        //Declare variable
        loginBtn=findViewById(R.id.loginBtn);
        email= findViewById(R.id.emailLogin);
        password=findViewById(R.id.passwordLogin);
        fpMessage=findViewById(R.id.fpMessage);
        registerNow= findViewById(R.id.textRegister);
        fingerPrintIcon= findViewById(R.id.fpImage);

        //initialize db constructor
        DB = new DBController(this);

        //initialize sharedpreference
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        //get sharedpreference data
        String name = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("name","");
        String emailtext = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("email","");
        String useFP= getSharedPreferences("settings", Context.MODE_PRIVATE).getString("fp","");


        //set email to the EditText
        email.setText(emailtext);


        //check either user enable fingerprint function to use login with fingerprint sensor
        if(useFP.equals("enable")){
            fpMessage.setText("Use fingerprint login for this email: "+ emailtext);

            BiometricManager biometricManager = BiometricManager.from(this);

            //check device fingerprint sensor current status
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

            //error handler for fingerprint Authentication failed
            executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(LoginActivity.this,
                    executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode,
                                                  @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(),
                                    "Authentication error: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }

                //if fingerprint succeed toast will appeared and navigate to te dashboard(MainActivity)
                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(),
                            "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                //error handler for fingerprint sensor if fingerprint is wrong
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




        //navigate user to registration activity when registration button been clicked
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //triger registration function when user click login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get input for login credential
                String emailString= email.getText().toString();
                String PasswordString = password.getText().toString();


                //check whether user has key-in input or not

                if(emailString != null && password !=null){

                    //find user in database
                    Cursor result = DB.findUser(emailString);

                    //if return data from database is 0, show toast message
                    if(result.getCount()==0){
                        Toast.makeText(LoginActivity.this, "User with this email doesn't exist! ", Toast.LENGTH_LONG).show();
                    }else{

                        while(result.moveToNext()){

                            //check user password is same on database with input or not
                            if(  result.getString(6).equals(PasswordString)){

                                //set sharedpreference data
                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("uid", result.getString(0) );
                                editor.putString("name",result.getString(1) );
                                editor.putString("email",result.getString(2) );
                                editor.putString("userIC",result.getString(4));
                                editor.putString("fp", "disable");

                                editor.commit();

                                //start enableFP activity
                                Intent intent = new Intent(LoginActivity.this, enableFP.class);

                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                }
            }
        });

    }
}