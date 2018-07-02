package com.example.dh.entregableandroid2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Pintura> listaDePinturas;
    private EscuchadorDePinturas escuchadorDePinturas;


    private ImageView imageViewFotoPintura;


    public RecyclerViewAdapter(List<Pintura> listaDePinturas, EscuchadorDePinturas escuchadorDePinturas) {
        this.escuchadorDePinturas = escuchadorDePinturas;
        this.listaDePinturas = listaDePinturas;
    }

    @NonNull
    @Override
    public PinturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.celda_pinturas, parent, false);
        PinturasViewHolder pinturasViewHolder = new PinturasViewHolder(itemView);

        return pinturasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Pintura pintura = listaDePinturas.get(position);
        PinturasViewHolder pinturasViewHolder = (PinturasViewHolder) holder;
        pinturasViewHolder.cargarDatos(pintura);
    }

    @Override
    public int getItemCount() {
        return listaDePinturas.size();
    }

    public class PinturasViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private LinearLayout layoutParaElListenerPintura;

        public PinturasViewHolder(View itemView) {
            super(itemView);

            imageViewFotoPintura = itemView.findViewById(R.id.imageViewFotoPintura);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            layoutParaElListenerPintura = itemView.findViewById(R.id.layoutParaElListenerPintura);
            layoutParaElListenerPintura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer posicionDeLaCelda = getAdapterPosition();
                    Pintura pintura = listaDePinturas.get(posicionDeLaCelda);
                    escuchadorDePinturas.seleccionaronUnaPintura(pintura);
                }
            });
        }

        public void cargarDatos(Pintura pintura) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef;

            imageRef = storageRef.child("artistpaints").child("andymilanapo.png");
            textViewNombre.setText(pintura.getName());



            File localFile = null;
            try {
                localFile = File.createTempFile("images", "jpg");
                final File finalLocalFile = localFile;
                imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Local temp file has been created
                        Bitmap bitmapDeImagen = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                        imageViewFotoPintura.setImageBitmap(bitmapDeImagen);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public interface EscuchadorDePinturas {
        void seleccionaronUnaPintura(Pintura pintura);
    }


}
