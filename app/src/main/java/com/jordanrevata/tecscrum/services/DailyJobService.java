package com.jordanrevata.tecscrum.services;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.utilities.Constant;
import com.jordanrevata.tecscrum.utilities.Function;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DailyJobService extends JobService {

    private static final String TAG = DailyJobService.class.getSimpleName();



    @Override
    public boolean onStartJob(JobParameters job) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                notificate();
            }
        }).start();




        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    public void notificate(){

        try {



            Calendar now = Calendar.getInstance();
            String dateNow = Function.convertToString(now);
            Calendar timestart = (Calendar) now.clone();
            timestart.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNow + " " + Constant.TIME_START_DAILY));

            Calendar timeend = (Calendar) timestart.clone();
            timeend.add(Calendar.MINUTE, 1);

            if(timestart.before(now) && now.before(timeend)){



                if(isNetworkAvailable()) {



                    Integer idSprint = Function.getIdSprintByDateNow();
                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - DAILY", "Toque para completar el daily", new DailyActivity(), Function.getIdSprintByDateNow());

                }else{

                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - DAILY", "No olvide conectarse a la red para completar el daily", null, null);

                }
                Log.d(TAG, "Daily Reminder...!!");

            }

            Log.d(TAG, "No pasó nada, sorry man " + now.getTime().toString());

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


    private void updateData(){

        Function.updateProjects();
        Function.updateSprints();

    }



}
