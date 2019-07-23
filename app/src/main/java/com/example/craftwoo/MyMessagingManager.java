package com.example.craftwoo;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingManager extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());




    }

    public void showNotification(String title , String msg){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "My Notifications")
                .setContentTitle(title).setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                .setAutoCancel(true).setContentText(msg);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

    }
}
