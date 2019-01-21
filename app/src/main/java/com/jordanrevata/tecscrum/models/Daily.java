package com.jordanrevata.tecscrum.models;

public class Daily {

    private Integer iddailies;
    private Integer sprints_idsprints;
    private Integer users_idusers;
    private String dailyname;
    private String what_did;
    private String what_willdo;
    private String date_daily;
    private Boolean state;

    public Daily(Integer iddailies, Integer sprints_idsprints, Integer users_idusers,String dailyname, String what_did, String what_willdo, String date_daily, Boolean state) {
        this.iddailies = iddailies;
        this.sprints_idsprints = sprints_idsprints;
        this.users_idusers = users_idusers;
        this.dailyname = dailyname;
        this.what_did = what_did;
        this.what_willdo = what_willdo;
        this.date_daily = date_daily;
        this.state = state;
    }

    public Daily(){}


    public String getDailyname() {
        return dailyname;
    }

    public void setDailyname(String dailyname) {
        this.dailyname = dailyname;
    }

    public Integer getIddailies() {
        return iddailies;
    }

    public void setIddailies(Integer iddailies) {
        this.iddailies = iddailies;
    }

    public Integer getSprints_idsprints() {
        return sprints_idsprints;
    }

    public void setSprints_idsprints(Integer sprints_idsprints) {
        this.sprints_idsprints = sprints_idsprints;
    }

    public Integer getUsers_idusers() {
        return users_idusers;
    }

    public void setUsers_idusers(Integer users_idusers) {
        this.users_idusers = users_idusers;
    }

    public String getWhat_did() {
        return what_did;
    }

    public void setWhat_did(String what_did) {
        this.what_did = what_did;
    }

    public String getWhat_willdo() {
        return what_willdo;
    }

    public void setWhat_willdo(String what_willdo) {
        this.what_willdo = what_willdo;
    }

    public String getDate_daily() {
        return date_daily;
    }

    public void setDate_daily(String date_daily) {
        this.date_daily = date_daily;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
