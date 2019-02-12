package com.jordanrevata.tecscrum.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.MoodToday;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.SavedRepository;
import com.jordanrevata.tecscrum.repositories.SprintRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Function {

    private static final String TAG = Function.class.getSimpleName();

    public static List<Daily> generateDailies(List<Daily> dailies, String date_start, String date_final, Integer idsprint, Integer iduser){

        List<Daily> dailies1 = new ArrayList<>();
        int contador = 0;

        Calendar start_sprint = convertToCalendar(date_start);
        Calendar end_sprint = convertToCalendar(date_final);
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

    public static List<MoodToday> generateMoodTodays(List<MoodToday> moodTodays, String date_start, String date_final, Integer idsprint, Integer iduser){

        List<MoodToday> moodTodays1 = new ArrayList<>();
        int contador = 0;

        Calendar start_sprint = convertToCalendar(date_start);
        Calendar end_sprint = convertToCalendar(date_final);
        Calendar now = Calendar.getInstance();
        if(now.after(end_sprint)){

            while (start_sprint.before(end_sprint) || start_sprint.equals(end_sprint)){

                if(start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start_sprint.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){

                    contador++;

                    MoodToday moodToday = findMoodTodaybyDate(moodTodays, start_sprint);

                    if(moodToday == null){
                        MoodToday moodToday1 = new MoodToday();
                        moodToday1.setSprints_idsprints(idsprint);
                        moodToday1.setUsers_idusers(iduser);
                        moodToday1.setMoodname("Mood Today " + contador);
                        moodToday1.setDate_mood(convertToString(start_sprint));
                        moodToday1.setState(0);
                        moodTodays1.add(moodToday1);
                    }else {
                        moodToday.setMoodname("Mood Today " + contador);
                        moodTodays1.add(moodToday);
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
                        MoodToday moodToday = findMoodTodaybyDate(moodTodays, start_sprint);

                        if (moodToday == null) {
                            MoodToday moodToday1 = new MoodToday();
                            moodToday1.setSprints_idsprints(idsprint);
                            moodToday1.setUsers_idusers(iduser);
                            moodToday1.setMoodname("Mood Today " + contador);
                            moodToday1.setDate_mood(convertToString(start_sprint));
                            moodToday1.setState(0);
                            moodTodays1.add(moodToday1);
                        } else {
                            moodToday.setMoodname("Mood Today " + contador);
                            moodTodays1.add(moodToday);
                        }

                    }


                    start_sprint.add(Calendar.DATE, 1);

                }

            }

        }

        return moodTodays1;
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

    public static MoodToday findMoodTodaybyDate(List<MoodToday> moodTodays, Calendar calendar){



        for(MoodToday moodToday: moodTodays){

            Calendar calendar1 = convertToCalendar(moodToday.getDate_mood());
            if(calendar1.equals(calendar)){
                return moodToday;
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

    public static void updateProjects(){


        ApiService apiService = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Project>> projects = apiService.getProjectsByUser(UserRepository.getUser().getIdusers());

        projects.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        if(!response.body().isEmpty()) {

                            List<Project> projectList1 = response.body();
                            saveDataProjects(projectList1);

                        }


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        //Toast.makeText(DailyJobService.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                //Toast.makeText(DailyJobService.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    public static void updateSprints(){


        Integer idproject = getIdProjectByDateNow();

        if(idproject != 0) {
            ApiService apiService = ApiServiceGenerator.createService(ApiService.class);

            Call<List<Sprint>> sprints = apiService.getSprintsByProject(idproject);

            sprints.enqueue(new Callback<List<Sprint>>() {
                @Override
                public void onResponse(Call<List<Sprint>> call, Response<List<Sprint>> response) {

                    try {

                        int statusCode = response.code();
                        Log.d(TAG, "HTTP status code: " + statusCode);

                        if (response.isSuccessful()) {

                            if(!response.body().isEmpty()) {

                                List<Sprint> sprintList = response.body();
                                saveDataSprints(sprintList);

                            }
                        } else {
                            Log.e(TAG, "onError: " + response.errorBody().string());
                            throw new Exception("Error en el servicio");
                        }

                    } catch (Throwable t) {
                        try {
                            Log.e(TAG, "onThrowable: " + t.toString(), t);
                            //Toast.makeText(DailyJobService.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Throwable x) {
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Sprint>> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.toString());
                    //Toast.makeText(DailyJobService.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }else{

            if(!SprintRepository.verifySprints()) {
                SprintRepository.deleteSprints();
            }
        }

    }

    private static void saveDataProjects(List<Project> projects){

        if(ProjectRepository.verifyProjects()){
            ProjectRepository.saveProjects(projects);
        }else{
            ProjectRepository.deleteProjects();
            ProjectRepository.saveProjects(projects);
        }

    }

    private static void saveDataSprints(List<Sprint> sprints){

        if(SprintRepository.verifySprints()) {
            SprintRepository.saveSprints(sprints);
        }else{
            SprintRepository.deleteSprints();
            SprintRepository.saveSprints(sprints);
        }

    }

    public static Integer getIdProjectByDateNow(){

        Integer idproject = 0;

        if(!ProjectRepository.verifyProjects()) {

            List<Project> projectList = ProjectRepository.getProjects();
            Calendar now = Calendar.getInstance();

            for (Project project : projectList) {

                Calendar start_project = convertToCalendar(project.getStart_date());
                Calendar end_project = convertToCalendar(project.getEnd_date());

                end_project.add(Calendar.DATE, 1);

                if (now.after(start_project) || now.equals(start_project)) {

                    if(now.before(end_project) || now.equals(end_project)){

                        return project.getIdprojects();

                    }


                }

            }
        }


        return idproject;

    }

    public static Integer getIdSprintByDateNow(){

        Integer idsprint = 0;

        if(!SprintRepository.verifySprints()){

            List<Sprint> sprintList = SprintRepository.getSprints();
            Calendar now = Calendar.getInstance();

            for(Sprint sprint : sprintList){

                Calendar start_sprint = convertToCalendar(sprint.getStart_date());
                Calendar end_sprint = convertToCalendar(sprint.getEnd_date());

                end_sprint.add(Calendar.DATE, 1);

                if(now.before(end_sprint) || now.equals(end_sprint)  ){
                    if(now.after(start_sprint) || now.equals(start_sprint)) {
                        return sprint.getIdsprints();
                    }

                }



            }

        }

        return idsprint;
    }






}
