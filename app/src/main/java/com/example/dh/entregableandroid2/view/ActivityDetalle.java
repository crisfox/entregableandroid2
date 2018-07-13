package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetalle extends AppCompatActivity {

    private static String ARTIST_ID = "artistId";
    private static String NOMBRE = "nombre";
    private static String FOTO = "foto";

    private TextView textViewNombrePinturaDetalle;
    private ImageView imageViewFotoPinturaDetalle;
    private String artistId;

    private List<Artist> listaDeArtistas;
    private Artist artistaEncontrado;

    private TextView textViewNombreArtistaDetalle;
    private TextView textViewDescripcionArtistaDetalle;
    private ImageView imageViewArtistaDetalle;
    private TextView textViewNacionalidadArtistaDetalle;
    private TextView textViewInfluencedArtistaDetalle;
    private FirebaseStorage storage;

    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (!isLoggedIn) {
            Intent intent = new Intent(ActivityDetalle.this, ActivityArtist.class);
            startActivity(intent);
            this.finish();
        }

        leerListaDeArtistas();
        myToolbar = findViewById(R.id.my_toolbar_detalle);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String nombre = bundle.getString(NOMBRE);
        artistId = bundle.getString(ARTIST_ID);
        String foto = bundle.getString(FOTO);


        storage = FirebaseStorage.getInstance();

        imageViewFotoPinturaDetalle = findViewById(R.id.imageViewFotoPinturaDetalle);
        textViewNombrePinturaDetalle = findViewById(R.id.textViewNombrePinturaDetalle);
        textViewNombrePinturaDetalle.setText(nombre);
        StorageReference storageRefPintura = storage.getReference().child(foto);
        Glide.with(ActivityDetalle.this).using(new FirebaseImageLoader()).load(storageRefPintura).into(imageViewFotoPinturaDetalle);



        imageViewArtistaDetalle = findViewById(R.id.imageViewFotoArtistaDetalle);
        textViewNombreArtistaDetalle = findViewById(R.id.textViewNombreArtistaDetalle);
        textViewDescripcionArtistaDetalle = findViewById(R.id.textViewDescripcionArtistaDetalle);
        textViewNacionalidadArtistaDetalle = findViewById(R.id.textViewNacionalidadArtistaDetalle);
        textViewInfluencedArtistaDetalle = findViewById(R.id.textViewInfluencedArtistaDetalle);



    }

    public void leerListaDeArtistas() {

        DatabaseReference mDatabase;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

        DatabaseReference reference = mDatabase.child("artists");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaDeArtistas = new ArrayList<>();

                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {

                    Artist artist = dataSnapshotChild.getValue(Artist.class);
                    listaDeArtistas.add(artist);

                }

                for (Artist artista : listaDeArtistas) {
                    if (artistId.equals(artista.getArtistId())){
                        artistaEncontrado = artista;
                        break;
                    }
                }
                textViewNombreArtistaDetalle.setText(artistaEncontrado.getName());
                textViewDescripcionArtistaDetalle.setText(artistaEncontrado.getDescripcion());
                textViewNacionalidadArtistaDetalle.setText(artistaEncontrado.getNationality());
                textViewInfluencedArtistaDetalle.setText(artistaEncontrado.getInfluenced_by());

                StorageReference storageRefArtista = storage.getReference().child(artistaEncontrado.getImagen());
                Glide.with(ActivityDetalle.this).using(new FirebaseImageLoader()).load(storageRefArtista).into(imageViewArtistaDetalle);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        reference.addListenerForSingleValueEvent(valueEventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_perfil:
                Intent intentPerfil = new Intent(ActivityDetalle.this, ActivityPerfil.class);
                startActivity(intentPerfil);
                return true;
            case R.id.action_cerrarsesion:
                LoginManager.getInstance().logOut();
                Intent intentLogin = new Intent(ActivityDetalle.this, ActivityLogin.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
