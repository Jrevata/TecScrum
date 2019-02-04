package com.jordanrevata.tecscrum.utilities;

import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.MoodToday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Function {

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

}
