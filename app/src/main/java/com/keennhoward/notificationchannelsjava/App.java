package com.keennhoward.notificationchannelsjava;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    //REGISTER THIS APPLICATION CLASS IN THE MANIFEST inside <application>
    //<application
    //        android:name=".App">


    //this class wraps our whole application with all its components
    //like activities and services

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    //this will be called before the activities start
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        //check Api version if 26+
        //Notification channels will not work on lower API <26

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Create Notification channel object
            //Notification Channel(id,name,importance)
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            //Note: you can add customizations to channel ex. channel.*
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            //get reference to notification manager
            //to create the channels
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
