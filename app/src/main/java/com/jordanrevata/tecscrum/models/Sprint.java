package com.jordanrevata.tecscrum.models;

public class Sprint {

    private Integer idsprints;
    private String sprint_name;
    private String sprint_goal;
    private String start_date;
    private String end_date;
    private Integer projects_idprojects;

    public Sprint(){

    }

    public Sprint(Integer idsprints, String sprint_name, String sprint_goal, String start_date, String end_date, Integer projects_idprojects) {
        this.idsprints = idsprints;
        this.sprint_name = sprint_name;
        this.sprint_goal = sprint_goal;
        this.start_date = start_date;
        this.end_date = end_date;
        this.projects_idprojects = projects_idprojects;
    }

    public Integer getIdsprints() {
        return idsprints;
    }

    public void setIdsprints(Integer idsprints) {
        this.idsprints = idsprints;
    }

    public String getSprint_name() {
        return sprint_name;
    }

    public void setSprint_name(String sprint_name) {
        this.sprint_name = sprint_name;
    }

    public String getSprint_goal() {
        return sprint_goal;
    }

    public void setSprint_goal(String sprint_goal) {
        this.sprint_goal = sprint_goal;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Integer getProjects_idprojects() {
        return projects_idprojects;
    }

    public void setProjects_idprojects(Integer projects_idprojects) {
        this.projects_idprojects = projects_idprojects;
    }
}
