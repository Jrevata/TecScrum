package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Daily;

import java.util.ArrayList;
import java.util.List;

public class DailyRepository {

    private static List<Daily> dailies = new ArrayList<>();

    static {

        for (int i = 1; i < 21; i++){
            Daily n = new Daily(i, 1, 1, "Daily " + i, "Nada", "Nada", i+"-01-2019", 1);

            if(i % 2 == 0)
                n.setState(0);

            dailies.add(n);
        }
    }


    public static List<Daily> getList(){
        return dailies;
    }
}
