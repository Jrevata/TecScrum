package com.jordanrevata.tecscrum.services;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.activities.MoodTodayActivity;
import com.jordanrevata.tecscrum.utilities.Constant;
import com.jordanrevata.tecscrum.utilities.Function;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DailyJobService extends JobService {

    public static final String TAG = DailyJobService.class.getSimpleName();


    BackgroundTask backgroundTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {


        backgroundTask = new BackgroundTask(){

            @Override
            protected void onPostExecute(String s) {
                notificateGeneral();
                updateData();
                jobFinished(job, false);
            }
        };

        backgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    private void notificateDaily(){

        Integer idSprint = Function.getIdSprintByDateNow();
        Integer contadorDaily = getSharedPreferences("CONT_DAILY", MODE_PRIVATE).getInt("contador_daily", 0);

        if(contadorDaily == 0){
            if(idSprint != 0) {

                if (isNetworkAvailable()) {


                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - DAILY", "Toque para completar el daily", new DailyActivity(), idSprint);

                } else {

                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - DAILY", "No olvide conectarse a la red para completar el daily", null, null);
                }
            }
        }

        getSharedPreferences("CONT_DAILY", MODE_PRIVATE).edit().putInt("contador_daily",1).apply();
        Integer newContador = getSharedPreferences("CONT_DAILY", MODE_PRIVATE).getInt("contador_daily", 0);
        Log.d(TAG, "new persistent data : " + newContador.toString());


    }

    private void notificateMood(){

        Integer idSprint = Function.getIdSprintByDateNow();
        Integer contadorMood = getSharedPreferences("CONT_MOOD", MODE_PRIVATE).getInt("contador_mood", 0);

        if(contadorMood == 0) {
            if (idSprint != 0) {

                if (isNetworkAvailable()) {

                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - MOODTODAY", "Toque para completar el Mood Today", new MoodTodayActivity(), idSprint);

                } else {

                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    notificationHelper.createNotification("TECSCRUM - MOODTODAY", "No olvide conectarse a la red para completar el Mood Today", null, null);

                }

            }
        }

        getSharedPreferences("CONT_MOOD", MODE_PRIVATE).edit().putInt("contador_mood", 1).apply();

    }

    private void notificateGeneral(){

        try {


            //Time daily
            Calendar nowDaily = Calendar.getInstance();
            String dateNowDaily = Function.convertToString(nowDaily);
            Calendar timeStartDaily = (Calendar) nowDaily.clone();
            timeStartDaily.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNowDaily + " " + Constant.TIME_START_DAILY));
            Calendar timeEndDaily = (Calendar) timeStartDaily.clone();
            timeEndDaily.add(Calendar.MINUTE, 60);

            //TimeMood
            Calendar nowMood = Calendar.getInstance();
            String dateNowMood = Function.convertToString(nowMood);
            Calendar timeStartMood = (Calendar) nowMood.clone();
            timeStartMood.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNowMood + " " + Constant.TIME_START_MOOD_TODAY));
            Calendar timeEndMood = (Calendar) timeStartMood.clone();

            if(timeStartDaily.before(nowDaily) && nowDaily.before(timeEndDaily)){

                notificateDaily();

                getSharedPreferences("CONT_MOOD", MODE_PRIVATE).edit().putInt("contador_mood", 0).apply();


            }

            if (timeStartMood.before(nowMood) && nowMood.before(timeEndMood)) {

                notificateMood();

                getSharedPreferences("CONT_DAILY", MODE_PRIVATE).edit().putInt("contador_daily", 0).apply();

            }

        }catch (ParseException e){
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

        try {
            Calendar now = Calendar.getInstance();

            String dateNow = Function.convertToString(now);

            Calendar timeStartUpdateProject = (Calendar) now.clone();
            timeStartUpdateProject.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNow + " " + Constant.TIME_START_UPDATE_PROJECT));
            Calendar timeEndUpdateProject = (Calendar) timeStartUpdateProject.clone();
            timeEndUpdateProject.add(Calendar.MINUTE, 60);

            Calendar timeStartUpdateSprint = (Calendar) now.clone();
            timeStartUpdateSprint.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateNow + " " + Constant.TIME_START_UPDATE_SPRINT));
            Calendar timeEndUpdateSprint = (Calendar) timeStartUpdateSprint.clone();
            timeEndUpdateSprint.add(Calendar.MINUTE, 60);


            if(timeStartUpdateProject.before(now) && now.before(timeEndUpdateProject)){

                updateProjects();

                getSharedPreferences("CONT_UPDATE_SPRINT", MODE_PRIVATE).edit().putInt("contador_sprint", 0).apply();

            }

            if(timeStartUpdateSprint.before(now) && now.before(timeEndUpdateSprint)){

                updateSprints();

                getSharedPreferences("CONT_UPDATE_PROJECT", MODE_PRIVATE).edit().putInt("contador_project", 0).apply();
            }



        }catch (ParseException e){
            Log.e(TAG, e.toString());
        }

    }



    private void updateProjects(){

        Integer contadorProjects = getSharedPreferences("CONT_UPDATE_PROJECT", MODE_PRIVATE).getInt("contador_project", 0);

        if(isNetworkAvailable()) {
            if (contadorProjects == 0) {

                Function.updateProjects();
                Log.d(TAG, "Update Projects");
            }

            getSharedPreferences("CONT_UPDATE_PROJECT", MODE_PRIVATE).edit().putInt("contador_project", 1).apply();
        }
    }

    private void updateSprints(){

        Integer contadorSprints = getSharedPreferences("CONT_UPDATE_SPRINT", MODE_PRIVATE).getInt("contador_sprint", 0);


        if(isNetworkAvailable()) {

            if (contadorSprints == 0) {

                Function.updateSprints();
                Log.d(TAG, "Update Sprints");

            }

            getSharedPreferences("CONT_UPDATE_SPRINT", MODE_PRIVATE).edit().putInt("contador_sprint", 1).apply();
        }
    }


    public static class BackgroundTask extends AsyncTask<Void,Void, String>{


        @Override
        protected String doInBackground(Void... voids) {
            return "";
        }
    }



}
