package com.example.week5day1firebasemessaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
//Allows for push notifications with messaging service
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    //Override onMessageReceived method to handle building the push notification
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationManager notificationManager;
        Notification.Builder notificationBuild;

        //Built pending intent to direct user to the right activity after the notification is clicked
        Log.d("TAG", "From: " + remoteMessage.getFrom());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Built the notification with a body text, title, icon and the corresponding pending intent
        //pass in a channel id if the Android version is O or newer.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuild = new Notification.Builder(this, "123")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent);
        }else{
            notificationBuild = new Notification.Builder(this)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent);
        }
        //Pass the notification service to the notification manager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("123", "channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //Notify the manager of the notification build
        notificationManager.notify(123,notificationBuild.build());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getData());

        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TAG", "on New token: " + s);
    }
}
