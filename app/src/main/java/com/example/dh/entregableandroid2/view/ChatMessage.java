package com.example.dh.entregableandroid2.view;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by Cristian on 9/7/2018.
 */

public class ChatMessage {


    private String messageText;
    private String messageUser;
    private long messageTime;
    private String imagenUser;
    private String urlImagen;

    public ChatMessage(String messageText, String urlImagen) {
        this.messageText = messageText;
        this.messageUser = obtenerUsuario().getDisplayName();
        this.imagenUser = obtenerUsuario().getPhotoUrl().toString();
        this.urlImagen = urlImagen;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(String messageText) {
        this.messageText = messageText;
        this.messageUser = obtenerUsuario().getDisplayName();
        this.imagenUser = obtenerUsuario().getPhotoUrl().toString();

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getImagenUser() {
        return imagenUser;
    }

    public void setImagenUser(String imagenUser) {
        this.imagenUser = imagenUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    private FirebaseUser obtenerUsuario(){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }
}