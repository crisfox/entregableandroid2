package com.example.dh.entregableandroid2.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dh.entregableandroid2.model.dao.DAOPinturaInternet;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian on 16/7/2018.
 */

public class ControllerPinturas {

    private Context context;
    private List<Pintura> listaDePinturasPorArtista;

    public ControllerPinturas(Context context) {
        this.context = context;
    }

    public void obtenerPinturas(final ResultListener<List<Pintura>> escuchadorVista, final String idArtist) {
        listaDePinturasPorArtista = new ArrayList<>();
        if (hayInternet()) {
            ResultListener<List<Pintura>> escuchadorControlador = new ResultListener<List<Pintura>>() {
                @Override
                public void finish(List<Pintura> resultado) {

                    listaDePinturasPorArtista = filtrarPorArtista(resultado, idArtist);
                    escuchadorVista.finish(listaDePinturasPorArtista);
                }
            };

            DAOPinturaInternet daoPinturaInternet = new DAOPinturaInternet();
            daoPinturaInternet.obtenerPinturaDeInternet(escuchadorControlador);
        } else {
            ControllerRoomPinturas controllerRoomPinturas = new ControllerRoomPinturas(context);

            listaDePinturasPorArtista = filtrarPorArtista(controllerRoomPinturas.getPinturas(), idArtist);
            escuchadorVista.finish(listaDePinturasPorArtista);

        }
    }

    private List<Pintura> filtrarPorArtista(List<Pintura> listaDePinturasObtenidas, String idDelArtista) {
        ControllerRoomPinturas controllerRoomPinturas = new ControllerRoomPinturas(context);
        for (Pintura pintura : listaDePinturasObtenidas) {
            if (pintura.getArtistId().equals(idDelArtista)) {
                listaDePinturasPorArtista.add(pintura);
                controllerRoomPinturas.removePintura(pintura);
                controllerRoomPinturas.addPintura(pintura);
            }
        }
        return listaDePinturasPorArtista;
    }

    private boolean hayInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
