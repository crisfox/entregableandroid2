package com.example.dh.entregableandroid2.controller;

import android.content.Context;

import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.util.DataBase;

import java.util.List;

/**
 * Created by Cristian on 14/7/2018.
 */

public class ControllerArtists {

    private Context context;

    public ControllerArtists(Context context) {
        this.context = context;
    }

    public List<Artist> getArtists(){
        DataBase dataBase = new DataBase(context);
        return dataBase.getAllArtists();
    }

    public void addArtist(Artist... artist){
        DataBase dataBase = new DataBase(context);
        dataBase.insertAll(artist);
    }
    public void removeTodo(Artist artist){
        DataBase dataBase = new DataBase(context);
        dataBase.delete(artist);
    }
    public Artist getTodoWithName(String name){
        DataBase dataBase = new DataBase(context);
        return dataBase.getTodoWithName(name);
    }




    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
