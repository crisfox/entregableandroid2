package com.example.dh.entregableandroid2.controller;

import android.content.Context;

import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.DataBase;

import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class ControllerRoomPinturas {

    private Context context;

    public ControllerRoomPinturas(Context context) {
        this.context = context;
    }

    public List<Pintura> getPinturas() {
        DataBase dataBase = new DataBase(context);
        return dataBase.getAllPinturas();
    }

    public void addPintura(Pintura... pintura) {
        DataBase dataBase = new DataBase(context);
        dataBase.insertAll(pintura);
    }

    public void removePintura(Pintura pintura) {
        DataBase dataBase = new DataBase(context);
        dataBase.delete(pintura);
    }

    public Pintura getPinturaWithName(String name) {
        DataBase dataBase = new DataBase(context);
        return dataBase.getPinturaWithName(name);
    }

}
