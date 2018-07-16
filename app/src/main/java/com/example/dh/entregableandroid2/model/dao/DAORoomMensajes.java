package com.example.dh.entregableandroid2.model.dao;

/**
 * Created by Cristian on 14/7/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dh.entregableandroid2.model.pojo.ChatMessage;

import java.util.List;

@Dao
public interface DAORoomMensajes {
    @Query("SELECT * FROM ChatMessage")
    List<ChatMessage> getAllMensajes();

    @Query("SELECT * FROM ChatMessage WHERE messageText Like :messageText")
    ChatMessage getChatMensaggeWithText(String messageText);

    @Insert
    void insertAll(ChatMessage... chatMessages);

    @Delete
    void delete(ChatMessage chatMessage);
}