package com.example.dh.entregableandroid2.controller;

import android.content.Context;

import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.util.DataBase;

import java.util.List;

/**
 * Created by Cristian on 14/7/2018.
 */

public class ControllerRoomArtists {

    private Context context;

    public ControllerRoomArtists(Context context) {
        this.context = context;
    }

    public List<Artist> getArtists() {
        DataBase dataBase = new DataBase(context);
        return dataBase.getAllArtists();
    }

    public void addArtist(Artist artist) {
        DataBase dataBase = new DataBase(context);
        dataBase.insertAll(artist);
    }

    public void removeArtist(Artist artist) {
        DataBase dataBase = new DataBase(context);
        dataBase.delete(artist);
    }

    public Artist getTodoWithName(String name) {
        DataBase dataBase = new DataBase(context);
        return dataBase.getTodoWithName(name);
    }


}
