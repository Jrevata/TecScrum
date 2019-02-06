package com.jordanrevata.tecscrum.models;

import com.orm.dsl.Table;

@Table
public class Sprint {

    private Integer idsprints;
    private Integer projects_idprojects;
    private String sprint_name;
    private String sprint_goal;
    private String start_date;
    private String end_date;
    private String created_at;
    private String updated_at;
    private Integer state;


    public Sprint(){

    }




    public Sprint(Integer idsprints, Integer projects_idprojects, String sprint_name, String sprint_goal, String start_date, String end_date, String created_at, String updated_at, Integer state) {
        this.idsprints = idsprints;
        this.projects_idprojects = projects_idprojects;
        this.sprint_name = sprint_name;
        this.sprint_goal = sprint_goal;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.state = state;
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


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "idsprints=" + idsprints +
                ", projects_idprojects=" + projects_idprojects +
                ", sprint_name='" + sprint_name + '\'' +
                ", sprint_goal='" + sprint_goal + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", state=" + state +
                '}';
    }
}
