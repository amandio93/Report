package com.example.amand.rep.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amand on 03/04/2016.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore (Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        /*spEditor.putInt("cod_user", user.cod_user);
        spEditor.putString("name_user", user.name_user);
        spEditor.putString("phone_user", user.phone_user);*/
        spEditor.putString("email_user", user.email_user);
        //spEditor.putString("password_user", user.password_user);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        /*String name_user = userLocalDatabase.getString("name_user", "");
        int cod_user = userLocalDatabase.getInt("cod_user", -1);
        String phone_user = userLocalDatabase.getString("phone_user", "");*/
        String email_user = userLocalDatabase.getString("email_user", "");
        //String password_user = userLocalDatabase.getString("password_user", "");

        //User storedUser = new User(cod_user, name_user, phone_user, email_user, password_user);
        User storedUser = new User(email_user);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggerIn", loggedIn);
        spEditor.commit();
    }

    public void clearData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", true)){
            return true;
        }else {
            return false;
        }
    }

}
