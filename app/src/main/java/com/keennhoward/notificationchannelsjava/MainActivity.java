package com.keennhoward.notificationchannelsjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
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

        //create intent that will open activity and add it to a pending intent
        //pending intent is a wrapper to a normal intent so we can pass it to the notification manager
        //which will allow us to execute the intent
        Intent activityIntent = new Intent(this, MainActivity.class);

        //PendingIntent.getActivity(context,request code, intent, flag);
        //request code can be used to update or cancel pending intent
        //flag defines what happens when we recreate this pending intent with a new one

        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        //create broadcast intent
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);

        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0,
                broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT); //add flag to update extra of intent when we create new pending intent

        //create and customize notification
        //its ok to add a channel_ID for api < 26 it will just ignore it
        //action intent is for the notification button click
        //content intent is for notification click

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID) //CHANNEL_1_ID is a const from App class
                .setSmallIcon(R.drawable.ic_1mp_24)
                .setContentText(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH) //add for API < 26
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) //assign category that fits your notification
                .setColor(Color.BLUE) //change color
                .setAutoCancel(true) //when we tap in our notification it will auto dismiss
                .setContentIntent(contentIntent) // add intent intent can also be a service, broadcast, etc
                .setOnlyAlertOnce(true)//will only make sound the first time and not on update
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent) //icon will only be shown for lower API. you can add up to 3 action button
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