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
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.utilities.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public static Integer calculateDays(String start, String end){

        int contador = 0;

        try {


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date_start = sdf.parse(start);
            Date date_end  = sdf.parse(end);

            Calendar start_calendar = Calendar. getInstance();
            start_calendar.setTime(date_start);
            Calendar end_calendar = Calendar. getInstance();
            end_calendar.setTime(date_end);

            while (start_calendar.before(end_calendar) || start_calendar.equals(end_calendar)){

                if(start_calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start_calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                    contador++;

                start_calendar.add(Calendar.DATE, 1);

            }



        } catch (ParseException e) {

            e.printStackTrace();
        }

        return contador;

    }

    public static  List<Daily> generateDailies(List<Daily> dailies, String dateinicio, String datefinal, Integer idsprint, Integer iduser){

        List<Daily> dailies1 = new ArrayList<>();
        int contador = 0;

        Calendar start_sprint = convertToCalendar(dateinicio);
        Calendar end_sprint = convertToCalendar(datefinal);
        Calendar now = Calendar.getInstance();
        if(now.after(end_sprint)){

            while (start_sprint.before(end_sprint) || start_sprint.equals(end_sprint)){

                if(start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){

                    contador++;

                    Daily daily = findDailybyDate(dailies, start_sprint);

                    if(daily == null){
                        Daily daily1 = new Daily();
                        daily1.setSprints_idsprints(idsprint);
                        daily1.setUsers_idusers(iduser);
                        daily1.setDailyname("Daily " + contador);
                        daily1.setDate_daily(convertToString(start_sprint));
                        daily1.setState(0);
                        dailies1.add(daily1);
                    }else {
                        daily.setDailyname("Daily " + contador);
                        dailies1.add(daily);
                    }

                }


                start_sprint.add(Calendar.DATE,1);

            }

        }
        if(now.before(end_sprint) || now.equals(end_sprint)  ){
            if(now.after(start_sprint) || now.equals(start_sprint)) {

                while (start_sprint.before(now) || start_sprint.equals(now)) {

                    if (start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

                        contador++;
                        Daily daily = findDailybyDate(dailies, start_sprint);

                        if (daily == null) {
                            Daily daily1 = new Daily();
                            daily1.setSprints_idsprints(idsprint);
                            daily1.setUsers_idusers(iduser);
                            daily1.setDailyname("Daily " + contador);
                            daily1.setDate_daily(convertToString(start_sprint));
                            daily1.setState(0);
                            dailies1.add(daily1);
                        } else {
                            daily.setDailyname("Daily " + contador);
                            dailies1.add(daily);
                        }

                    }


                    start_sprint.add(Calendar.DATE, 1);

                }

            }

        }



        return dailies1;
    }


    public static Daily findDailybyDate(List<Daily> dailies,Calendar calendar){



        for(Daily daily1: dailies){

            Calendar calendar1 = convertToCalendar(daily1.getDate_daily());
            if(calendar1.equals(calendar)){
                return daily1;
            }

        }

        return null;

    }

    public static Calendar convertToCalendar(String date){

        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date_start = sdf.parse(date);

            calendar.setTime(date_start);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return calendar;
    }

    public static String convertToString(Calendar calendar){

        String date = null;

        Date date1 = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd");
        date = format1.format(date1);

        return date;

    }

}
