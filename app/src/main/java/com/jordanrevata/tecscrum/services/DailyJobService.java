package com.jordanrevata.tecscrum.services;


import android.annotation.SuppressLint;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.utilities.Constant;
import com.jordanrevata.tecscrum.utilities.Function;


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
                Toast.makeText(getApplicationContext(), "Message: " + s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "In Background...!!");
                RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();
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
            Toast.makeText(getApplicationContext(), "Execution background", Toast.LENGTH_LONG).show();

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

    public static class BackgroundTask extends AsyncTask<Void,Void, String>{


        @Override
        protected String doInBackground(Void... voids) {
            return "Jelouda";
        }
    }



}
