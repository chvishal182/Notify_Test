package com.example.notification_t1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;

public class MainActivity_FirstDisplay extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__first_display);

        EditText title = findViewById(R.id.edTxtTitle);
        EditText body  = findViewById(R.id.edtTxtBody);
        EditText userToken  = findViewById(R.id.edTxtToken);

        Button send =  findViewById(R.id.btnSend);
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity_FirstDisplay.this, "Hey there", Toast.LENGTH_SHORT).show();
//            }
//        });


        // sending notification to every user
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!title.getText().toString().isEmpty()&&!body.getText().toString().isEmpty())
                {
                    fcmNotificationSender notificationSender = new fcmNotificationSender("/topics/all",title.getText().toString(),body.getText().toString(),getApplicationContext(),MainActivity_FirstDisplay.this);
                    notificationSender.SendNotifications();
                }

            }
        });
    }


}