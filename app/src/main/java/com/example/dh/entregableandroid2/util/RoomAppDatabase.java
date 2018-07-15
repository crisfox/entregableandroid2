package com.example.dh.entregableandroid2.util;

/**
 * Created by Cristian on 14/7/2018.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dh.entregableandroid2.model.dao.DAORoomArtistas;
import com.example.dh.entregableandroid2.model.dao.DAORoomPinturas;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.model.pojo.Pintura;

@Database(entities = {Artist.class, Pintura.class}, version = 1)
public abstract class RoomAppDatabase extends RoomDatabase {
    public abstract DAORoomArtistas artistDao();
    public abstract DAORoomPinturas pinturasDao();
}
