package com.example.sriharsha.carpool;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;


public class RideInfo extends GenericJson { // For Serialization

    @Key("_id")
    private String id;
    @Key ("UserID")
    private String User_id;
    @Key ("Source")
    private String Ride_Source;
    @Key ("Destination")
    private String Ride_Destination;
    @Key ("RideTime")
    private String Time;
    @Key ("RideStatus")
    private String Status;
    @Key ("CarNumber")
    private String Car_Num;
    @Key ("PhoneNum")
    private String Phone_Num;
    @Key("_kmd")
    private KinveyMetaData meta;
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public RideInfo(){}  //GenericJson classes must have a public empty constructor


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

    public String getRideTime(){
        return Time;
    }

    public void setRideTime(String time){
        this.Time=time;
    }

    public String getSource(){
        return Ride_Source;
    }
    public void setSource(String source){
        this.Ride_Source=source;
    }

    public String getDestination(){
        return Ride_Destination;
    }
    public void setDestination(String dest){
        this.Ride_Destination=dest;
    }

    public String getCar_Num(){
        return Car_Num;
    }
    public void setCar_Num(String cnum){
        this.Car_Num=cnum;
    }

    public String getPhone_Num(){
        return Phone_Num;
    }
    public void setPhone_Num(String ph){
        this.Phone_Num=ph;
    }

    public String getRideStatus(){
        return Status;
    }
    public void setRideStatus(String status){
        this.Status=status;
    }


}