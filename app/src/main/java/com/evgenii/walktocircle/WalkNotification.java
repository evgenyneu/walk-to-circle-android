package com.evgenii.walktocircle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class WalkNotification {

    public int mId = 1;

    void sendNotification(String title, String contentText) {

        if (WalkApplication.isActive()) {
            // Do not show notification if the app is in the foreground.
            return;
        }

        Context context = WalkApplication.getAppContext();

        Uri soundUri = Uri.parse("android.resource://" + WalkApplication.getAppContext().getPackageName() + "/" + R.raw.notification_morning_fresh);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setColor(ContextCompat.getColor(context, R.color.notificationBackgroundColor))
                        .setSound(soundUri)
                        .setVibrate(new long[]{
                                0, 100,
                                100, 200,
                                100, 200,
                                100, 200});


        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        mBuilder.setContentIntent(intent);

        mBuilder.setPriority(Notification.PRIORITY_MAX);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
