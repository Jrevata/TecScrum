package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Daily;

import java.util.ArrayList;
import java.util.List;

public class DailyRepository {

    private static List<Daily> dailies = new ArrayList<>();

    static {

        for (int i = 1; i < 21; i++)
            dailies.add(new Daily(1, 1, 1, "Daily " + i, "Nada", "Nada", i+"-01-2019", true));
    }


    public static List<Daily> getList(){
        return dailies;
    }
}
