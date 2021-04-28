package com.example.notification_t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID  = "CVISHALDEV";
    private  static final String CHANNEL_NAME = "Vishal";
    private  static final String CHANNEL_DES = "cvishal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
       {
           NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
           channel.setDescription(CHANNEL_DES);
             NotificationManager manager = getSystemService(NotificationManager.class);
             manager.createNotificationChannel(channel);
       }

       findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               displayNotification();
           }
       });


    }
    void displayNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("NOTIFY")
                .setContentText("It is working")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager =  NotificationManagerCompat.from(this);
        manager.notify(1,mBuilder.build());
    }

}