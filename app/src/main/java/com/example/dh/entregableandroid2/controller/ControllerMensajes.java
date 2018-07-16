package com.example.dh.entregableandroid2.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.dh.entregableandroid2.model.pojo.ChatMessage;
import com.example.dh.entregableandroid2.util.ResultListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian on 16/7/2018.
 */

public class ControllerMensajes {

    Context context;

    public ControllerMensajes(Context context) {
        this.context = context;
    }

    public void obtenerMensajes(final ResultListener<List<ChatMessage>> escuchadorVista) {

        if (hayInternet()) {                                                                          //Si hay internet
            final ArrayList<ChatMessage> listado = new ArrayList<>();
            DatabaseReference mDataBase;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            mDataBase = firebaseDatabase.getReference();
            DatabaseReference reference = mDataBase.child("mensajes");
            ValueEventListener valueEventListener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ControllerRoomMensajes controllerRoomMensajes = new ControllerRoomMensajes(context);
                    for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                        ChatMessage chatMessageLeido = dataSnapshotChild.getValue(ChatMessage.class);
                        listado.add(chatMessageLeido);

                        controllerRoomMensajes.removeMensaje(chatMessageLeido);
                        controllerRoomMensajes.addMensajeAlRoom(chatMessageLeido);
                    }
                    escuchadorVista.finish(listado);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(context, "paso algoo", Toast.LENGTH_SHORT).show();
                }

            };
            reference.addValueEventListener(valueEventListener);

        } else {                                                                                    //Si no hay internet
            ControllerRoomMensajes controllerRoomMensajes = new ControllerRoomMensajes(context);
            escuchadorVista.finish(controllerRoomMensajes.getMensajes());
        }
    }

    private boolean hayInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(context, "Hay internet", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "No hay internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
