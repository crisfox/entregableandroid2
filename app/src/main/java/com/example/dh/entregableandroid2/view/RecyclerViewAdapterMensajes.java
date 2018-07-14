package com.example.dh.entregableandroid2.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by DH on 11/6/2018.
 */

public class RecyclerViewAdapterMensajes extends RecyclerView.Adapter {

    private List<ChatMessage> listaDeMensajes;
    private Context context;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public RecyclerViewAdapterMensajes() {
        this.listaDeMensajes = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
       if(listaDeMensajes.get(position).getMessageUser().equals(user.getDisplayName())) {

           return R.layout.message_user;

       }else {
           return R.layout.message;
       }

    }

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(viewType, parent, false);
        MensajesViewHolder mensajesViewHolder = new MensajesViewHolder(itemView);

        return mensajesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = listaDeMensajes.get(position);
        MensajesViewHolder mensajesViewHolder = (MensajesViewHolder) holder;
        mensajesViewHolder.cargarDatos(chatMessage);

    }

    @Override
    public int getItemCount() {
        return listaDeMensajes.size();
    }

    public class MensajesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUser;
        private TextView textViewMensaje;
        private TextView textViewTiempo;
        private ImageView imageViewUser;
        private ImageView imageViewImagenEnviadaMensaje;

        public MensajesViewHolder(View itemView) {
            super(itemView);

            textViewUser = itemView.findViewById(R.id.message_user);
            textViewMensaje = itemView.findViewById(R.id.message_text);
            textViewTiempo = itemView.findViewById(R.id.message_time);
            imageViewUser = itemView.findViewById(R.id.imageViewUser);
            imageViewImagenEnviadaMensaje = itemView.findViewById(R.id.imageViewImagenEnviada);


        }

        public void cargarDatos(ChatMessage chatMessage) {

            textViewUser.setText(chatMessage.getMessageUser());
            textViewMensaje.setText(chatMessage.getMessageText());

            Glide.with(context).load(chatMessage.getImagenUser()).into(imageViewUser);
            textViewTiempo.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                    chatMessage.getMessageTime()));
            if (chatMessage.getUrlImagen() != null){
                imageViewImagenEnviadaMensaje.setVisibility(View.VISIBLE);
                textViewMensaje.setVisibility(View.GONE);
                Glide.with(context).load(chatMessage.getUrlImagen()).into(imageViewImagenEnviadaMensaje);
            }else{
                imageViewImagenEnviadaMensaje.setVisibility(View.GONE);
            }

        }

    }

    public void addMensaje(ChatMessage unMensaje){
        listaDeMensajes.add(unMensaje);
        notifyItemChanged(listaDeMensajes.size());
    }



}
