package com.example.dh.entregableandroid2.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.model.pojo.Pintura;

import java.util.List;

/**
 * Created by Cristian on 14/7/2018.
 */

public class DataBase {

    private RoomAppDatabase db;

    public DataBase(Context context){
        db = Room.databaseBuilder(context,
                RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public List<Artist> getAllArtists(){
        return db.artistDao().getAllArtists();
    }

    public Artist getTodoWithName(String name){
        return db.artistDao().getTodoWithName(name);
    }

    public void insertAll(Artist... artist){
        db.artistDao().insertAll(artist);
    }

    public void delete(Artist artist){
        db.artistDao().delete(artist);
    }




    public List<Pintura> getAllPinturas(){
        return db.pinturasDao().getAllPinturas();
    }

    public Pintura getPinturaWithName(String name){
        return db.pinturasDao().getPinturaWithName(name);
    }

    public void insertAll(Pintura... pinturas){
        db.pinturasDao().insertAll(pinturas);
    }

    public void delete(Pintura pintura){
        db.pinturasDao().delete(pintura);
    }


}
