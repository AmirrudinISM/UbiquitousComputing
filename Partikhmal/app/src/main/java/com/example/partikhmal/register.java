package com.example.partikhmal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText name,email,dob,ic,phone,password1,password2;
    Button registerBtn;

    DBController DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name= findViewById(R.id.nameInput);
        email= findViewById(R.id.emailInput);
        dob= findViewById(R.id.dobInput);
        ic= findViewById(R.id.icInput);
        phone= findViewById(R.id.phoneInput);
        password1= findViewById(R.id.pass1Input);
        password2= findViewById(R.id.pass2Input);
        registerBtn= findViewById(R.id.registerBtn);

        DB = new DBController(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Convert input to string

                String nameString= name.getText().toString();
                String emailString= email.getText().toString();
                String dobString= dob.getText().toString();
                String icString= ic.getText().toString();
                String phoneString= phone.getText().toString();
                String pass1String= password1.getText().toString();
                String pass2String= password2.getText().toString();


                Log.d("name=", nameString);
                Log.d("emailString=", emailString);
                Log.d("dobString=", dobString);
                Log.d("icString=", icString);
                Log.d("phoneString=", phoneString);
                Log.d("pass1String=", pass1String);
                Log.d("pass2String=", pass2String);

                if(pass1String.isEmpty() && pass2String.isEmpty()){

                    Toast.makeText(register.this, "Please enter password for your account", Toast.LENGTH_LONG).show();

                }else{

                    if(pass1String.equals(pass2String)){

                        Long checkCreateUser= DB.createUser(nameString,emailString,dobString,icString,phoneString,pass1String);

                        if(checkCreateUser == -1){

                            Toast.makeText(register.this, "ERROR!! USER CANNOT BE REGISTER!", Toast.LENGTH_LONG).show();

                        }else if(checkCreateUser == -2){

                            Toast.makeText(register.this, "User with this email already exists!", Toast.LENGTH_LONG).show();

                        }else{
                            //go to new page

                            Intent intent = new Intent(register.this, MainActivity.class);
                            startActivity(intent);

                        }

                    }else{

                        Toast.makeText(register.this, "Password not same!!", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

    }
}