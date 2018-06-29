package com.example.dh.entregableandroid2.controller;

import com.example.dh.entregableandroid2.model.dao.DAOPinturaInternet;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.ResultListener;

import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class ControllerPinturas {

    public void obtenerPinturas(final ResultListener<List<Pintura>> listenerDeLaVista) {

        ResultListener<List<Pintura>> listenerDelControlador = new ResultListener<List<Pintura>>() {
            @Override
            public void finish(List<Pintura> resultado) {
                listenerDeLaVista.finish(resultado);
            }
        };

        DAOPinturaInternet daoPinturaInternet = new DAOPinturaInternet();

        daoPinturaInternet.obtenerPinturaDeInternet(listenerDelControlador);

    }

}
