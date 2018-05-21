package com.example.maximus09.spfsupply;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;



public class MyIDListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {

        //String mes = data.getString("command");
        //String text = data.getString("text");
        //Log.i("GCM", "Received : (" +from+ ")  command = " + data.getString("command") + " text = " + data.getString("text"));

        String message = data.getString("message");
        String title = data.getString("title");
        String subtitle = data.getString("subtitle");
        String tickerText = data.getString("tickerText");
        int vibrate = Integer.parseInt(data.getString("vibrate"));
        int sound = Integer.parseInt(data.getString("sound"));
        String largeIcon = data.getString("largeIcon");
        String smallIcon = data.getString("smallIcon");
        boolean isDebug = data.getBoolean("debug");

        Log.i(TAG, "Received: " + data.toString());


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());



    }


}
