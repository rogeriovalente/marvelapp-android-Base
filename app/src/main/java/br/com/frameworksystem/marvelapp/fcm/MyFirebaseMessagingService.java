package br.com.frameworksystem.marvelapp.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.com.frameworksystem.marvelapp.R;
import br.com.frameworksystem.marvelapp.ui.activities.MainActivity;

/**
 * Created by rogerio.valente on 29/09/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  String TAG = "push";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.d(TAG, "From: " + remoteMessage.getFrom());
    Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

    sendNotification(remoteMessage.getNotification().getBody());
  }


  private void sendNotification(String messageBody) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_marvel)
        .setContentTitle("Marvel App" + " - Push Notification")
        .setContentText(messageBody)
        .setAutoCancel(Boolean.TRUE)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0, notificationBuilder.build());
  }
}
