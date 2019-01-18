package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Sprint;

import java.util.ArrayList;
import java.util.List;

public class SprintRepository {

    private static List<Sprint> sprints = new ArrayList<>();

    static {
        sprints.add(new Sprint(1,"Sprint 1", "Acabar con el modelo de negocio", "15-01-2019", "24-01-2019",1));
        sprints.add(new Sprint(2,"Sprint 2", "Definir e implementar el diseño de las vistas de la app", "25-01-2019", "03-02-2019",1));
        sprints.add(new Sprint(3,"Sprint 3", "Hacer pruebas de la aplicación", "04-02-2019", "13-02-2019",1));
    }

    public static List<Sprint> getList(){
        return sprints;
    }

}
