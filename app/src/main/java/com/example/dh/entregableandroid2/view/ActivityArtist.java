package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityArtist extends AppCompatActivity implements RecyclerViewAdapterArtistas.EscuchadorDeArtista {
    private TextView textViewNombreArtist;
    private TextView textViewNacionalidadArtist;
    private TextView textViewEmailFacebook;
    private Button buttonCerrarSesion;

    private RecyclerViewAdapterArtistas recyclerViewAdapterArtistas;
    private RecyclerView recyclerView;
    private List<Artist> listaDeArtistas;
    private RecyclerViewAdapterArtistas.EscuchadorDeArtista escuchadorDeArtista = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (!isLoggedIn) {
            Intent intent = new Intent(ActivityArtist.this, ActivityLogin.class);
            startActivity(intent);
            this.finish();
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        leerListaDeArtistas();


        recyclerView = findViewById(R.id.recyclerViewArtist);


    }


    public void EscribirArtista(View view) {
        DatabaseReference mDatabase;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

        Mensaje unMensaje = new Mensaje("Este es un mensaje", "Cristian");
        mDatabase.setValue(unMensaje);

    }

    public void leerArtista() {
        DatabaseReference mDatabase;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        DatabaseReference reference = mDatabase.child("artists").child("1");

        ValueEventListener artistListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Artist artist = dataSnapshot.getValue(Artist.class);
                Toast.makeText(ActivityArtist.this, artist.toString(), Toast.LENGTH_LONG).show();

                textViewNombreArtist.setText(artist.getName());
                textViewNacionalidadArtist.setText(artist.getNationality());

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        };
        reference.addListenerForSingleValueEvent(artistListener);
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
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ActivityArtist.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerViewAdapterArtistas = new RecyclerViewAdapterArtistas(listaDeArtistas, escuchadorDeArtista);
                recyclerView.setAdapter(recyclerViewAdapterArtistas);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        reference.addListenerForSingleValueEvent(valueEventListener);

    }

    @Override
    public void seleccionarAlArtista(Artist artist) {
        Intent intent = new Intent(ActivityArtist.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("artistId", artist.getArtistId());
        bundle.putString("nombre", artist.getName());

        intent.putExtras(bundle);
        startActivity(intent);
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
                Intent intentPerfil = new Intent(ActivityArtist.this, ActivityPerfil.class);
                startActivity(intentPerfil);
                finish();
                return true;
            case R.id.action_cerrarsesion:
                LoginManager.getInstance().logOut();
                Intent intentLogin = new Intent(ActivityArtist.this, ActivityLogin.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}