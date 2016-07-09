package com.evgenii.walktocircle.walkService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkApplication;

import java.util.Timer;
import java.util.TimerTask;

public class WalkInProgressService extends Service {
    private WalkInProgressServiceBinder mBinder = new WalkInProgressServiceBinder();

    private WalkInProgressServiceCallbacks serviceCallbacks;

    private Timer mTimer;

    @Override
    // The system calls this method when the service is first created.
    // If the service is already running, this method is not called.
    public void onCreate() {
        Log.d("ii", "WalkInProgressService onCreate");
        startCountdownTimer();
        showOngoingNotification();

        super.onCreate();
    }

    @Override
    // The system calls this method when another component, such as an activity,
    // requests that the service be started, by calling startService().
    // Can be called multiple times.
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ii", "WalkInProgressService onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        cancelCountdownTimer();
        super.onDestroy();
    }

    public void startCountdownTimer() {
        cancelCountdownTimer();

        mTimer = new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                didTimerTick();
            }

        }, 0, 5000);
    }

    private void cancelCountdownTimer() {
        if (mTimer == null) { return; }
        mTimer.cancel();
        mTimer = null;
    }

    private void didTimerTick() {
        Log.d("ii", "Service timer: " + System.currentTimeMillis());

        if (serviceCallbacks != null) {
            serviceCallbacks.didReachTheCircle();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setCallbacks(WalkInProgressServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }

    private void showOngoingNotification() {
        Context context = WalkApplication.getAppContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Walk to your circle.")
                        .setContentText("You will be notified with you reach it.")
                        .setColor(ContextCompat.getColor(context, R.color.walkColorShade50))
                        .setContentIntent(intent);

        Notification notification = mBuilder.build();

        startForeground(2, notification);
    }


    public class WalkInProgressServiceBinder extends Binder {
        public WalkInProgressService getService() {
            // Return this instance of WalkInProgressService so clients can call public methods
            return WalkInProgressService.this;
        }
    }
}
