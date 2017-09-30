package de.androidnewcomer.pendlerwecker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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



        if(Build.VERSION.SDK_INT >= 26) {
        // Für Android 8
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The id of the channel.
            String id = "my_channel_01";
// The user-visible name of the channel.
            CharSequence name = "name";
// The user-visible description of the channel.
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
// Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);

        }


        return START_NOT_STICKY;

    }

    private void gps(){


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




}



