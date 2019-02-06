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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.activities.MainActivity;
import com.jordanrevata.tecscrum.activities.MoodTodayActivity;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.MoodToday;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.utilities.Constant;
import com.jordanrevata.tecscrum.utilities.Function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyJobService extends JobService {

    private static final String TAG = DailyJobService.class.getSimpleName();
    Integer NOTIFICATION_ID = 1;
    //En caso el dispositivo tenga la versión 8 (Oreo) o superior
    String  CHANNEL_ID = "channel_daily";
    Integer idproject = 0;



    private void setIdproject(Integer idproject) {
        this.idproject = idproject;
    }

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

            Calendar now = Calendar.getInstance();
            String dateNow = Function.convertToString(now);
            Calendar timestart = (Calendar) now.clone();
            timestart.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNow + " " + Constant.TIME_START_DAILY));

            Calendar timeend = (Calendar) timestart.clone();
            timeend.add(Calendar.MINUTE, 1);

            if(timestart.before(now) && now.before(timeend)){



                if(isNetworkAvailable()) {

                    Function.updateProjects();
                    Function.updateSprints();

                    Integer idSprint = Function.getIdSprintByDateNow();
                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("Titulo", "Tiene internet, que emoción " + idSprint );

                }else{

                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("Titulo", "GG mitre, no tiene internet");

                }
                Log.d(TAG, "Daily Reminder...!!");

            }

        }catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }












    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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


}
