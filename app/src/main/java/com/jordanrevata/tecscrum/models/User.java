package com.jordanrevata.tecscrum.models;

public class User {

    private Integer idusers;
    private String email;
    private String pictureURL;
    private String fullname;
    private String familyName;
    private String givenName;
    private String phone;


    public User(){}

    public User(Integer idusers, String email, String pictureURL, String familyName, String givenName, String phone) {
        this.idusers = idusers;
        this.email = email;
        this.pictureURL = pictureURL;
        this.familyName = familyName;
        this.givenName = givenName;
        this.phone = phone;
        this.fullname = givenName + " " + familyName;
    }

    public Integer getIdusers() {
        return idusers;
    }

    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "User{" +
                "idusers=" + idusers +
                ", email='" + email + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                ", fullname='" + fullname + '\'' +
                ", familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
