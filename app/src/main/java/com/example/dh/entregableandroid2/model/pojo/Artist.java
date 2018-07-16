package com.example.dh.entregableandroid2.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Cristian on 22/6/2018.
 */
@Entity
public class Artist {
    @PrimaryKey
    @NonNull
    private String artistId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "nacionalidad")
    private String nationality;

    @ColumnInfo(name = "imagen")
    private String imagen;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "influecia")
    private String Influenced_by;

    @Ignore
    public Artist() {
        //Para Firebase
    }


    public Artist(String name, String nationality, String artistId, String imagen, String descripcion, String Influenced_by) {
        this.name = name;
        this.nationality = nationality;
        this.artistId = artistId;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.Influenced_by = Influenced_by;
    }

    public String getInfluenced_by() {
        return Influenced_by;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", artistId='" + artistId + '\'' +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", Influenced_by='" + Influenced_by + '\'' +
                '}';
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getNationality() {
        return nationality;
    }

    public String getImagen() {
        return imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        return artistId.equals(artist.artistId);
    }

    @Override
    public int hashCode() {
        return artistId.hashCode();
    }

}
