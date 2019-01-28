package com.jordanrevata.tecscrum.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.activities.MainActivity;
import com.jordanrevata.tecscrum.utilities.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DailyJobService extends JobService {

    private static final String TAG = DailyJobService.class.getSimpleName();
    Integer NOTIFICATION_ID = 1;
    //En caso el dispositivo tenga la versión 8 (Oreo) o superior
    String  CHANNEL_ID = "channel_daily";


    @Override
    public boolean onStartJob(JobParameters job) {

        try{

            notificate();

        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    public void notificate(){

        try {

            getApplicationContext();
            NotificationManager notificationManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);


            Calendar now = Calendar.getInstance();

            Calendar timestart = (Calendar) now.clone();
            timestart.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2019-01-28 " + Constant.TIME_START_DAILY));
            //timestart.setTime(Calendar.getInstance().getTime());
            Calendar timeend = (Calendar) timestart.clone();
            timeend.add(Calendar.MINUTE, 5);

            if(timestart.before(now) && now.before(timeend)){


                /*
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), null);

                Intent intentDaily = new Intent(getApplicationContext(), DailyActivity.class);
                intentDaily.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intentDaily, PendingIntent.FLAG_ONE_SHOT);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                    CharSequence name = "Daily";

                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
                    mChannel.setDescription(getString(R.string.daily_text));
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.BLUE);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                    notificationManager.createNotificationChannel(mChannel);
                    builder = new NotificationCompat.Builder(this, CHANNEL_ID);

                }

                builder.setContentTitle(getString(R.string.daily))
                        .setContentText(getString(R.string.daily_text))
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID, builder.build());*/

                NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                notificationHelper.createNotification("Titulo", "Un mensaje en Oreo :v");

                Log.d(TAG, "Daily Reminder...!!");

            }

        }catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }

}