package com.example.dh.entregableandroid2.model.pojo;

/**
 * Created by DH on 11/6/2018.
 */

public class Pintura extends Arte {
    private String name;
    private String artistId;
    private String image;

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
