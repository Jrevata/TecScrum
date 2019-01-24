package com.jordanrevata.tecscrum.models;
import com.orm.dsl.Table;

@Table
public class Saved {

    private Integer idusers;
    private String token;

    public Saved(){

    }

    public Saved(Integer idusers, String token) {
        this.idusers = idusers;
        this.token = token;
    }

    public Integer getIdusers() {
        return idusers;
    }

    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Saved{" +
                "idusers=" + idusers +
                ", token='" + token + '\'' +
                '}';
    }
}
