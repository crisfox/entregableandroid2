package com.example.dh.entregableandroid2.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Cristian on 9/7/2018.
 */
@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    private String messageText;

    @ColumnInfo(name = "messageUser")
    private String messageUser;

    @ColumnInfo(name = "messageTime")
    private String messageTime;

    @ColumnInfo(name = "imagenUser")
    private String imagenUser;

    @ColumnInfo(name = "urlImagen")
    private String urlImagen;

    public ChatMessage(String messageText, String urlImagen) {
        this.messageText = messageText;
        this.messageUser = obtenerUsuario().getDisplayName();
        this.imagenUser = obtenerUsuario().getPhotoUrl().toString();
        this.urlImagen = urlImagen;

        // Initialize to current time
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        messageTime = currentDateTimeString;
    }

    @Ignore
    public ChatMessage(String messageText) {
        this.messageText = messageText;
        this.messageUser = obtenerUsuario().getDisplayName();
        this.imagenUser = obtenerUsuario().getPhotoUrl().toString();

        // Initialize to current time
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        messageTime = currentDateTimeString;

    }

    @Ignore
    public ChatMessage() {
        //Para Firebase
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
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


    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    private FirebaseUser obtenerUsuario() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }
}