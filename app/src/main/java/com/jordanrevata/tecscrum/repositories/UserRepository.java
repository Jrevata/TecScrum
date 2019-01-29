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

    public static void updateUser(User user1){

        User user = getUser();

        user.setPhone(user1.getPhone());
        user.setGivenName(user1.getGivenName());
        user.setFamilyName(user1.getFamilyName());
        user.setImage(user1.getImage());
        user.setFullname(user1.getGivenName() + " " + user1.getFamilyName());

        SugarRecord.update(user);

    }


    public static boolean verifyLogeo(){

        boolean verify = SugarRecord.listAll(User.class).isEmpty();

        return verify;

    }

    public static void logout(){

        SugarRecord.deleteAll(User.class);

    }

}
