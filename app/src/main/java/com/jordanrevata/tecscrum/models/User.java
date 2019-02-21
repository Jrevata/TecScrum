package com.jordanrevata.tecscrum.models;

import com.orm.dsl.Table;

@Table
public class User {

    private Integer idusers;
    private String email;
    private String password;
    private String image;
    private String fullname;
    private String familyName;
    private String givenName;
    private String phone;
    private String role;
    private String created_at;
    private String updated_at;
    private String token;
    private Integer state;


    public User(){


    }

    public User(Integer idusers, String email, String image, String familyName, String givenName, String phone) {
        this.idusers = idusers;
        this.email = email;
        this.image = image;
        this.familyName = familyName;
        this.givenName = givenName;
        this.phone = phone;
        this.fullname = givenName + " " + familyName;
    }

    public User(Integer idusers, String email, String password, String image, String fullname, String familyName, String givenName, String phone, String role, String created_at, String updated_at, String token, Integer state) {
        this.idusers = idusers;
        this.email = email;
        this.password = password;
        this.image = image;
        this.fullname = fullname;
        this.familyName = familyName;
        this.givenName = givenName;
        this.phone = phone;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.token = token;
        this.state = state;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "idusers=" + idusers +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", fullname='" + fullname + '\'' +
                ", familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", token='" + token + '\'' +
                ", state=" + state +
                '}';
    }
}
