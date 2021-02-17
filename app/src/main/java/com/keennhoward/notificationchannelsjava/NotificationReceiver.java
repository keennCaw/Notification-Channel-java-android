package com.keennhoward.notificationchannelsjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    //this will be triggered when we trigger this broadcast receiver
    //we can execute code here without our app being opened

    //DO NOT FORGET TO REGISTER RECEIVER IN MANIFEST
    // <receiver android:name=".NotificationReceiver"/> inside application
    // no need for intent filter because we declared it explicitly

    @Override
    public void onReceive(Context context, Intent intent) {

        //get extra from intent
        String message = intent.getStringExtra("toastMessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
