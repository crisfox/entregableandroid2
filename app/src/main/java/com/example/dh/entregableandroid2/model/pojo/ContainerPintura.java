package com.example.dh.entregableandroid2.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class ContainerPintura {
    @SerializedName("paints")
    List<Pintura> listaDePinturas;

    public ContainerPintura(List<Pintura> listaDePinturas) {
        this.listaDePinturas = listaDePinturas;
    }

    public List<Pintura> getListaDePinturas() {
        return listaDePinturas;
    }
}
