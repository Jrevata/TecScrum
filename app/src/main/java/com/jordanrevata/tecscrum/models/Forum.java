package com.jordanrevata.tecscrum.models;


//Por discutir en el modelo de la base de datos
public class Forum {

    private Integer idcomments;
    private Integer users_idusers;
    private Integer sprints_idsprints;
    private String message;
    private String created_at;
    private String givenName;
    private String familyName;
    private String image;


    public Forum(){

    }


    public Forum(Integer idcomment, Integer sprints_idsprints, Integer users_idusers, String familyName, String givenName, String message, String created_at, String image) {
        this.idcomments = idcomment;
        this.sprints_idsprints = sprints_idsprints;
        this.users_idusers = users_idusers;
        this.familyName = familyName;
        this.givenName = givenName;
        this.message = message;
        this.created_at = created_at;
        this.image = image;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Integer getIdcomments() {
        return idcomments;
    }

    public void setIdcomments(Integer idcomment) {
        this.idcomments = idcomment;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
