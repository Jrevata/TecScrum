package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Forum;

import java.util.ArrayList;
import java.util.List;

public class ForumRepository {

    private static List<Forum> comments = new ArrayList<>();

    static {
/*
        comments.add(new Forum(1,1, 2,"Jordan Revata Cuela", "Hola que tal prueba soy nuevo en esto, cuento con su ayuda", "01-01-2019 12:00", "www.asd.com"));
        comments.add(new Forum(2,1, 3,"Axel Revata Gonzales", "No hay problema, nosotros podemos ayudarte", "01-01-2019 12:12", "www.asd.com"));
        comments.add(new Forum(3,1, 4,"Benito Camelo", "Alguien que me preste dinero? porfavor :v", "01-01-2019 13:01", "www.asd.com"));
*/
    }

    public static List<Forum> getList(){
        return comments;
    }

}
