package com.example.dh.entregableandroid2.model.pojo;

/**
 * Created by Cristian on 22/6/2018.
 */

public class Artist {
    private String name;
    private String nationality;
    private String artistId;
    private String imagen;
    private String descripcion;
    private String Influenced_by;


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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
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

    public String getImagen() {
        return imagen;
    }
}
