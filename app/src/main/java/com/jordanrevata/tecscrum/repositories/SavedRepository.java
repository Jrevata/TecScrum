package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Saved;
import com.orm.SugarRecord;

public class SavedRepository {

    public static void create(Integer idusers, String token){

        Saved saved = new Saved(idusers, token);
        SugarRecord.save(saved);

    }



    public static boolean verifySave(){

        boolean verify = SugarRecord.listAll(Saved.class).isEmpty();

        return verify;

    }

    public static void deleteSave(){

        SugarRecord.deleteAll(Saved.class);

    }

}
