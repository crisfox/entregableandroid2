package com.example.dh.entregableandroid2.controller;

import android.content.Context;

import com.example.dh.entregableandroid2.model.pojo.ChatMessage;
import com.example.dh.entregableandroid2.util.DataBase;

import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class ControllerRoomMensajes {

    private Context context;

    public ControllerRoomMensajes(Context context) {
        this.context = context;
    }

    public List<ChatMessage> getMensajes() {
        DataBase dataBase = new DataBase(context);
        return dataBase.getAllMensajes();
    }

    public void addMensajeAlRoom(ChatMessage... chatMessages) {
        DataBase dataBase = new DataBase(context);
        dataBase.insertAll(chatMessages);
    }

    public void removeMensaje(ChatMessage chatMessages) {
        DataBase dataBase = new DataBase(context);
        dataBase.delete(chatMessages);
    }

    public ChatMessage getChatMensaggeWithText(String texto) {
        DataBase dataBase = new DataBase(context);
        return dataBase.getChatMensaggeWithText(texto);
    }

}
