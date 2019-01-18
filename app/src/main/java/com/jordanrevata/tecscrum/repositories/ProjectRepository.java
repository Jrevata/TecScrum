package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private static List<Project> projects = new ArrayList<>();



    static {

        projects.add(new Project(1, "Tecsup Virtual", 4, "15-01-2019", "25-01-2019",12));
        projects.add(new Project(2, "Código GO", 5, "16-01-2019", "26-01-2019",13));
        projects.add(new Project(3, "TecScrum", 6, "17-01-2019", "27-01-2019",14));
        projects.add(new Project(4, "Sistema Blockchain para la aplicación de los certificados en la minería", 6, "18-01-2019", "28-01-2019",5));


    }

    public static List<Project> getList(){
        return  projects;
    }

}
