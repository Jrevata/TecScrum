package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Sprint;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class SprintRepository {


    public static void saveSprints(List<Sprint> sprints){

        for (Sprint sprint : sprints){

            SugarRecord.save(sprint);

        }

    }

    public static void deleteSprints(){

        SugarRecord.deleteAll(Sprint.class);

    }

    public static List<Sprint> getSprints(){

        List<Sprint> sprintList = SugarRecord.listAll(Sprint.class);

        return sprintList;

    }

    public static Boolean verifySprints(){

        boolean verify = SugarRecord.listAll(Sprint.class).isEmpty();

        return verify;

    }


}
