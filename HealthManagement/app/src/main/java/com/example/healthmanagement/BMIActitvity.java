package com.example.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class BMIActitvity extends AppCompatActivity {

    //Declare variables
    TextView result,result3,resultbody;
    EditText height,weight;
    Button calculateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactitvity);

        //Callout the variable and id
        result=findViewById(R.id.resultBmi);
        resultbody=findViewById(R.id.resultBmi2);
        result3=findViewById(R.id.result2);

        calculateBtn= findViewById(R.id.CalculateBtn);

        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);


        //Remove the visibility
        result3.setVisibility(View.GONE);

        //Input validation


        //Calculate function
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(height.getText().toString().isEmpty() || weight.getText().toString().isEmpty()){
                    Toast.makeText(BMIActitvity.this, "Please insert all values", Toast.LENGTH_LONG).show();
                }
                else{
                    double weightVal = 0;
                    double heightVal = 0;
                    double res = 0;

                    weightVal = Double.valueOf(weight.getText().toString());
                    heightVal = Double.valueOf(height.getText().toString());

                    if( weightVal <= 0 || heightVal <= 0){
                        Toast.makeText(BMIActitvity.this, "Input must be more than 0", Toast.LENGTH_LONG).show();
                    }
                    else{
                        heightVal /= 100;
                        res = (weightVal/(heightVal*heightVal));


                        if(res < 18.5){

                            resultbody.setText("Underweight");

                        }
                        else if(res >= 18.5 && res < 25){

                            resultbody.setText("Normal Weight");

                        }
                        else if( res >= 25 && res < 30){

                            resultbody.setText("Overweight");

                        }
                        else if(res >= 30){

                            resultbody.setText("Obesity");

                        }
                        else{

                            resultbody.setText("Invalid Value");

                        }

                        result.setText("BMI: "+String.format("%.2f",res));
                    }
                }
            }
        });

    }
}
