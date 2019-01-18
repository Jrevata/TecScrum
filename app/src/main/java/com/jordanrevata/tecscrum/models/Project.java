package com.jordanrevata.tecscrum.models;

public class Project {

    private Integer idprojects;
    private String project_name;
    private  Integer number_sprints;
    private String start_date;
    private String end_date;
    private Integer number_members;

    public  Project(){

    }

    public Project(Integer idprojects, String project_name, Integer number_sprints, String start_date, String end_date, Integer number_members) {
        this.idprojects = idprojects;
        this.project_name = project_name;
        this.number_sprints = number_sprints;
        this.start_date = start_date;
        this.end_date = end_date;
        this.number_members = number_members;
    }

    public Integer getIdprojects() {
        return idprojects;
    }

    public void setIdprojects(Integer idprojects) {
        this.idprojects = idprojects;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Integer getNumber_sprints() {
        return number_sprints;
    }

    public void setNumber_sprints(Integer number_sprints) {
        this.number_sprints = number_sprints;
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

    public Integer getNumber_members() {
        return number_members;
    }

    public void setNumber_members(Integer number_members) {
        this.number_members = number_members;
    }
}
