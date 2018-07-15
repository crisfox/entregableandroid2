package com.example.dh.entregableandroid2.model.dao;

/**
 * Created by Cristian on 14/7/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dh.entregableandroid2.model.pojo.Pintura;

import java.util.List;

@Dao
public interface DAORoomPinturas {
    @Query("SELECT * FROM Pintura")
    List<Pintura> getAllPinturas();

    @Query("SELECT * FROM Pintura WHERE name Like :name")
    Pintura getPinturaWithName(String name);

    @Insert
    void insertAll(Pintura... pinturas);

    @Delete
    void delete(Pintura pintura);
}