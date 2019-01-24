package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.User;
import com.orm.SugarRecord;

public class UserRepository {

    public static void create(User user){

        SugarRecord.save(user);

    }

    public static User getUser(){

        User user = SugarRecord.listAll(User.class).get(0);
        return user;

    }

    public static boolean verifyLogeo(){

        boolean verify = SugarRecord.listAll(User.class).isEmpty();

        return verify;

    }

    public static void logout(){

        SugarRecord.deleteAll(User.class);

    }

}
