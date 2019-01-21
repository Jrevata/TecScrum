package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.MoodToday;

import java.util.ArrayList;
import java.util.List;

public class MoodRepository {

    private static List<MoodToday> moodTodays = new ArrayList<>();

    static {

        for (int i = 1; i < 51; i++){
            moodTodays.add(new MoodToday(1,1,1,"Mood Today " + i, 1, "1","Nada", i + "-01-2019", true ));
        }

    }


    public static List<MoodToday> getList(){
        return moodTodays;
    }

}
