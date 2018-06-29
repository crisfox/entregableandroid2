package com.example.dh.entregableandroid2.model.pojo;

/**
 * Created by Cristian on 22/6/2018.
 */

public class Artist extends Arte {
    private String name;
    private String nationality;
    private String artistId;


    public Artist() {
        //Para Firebase
    }

    public Artist(String name, String nationality, String artistId) {
        this.name = name;
        this.nationality = nationality;
        this.artistId = artistId;
    }

    public String getArtistId() {
        return artistId;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
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

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
