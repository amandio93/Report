package com.example.amand.rep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amand on 24/03/2016.
 */
public class User {

    @SerializedName("cod_user")
    public int cod_user;

    @SerializedName("name_user")
    public String name_user;

    @SerializedName("phone_user")
    public String phone_user;

    @SerializedName("email_user")
    public String email_user;

    @SerializedName("password_user")
    public String password_user;

    public User (int cod_user, String name_user, String phone_user, String email_user, String password_user){
        this.cod_user = cod_user;
        this.name_user = name_user;
        this.email_user = email_user;
        this.phone_user = phone_user;
        this.password_user = password_user;
    }

    public User (String email_user, String password_user){
        this.cod_user = -1;
        this.name_user = "";
        this.email_user = email_user;
        this.phone_user = "";
        this.password_user = password_user;
    }

    public User(String email_user){
        this.email_user = email_user;
    }

}
