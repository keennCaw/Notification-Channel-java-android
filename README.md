# Notification-Channel-java-android
Using Notification channels in displaying notifications

Create Notification channels in a Application class
Note: Do not forget to register it in the manifest


IMPORTS

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


Example how to use notification channels in the main activity:


IMPORTS

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;

import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

import static com.keennhoward.notificationchannelsjava.App.CHANNEL_1_ID;

import static com.keennhoward.notificationchannelsjava.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    //use this notification manager to show our notifications
    //because it wraps around a normal notification manager and has some internal checks for
    //backwards compatibility but it cannot make notification channels
    private NotificationManagerCompat notificationManager;

    //Initialize edit texts
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables
        notificationManager = NotificationManagerCompat.from(this); //will be used to send notifications

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);


    }


    //Create and show notification
    public void sendOnChannel1(View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        //create and customize notification
        //its ok to add a channel_ID for api < 26 it will just ignore it
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID) //CHANNEL_1_ID is a const from App class
                .setSmallIcon(R.drawable.ic_1mp_24)
                .setContentText(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH) //add for API < 26
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) //assign category that fits your notification
                .build();

        //display notification
        //notifications with the same id will override the first
        //make multiple notifications by using different id's
        notificationManager.notify(1, notification);
    }


    public void sendOnChannel2(View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID) //CHANNEL_1_ID is a const from App class
                .setSmallIcon(R.drawable.ic_22mp_24)
                .setContentText(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW) //add for API < 26
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) //assign category that fits your notification
                .build();

        notificationManager.notify(2, notification);
    }
}


![alt text](https://docs.google.com/uc?export=download&id=1oSpqwlqZ3nUJGcmUMa4EosxX-WVa2FDT)
