package com.newsman.newsman.auxiliary.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.newsman.newsman.R;
import com.newsman.newsman.activities.DisplayNewsActivity;
import com.newsman.newsman.auxiliary.Constant;

public class NotificationHelper {
    public static final int NOTIFIACTION_ID = 5;
    public static final String NOTIFICATION_CHANNEL_ID = "news_notification";

    public static void newsUpdateNotification(Context context, int newsId,
                                              String notificationText, boolean startActivity) {
    NotificationManager notificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_notification_small)
                .setLargeIcon(getLargeIcon(context))
                .setContentTitle("NEWSMAN")
                .setContentText(notificationText)
                .setAutoCancel(true);

        if(startActivity)
            notificationBuilder.setContentIntent(displayNewsIntent(context, newsId));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NOTIFIACTION_ID, notificationBuilder.build());
    }

    private static Bitmap getLargeIcon(Context context) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_notification_small);
    }

    private static PendingIntent displayNewsIntent(Context context, int newsId) {
        Intent intent = new Intent(context, DisplayNewsActivity.class);
        intent.putExtra(Constant.NEWS_BUNDLE_KEY, newsId);
        return PendingIntent.getActivity(
                context,
                NOTIFIACTION_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
