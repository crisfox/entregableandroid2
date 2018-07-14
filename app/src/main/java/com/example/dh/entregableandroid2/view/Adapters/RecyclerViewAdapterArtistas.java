package com.example.dh.entregableandroid2.view.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;

/**
 * Created by DH on 11/6/2018.
 */

public class RecyclerViewAdapterArtistas extends RecyclerView.Adapter {

    private List<Artist> listaDeArtistas;
    private EscuchadorDeArtista escuchadorDeArtista;

    private ImageView imageViewFotoArtista;
    private Context context;



    public RecyclerViewAdapterArtistas(List<Artist> listaDeArtistas, EscuchadorDeArtista escuchadorDeArtista) {
        this.listaDeArtistas = listaDeArtistas;
        this.escuchadorDeArtista = escuchadorDeArtista;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.celda_artistas, parent, false);
        ArtistViewHolder artistViewHolder = new ArtistViewHolder(itemView);

        return artistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Artist artist = listaDeArtistas.get(position);
        ArtistViewHolder artistasViewHolder = (ArtistViewHolder) holder;
        artistasViewHolder.cargarDatos(artist);
    }

    @Override
    public int getItemCount() {
        return listaDeArtistas.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombreArtist;
        private TextView textViewNacionalidadArtist;
        private LinearLayout linearLayoutParaListener;

        public ArtistViewHolder(View itemView) {
            super(itemView);

            textViewNombreArtist = itemView.findViewById(R.id.textViewNombreArtist);
            imageViewFotoArtista = itemView.findViewById(R.id.imageViewFotoArtista);
            textViewNacionalidadArtist = itemView.findViewById(R.id.textViewNacionalidadArtist);
            linearLayoutParaListener = itemView.findViewById(R.id.layoutParaElListener);

            linearLayoutParaListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer posicionDeLaCelda = getAdapterPosition();
                    Artist artist = listaDeArtistas.get(posicionDeLaCelda);
                    escuchadorDeArtista.seleccionarAlArtista(artist);
                }
            });


        }

        public void cargarDatos(Artist artist) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child(artist.getImagen());


            Glide.with(context).using(new FirebaseImageLoader()).load(storageRef).into(imageViewFotoArtista);

            textViewNombreArtist.setText(artist.getName());
            textViewNacionalidadArtist.setText(artist.getNationality());

        }

    }

    public void setArtistas(List<Artist> listaDeArtistas){
        this.listaDeArtistas.clear();
        this.listaDeArtistas.addAll(listaDeArtistas);
        notifyDataSetChanged();
    }

    public interface EscuchadorDeArtista {
        public void seleccionarAlArtista(Artist artist);
    }


}
