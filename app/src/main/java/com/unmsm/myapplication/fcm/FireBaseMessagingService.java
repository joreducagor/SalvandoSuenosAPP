package com.unmsm.myapplication.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unmsm.myapplication.Activities.MainActivity;
import com.unmsm.myapplication.R;


public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
// [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
// [START_EXCLUDE]
// There are two types of messages data messages and notification messages. Data messages are handled
// here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
// traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
// is in the foreground. When the app is in the background an automatically generated notification is displayed.
// When the user taps on the notification they are returned to the app. Messages containing both notification
// and data payloads are treated as notification messages. The Firebase console always sends notification
// messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
// [END_EXCLUDE]

// TODO(developer): Handle FCM messages here.
// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

// Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            showNotification("" + remoteMessage.getData().get("message"));
        }

// Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

// Also if you intend on generating your own notifications as a result of a received FCM
// message, here is where that should be initiated. See sendNotification method below.
    }
// [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     */

    private void showNotification(String message) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Test Notification")
                .setLargeIcon(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null)).getBitmap())
                .setSmallIcon(getNotificationIcon())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);
        ;

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setOnlyAlertOnce(false);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        mNotificationManager.notify(0, notification);
    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}