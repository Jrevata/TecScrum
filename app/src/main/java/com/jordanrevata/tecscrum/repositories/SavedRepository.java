package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Saved;
import com.orm.SugarRecord;

public class SavedRepository {

    public static void create(Integer idusers, String token){

        Saved user = new Saved(idusers, token);
        SugarRecord.save(user);

    }


    public static Saved getUser(){

        Saved user = SugarRecord.listAll(Saved.class).get(0);
        return user;

    }

    public static boolean verifyLogeo(){

        boolean verify = SugarRecord.listAll(Saved.class).isEmpty();

        return verify;

    }

    public static void logout(){

        SugarRecord.deleteAll(Saved.class);

    }

}
