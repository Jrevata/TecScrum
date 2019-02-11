package com.jordanrevata.tecscrum.services;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.utilities.Function;

import java.util.Calendar;

public class NotificationHelper {

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String  NOTIFICATION_CHANNEL_ID = "10001";
    public static final Integer NOTIFICATION_ID         = 123456;

    public NotificationHelper(Context context) {
        mContext = context;
    }


    public void createNotification(String title, String message, Activity activity, Integer idsprint)
    {
        Calendar calendar = Calendar.getInstance();
        String now = Function.convertToString(calendar);
        Integer iduser = UserRepository.getUser().getIdusers();
        PendingIntent resultPendingIntent = null;

        if(activity!= null){
            Intent resultIntent = new Intent(mContext , activity.getClass());
            resultIntent.putExtra("idsprint", idsprint);
            resultIntent.putExtra("iduser", iduser);
            resultIntent.putExtra("action", "Edit");
            resultIntent.putExtra("date", now);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            resultPendingIntent = PendingIntent.getActivity(mContext,
                    NOTIFICATION_ID , resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.img_reminder)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        if(activity != null){

            mBuilder.setContentIntent(resultPendingIntent);

        }

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
