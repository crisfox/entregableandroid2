package com.example.dh.entregableandroid2.model.dao;

/**
 * Created by Cristian on 14/7/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dh.entregableandroid2.model.pojo.Artist;

import java.util.List;

@Dao
public interface DAORoomArtistas {
    @Query("SELECT * FROM Artist")
    List<Artist> getAllArtists();

    @Query("SELECT * FROM Artist WHERE name Like :name")
    Artist getTodoWithName(String name);

    @Insert
    void insertAll(Artist... artist);

    @Delete
    void delete(Artist artist);
}