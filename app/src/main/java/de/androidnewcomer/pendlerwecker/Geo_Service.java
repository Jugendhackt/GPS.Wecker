package de.androidnewcomer.pendlerwecker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by jonas on 30.09.17.
 */

public class Geo_Service extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Notification wird erstellt
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        //Paramenter werden
                        .setContentTitle("My notification")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("Hello World!");

        //Notification wird angezeigt
        startForeground(100, mBuilder.build());



        return START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
