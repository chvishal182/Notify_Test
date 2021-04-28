package com.example.notification_t1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import static com.example.notification_t1.MainActivity.CHANNEL_ID;

public class Push_Receive extends com.google.firebase.messaging.FirebaseMessagingService {
    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        // playing audio and vibration when user sends request
        // uri is used to identify a resource

        Uri notification  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);// identifying the location and saving it in a URI
        Ringtone r = RingtoneManager.getRingtone(this,notification);
        r.play();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P )
        {
            r.setLooping(false);
        }

        //adding vibration to our notifications

        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100,300,300,300};
        v.vibrate(pattern,-1);

        int resourceImage = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(resourceImage);


        Intent resultIntent = new Intent(this, com.example.notification_t1.Push_Send.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }


    }
}
