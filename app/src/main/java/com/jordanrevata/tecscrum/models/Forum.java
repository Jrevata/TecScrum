package com.jordanrevata.tecscrum.models;


//Por discutir en el modelo de la base de datos
public class Forum {

    private Integer idcomment;
    private Integer sprints_idsprints;
    private Integer users_idusers;
    private String fullname;
    private String message;
    private String datetime_comment;
    private String photoURL;


    public Forum(){

    }

    public Forum(Integer idcomment, Integer sprints_idsprints, Integer users_idusers,String fullname, String message, String datetime_comment, String photoURL) {
        this.idcomment = idcomment;
        this.sprints_idsprints = sprints_idsprints;
        this.users_idusers = users_idusers;
        this.fullname = fullname;
        this.message = message;
        this.datetime_comment = datetime_comment;
        this.photoURL = photoURL;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getIdcomment() {
        return idcomment;
    }

    public void setIdcomment(Integer idcomment) {
        this.idcomment = idcomment;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime_comment() {
        return datetime_comment;
    }

    public void setDatetime_comment(String datetime_comment) {
        this.datetime_comment = datetime_comment;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
