package com.hadi.activitiesandintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public final static String MESSAGE_KEY = "message-key";
    public final static String REPLY_KEY = "reply-key";

    TextView message;
    Button btnSendMessage;
    EditText etInputMessage;

    @Override //to override any decision on the screen
    //handle anything happen on the screen
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get message from the first intent
        Intent intent = getIntent();
        String messageTxt = intent.getStringExtra(MESSAGE_KEY);
        message=findViewById(R.id.message);
        btnSendMessage=findViewById(R.id.sendMessage);
        etInputMessage=findViewById(R.id.input_msg);
        message.setText(messageTxt);

        btnSendMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                returnMessage();
            }
        });

        //Intent intern = new Intent(this, MainActivity.class);
    }

    void returnMessage()
    {
        String input = etInputMessage.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(REPLY_KEY, input);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
