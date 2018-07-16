package com.example.dh.entregableandroid2.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by DH on 11/6/2018.
 */
@Entity
public class Pintura {
    @PrimaryKey
    @NonNull
    private String image;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "id_artist")
    private String artistId;


    public Pintura(String name, String artistId, String image) {
        this.name = name;
        this.artistId = artistId;
        this.image = image;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pintura{" +
                "name='" + name + '\'' +
                ", artistId='" + artistId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
