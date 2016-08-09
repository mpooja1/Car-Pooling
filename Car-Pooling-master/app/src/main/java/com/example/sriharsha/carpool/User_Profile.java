package com.example.sriharsha.carpool;


import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class User_Profile extends GenericJson { // For Serialization

    @Key("_id")
    private String id;
    @Key ("UserID")
    private String User_id;
    @Key ("Password")
    private String Password;
    @Key ("CarNumber")
    private String Car_No;
    @Key ("PhoneNum")
    private String Phone_No;
    public User_Profile(){}  //GenericJson classes must have a public empty constructor


    public String getActionId(){
        return id;
    }

    public void setActionId(String id){
        this.id=id;
    }

    public String getUserID(){
        return User_id;
    }

    public void setUserID(String username){
        this.User_id=username;
    }

    public String getUserPassword(){
        return Password;
    }

    public void setUserPassword(String password){
        this.Password=password;
    }

    public String getCar_Num(){
        return Car_No;
    }
    public void setCar_Num(String cnum){
        this.Car_No=cnum;
    }

    public String getPhone_Num(){
        return Phone_No;
    }
    public void setPhone_Num(String ph){
        this.Phone_No=ph;
    }

}