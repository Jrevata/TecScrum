package com.jordanrevata.tecscrum.repositories;

import com.jordanrevata.tecscrum.models.Project;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {



    public static void saveProjects(List<Project> projects){

        for (Project project : projects) {

            SugarRecord.save(project);

        }

    }

    public static void deleteProjects(){

        SugarRecord.deleteAll(Project.class);

    }

    public static List<Project> getProjects(){

        List<Project> projects = SugarRecord.listAll(Project.class);
        return projects;

    }

}
