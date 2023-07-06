package com.example.healthmanagement;

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

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,dob,ic,phone,password1,password2;
    Button registerBtn;

    DBController DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //assign variable
        name= findViewById(R.id.nameInput);
        email= findViewById(R.id.emailInput);
        dob= findViewById(R.id.dobInput);
        ic= findViewById(R.id.icInput);
        phone= findViewById(R.id.phoneInput);
        password1= findViewById(R.id.pass1Input);
        password2= findViewById(R.id.pass2Input);
        registerBtn= findViewById(R.id.registerBtn);

        //initallize database constructor
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

                //input validation
                boolean inputEmpty= true;
                if(nameString.isEmpty()){
                    inputEmpty= true;

                }else{
                    inputEmpty= false;
                }

                if(emailString.isEmpty()){
                    inputEmpty= true;

                }else{
                    inputEmpty= false;
                }

                if(dobString.isEmpty()){
                    inputEmpty= true;

                }else{
                    inputEmpty= false;
                }

                if(icString.isEmpty()){
                    inputEmpty= true;

                }else{
                    inputEmpty= false;
                }

                if(pass1String.isEmpty() && pass2String.isEmpty()){
                    inputEmpty= true;

                }else{
                    inputEmpty= false;
                }
                //if all input is entered , check password is same or not. if same register user
                if(inputEmpty == false){

                    if(pass1String.equals(pass2String)){

                        //create user in database
                        Long checkCreateUser= DB.createUser(nameString,emailString,dobString,icString,phoneString,pass1String);

                        //check return value from DB.create user to identify whether user  exist or not
                        if(checkCreateUser == -1){

                            Toast.makeText(RegisterActivity.this, "ERROR!! USER CANNOT BE REGISTER!", Toast.LENGTH_LONG).show();

                        }else if(checkCreateUser == -2){

                            Toast.makeText(RegisterActivity.this, "User with this email already exists!", Toast.LENGTH_LONG).show();

                        }else{
                            //prompt user to login first
                            Toast.makeText(RegisterActivity.this, "Please Login first", Toast.LENGTH_LONG).show();

                            //start login activity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }

                    }else{

                        //alert user that been entered is not same
                        Toast.makeText(RegisterActivity.this, "Password not same!!", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this,"Please fill up the form", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}