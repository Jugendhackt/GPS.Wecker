package de.androidnewcomer.pendlerwecker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by jonas on 30.09.17.
 */

public class Geo_Service extends Service {


    private NotificationManager mNotificationManager;
    String id = "my_channel_01";
    Boolean updatesEnable = true;

    public static boolean isGeoActive = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Notification wird erstellt


        if(intent.getAction() != null && intent.getAction().equals("stop")){

            stopSelf();
            return START_NOT_STICKY;
        }
        isGeoActive = true;

        final Double positionLatitude = intent.getDoubleExtra("lat", 50);
        final Double positionLongitude = intent.getDoubleExtra("lon", 20);

        Log.d("lat", String.valueOf(positionLatitude));

        Log.d("lon", String.valueOf(positionLongitude));
        final Double distance = intent.getDoubleExtra("meter", 50000);


        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= 26){
            // Für Android 8


            // The id of the channel.

            // The user-visible name of the channel.
            CharSequence name = "Pendler Wecker";
            // The user-visible description of the channel.
            String description = "Alarm ist aktiviert";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            //mChannel.enableVibration(true);
            //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);

        }

        //Notification wird angezeigt
        startForeground(100, notification(-1.00).build());







        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();


                //Log.d("location", String.valueOf("Latitude: " + latitude + "Longitude: " + longitude));

                Location loc1 = new Location("");
                loc1.setLatitude(latitude);
                loc1.setLongitude(longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(positionLatitude);
                loc2.setLongitude(positionLongitude);



                double distanceInMeters = loc1.distanceTo(loc2);

                Log.d("Meter: ", String.valueOf(distanceInMeters));


                //Notification wird angezeigt
               // startForeground(100, notification(distanceInMeters).build());


                mNotificationManager.notify(
                        100,
                        notification(distanceInMeters).build());




                if(distanceInMeters <= distance && updatesEnable){


                    updatesEnable = false;
                    Intent intent= new Intent(getBaseContext(),AlarmActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                    stopSelf();

                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };



        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED ){
          //  Log.d("Log", "True");

            
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, locationListener);


        }






        return START_NOT_STICKY;

    }

    private void gps(){


    }
    @Override
    public void onDestroy(){

        Log.d("onDestroy","Destroy");
    isGeoActive = false;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private NotificationCompat.Builder notification(Double entfernung){


        Intent intent2 = new Intent(getBaseContext(), Geo_Service.class);

        intent2.setAction("stop");



        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, intent2, 0);

        NotificationCompat.Builder mBuilder;

        if(entfernung == -1.00) {
            mBuilder =
                    new NotificationCompat.Builder(getBaseContext(), id)
                            //Paramenter werden
                            .setContentTitle("Alarm ist aktiviert")
                            .setSmallIcon(R.mipmap.ic_notification)
                            .addAction(R.mipmap.ic_launcher, "Deaktivieren", pendingIntent)
                            .setContentText("Entfernung zum Ziel: ?");

        }else {

            mBuilder =
                    new NotificationCompat.Builder(getBaseContext(), id)
                            //Paramenter werden
                            .setContentTitle("Alarm ist aktiviert")
                            .setSmallIcon(R.mipmap.ic_notification)
                            .addAction(R.mipmap.ic_launcher, "Deaktivieren", pendingIntent)
                            .setContentText("Entfernung zum Ziel: " + String.valueOf((Integer.valueOf(entfernung.intValue()) + " Meter")));

        }




        return mBuilder;

    }


}



