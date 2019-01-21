package com.jordanrevata.tecscrum.models;

public class MoodToday {

    private Integer idmoodtoday;
    private Integer sprints_idsprints;
    private Integer users_idusers;
    private String moodname;
    private Integer mood_idmood;
    private String  dedicated_time;
    private String difficulties;
    private String date_mood;
    private Boolean state;


    public MoodToday(){

    }

    public MoodToday(Integer idmoodtoday, Integer sprints_idsprints, Integer users_idusers, String moodname, Integer mood_idmood, String dedicated_time, String difficulties, String date_mood, Boolean state) {
        this.idmoodtoday = idmoodtoday;
        this.sprints_idsprints = sprints_idsprints;
        this.users_idusers = users_idusers;
        this.moodname = moodname;
        this.mood_idmood = mood_idmood;
        this.dedicated_time = dedicated_time;
        this.difficulties = difficulties;
        this.date_mood = date_mood;
        this.state = state;
    }


    public Integer getIdmoodtoday() {
        return idmoodtoday;
    }

    public void setIdmoodtoday(Integer idmoodtoday) {
        this.idmoodtoday = idmoodtoday;
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

    public String getMoodname() {
        return moodname;
    }

    public void setMoodname(String moodname) {
        this.moodname = moodname;
    }

    public Integer getMood_idmood() {
        return mood_idmood;
    }

    public void setMood_idmood(Integer mood_idmood) {
        this.mood_idmood = mood_idmood;
    }

    public String getDedicated_time() {
        return dedicated_time;
    }

    public void setDedicated_time(String dedicated_time) {
        this.dedicated_time = dedicated_time;
    }

    public String getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(String difficulties) {
        this.difficulties = difficulties;
    }

    public String getDate_mood() {
        return date_mood;
    }

    public void setDate_mood(String date_mood) {
        this.date_mood = date_mood;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}

