package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.controller.ControllerArtists;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.example.dh.entregableandroid2.util.ResultListener;
import com.example.dh.entregableandroid2.view.Adapters.RecyclerViewAdapterArtistas;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.List;

public class ActivityArtist extends AppCompatActivity implements RecyclerViewAdapterArtistas.EscuchadorDeArtista {

    private RecyclerViewAdapterArtistas recyclerViewAdapterArtistas;
    private RecyclerView recyclerView;


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
            return;
        }
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = findViewById(R.id.recyclerViewArtist);
        recyclerViewAdapterArtistas = new RecyclerViewAdapterArtistas(this);
        recyclerView.setAdapter(recyclerViewAdapterArtistas);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        leerListaDeArtistas();


    }

    public void leerListaDeArtistas() {

        final ControllerArtists controllerArtists = new ControllerArtists(getApplicationContext());
        controllerArtists.obtenerArtistas(new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                recyclerViewAdapterArtistas.setArtistas(resultado);
            }
        });
    }

    @Override
    public void seleccionarAlArtista(Artist artist) {
        Intent intent = new Intent(ActivityArtist.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("artistId", artist.getArtistId());
        bundle.putString("nombre", artist.getName());
        bundle.putString("fotoArtista", artist.getImagen());

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
