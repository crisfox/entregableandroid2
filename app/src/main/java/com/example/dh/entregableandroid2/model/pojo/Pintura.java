package com.example.dh.entregableandroid2.model.pojo;

/**
 * Created by DH on 11/6/2018.
 */

public class Pintura extends Arte {
    private String name;
    private String artistId;

    public Pintura(String name, String artistId) {
        this.name = name;
        this.artistId = artistId;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pintura{" +
                "name='" + name + '\'' +
                '}';
    }
}
