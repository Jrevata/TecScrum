package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.User;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private static List<User> team = new ArrayList<>();



    static {
        team.add(new User(1, "jordan.revata@tecsup.edu.pe" , "www.casd.com","Revata Cuela", "Jordan Axel", "7795501"));
        team.add(new User(2, "axel.revata@tecsup.edu.pe" , "www.casd.com","Lamata Feliz", "Jorge Axel", "7795502"));
        team.add(new User(3, "carhuancho.revata@tecsup.edu.pe" , "www.casd.com","Rico de Alegría", "Alma Marcela", "7795503"));
    }

    public static List<User> getList() {
        return team;
    }


}
