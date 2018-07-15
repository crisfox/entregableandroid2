package com.example.dh.entregableandroid2.controller;

import android.content.Context;

import com.example.dh.entregableandroid2.model.dao.DAOPinturaInternet;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.DataBase;
import com.example.dh.entregableandroid2.util.ResultListener;

import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class ControllerPinturas {

    private Context context;

    public ControllerPinturas(Context context) {
        this.context = context;
    }

    public List<Pintura> getPintura(){
        DataBase dataBase = new DataBase(context);
        return dataBase.getAllPinturas();
    }

    public void addPintura(Pintura pintura){
        DataBase dataBase = new DataBase(context);
        dataBase.insertAll(pintura);
    }
    public void removePintura(Pintura pintura){
        DataBase dataBase = new DataBase(context);
        dataBase.delete(pintura);
    }
    public Pintura getPinturaWithName(String name){
        DataBase dataBase = new DataBase(context);
        return dataBase.getPinturaWithName(name);
    }




    public void obtenerPinturas(final ResultListener<List<Pintura>> listenerDeLaVista) {


            ResultListener<List<Pintura>> listenerDelControlador = new ResultListener<List<Pintura>>() {
                @Override
                public void finish(List<Pintura> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            };
        if(true){
            DAOPinturaInternet daoPinturaInternet = new DAOPinturaInternet();

            daoPinturaInternet.obtenerPinturaDeInternet(listenerDelControlador);
        }else{



        }



    }

}
