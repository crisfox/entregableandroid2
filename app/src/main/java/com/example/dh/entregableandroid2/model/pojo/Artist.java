package com.example.dh.entregableandroid2.model.pojo;

/**
 * Created by Cristian on 22/6/2018.
 */

public class Artist {
    private String name;
    private String nationality;
    private String artistId;
    private String imagen;


    public Artist() {
        //Para Firebase
    }

    public Artist(String name, String nationality, String artistId, String imagen) {
        this.name = name;
        this.nationality = nationality;
        this.artistId = artistId;
        this.imagen = imagen;
    }

    public String getArtistId() {
        return artistId;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", artistId='" + artistId + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getImagen() {
        return imagen;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
