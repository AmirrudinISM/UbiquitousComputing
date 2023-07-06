package com.hadi.activitiesandintents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView message;
    Button btnSendMessage;
    EditText etInputMessage;

    //public static final int REQUEST_KEY = 123;
    public static final int REQUEST_REPLY = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.message);
        btnSendMessage = findViewById(R.id.sendMessage);
        etInputMessage = findViewById(R.id.input_msg);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSecondActivity();
            }
        });
    }

    void launchSecondActivity() {
        //get inut
        String input = etInputMessage.getText().toString();

        //create new activity
        Intent intent = new Intent(this, SecondActivity.class);//create intent
        intent.putExtra(SecondActivity.MESSAGE_KEY, input);//put the message in the box
        startActivityForResult(intent, REQUEST_REPLY);
        //startActivity(intent);//create new activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_REPLY) {
            String reply = data.getStringExtra(SecondActivity.REPLY_KEY);
            message.setText(reply);
        }
    }

    /*
    public void launchSecondActivity (View view)
    {
        //get inut
        String input = etInputMessage.getText().toString();

        //create new activity
        Intent intent = new Intent(this, SecondActivity.class);//create intent
        intent.putExtra(SecondActivity.MESSAGE_KEY, input);//put the message in the box
        startActivity(intent);//create new activity
    }*/
}
