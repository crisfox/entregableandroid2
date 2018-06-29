package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.controller.ControllerPinturas;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.ResultListener;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.EscuchadorDePinturas {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private static String ARTIST_ID = "artistId";
    private static String NOMBRE = "nombre";
    private String idDelArtista;
    private String nombreDelArtista;
    private RecyclerViewAdapter.EscuchadorDePinturas escuchadorDePinturas = this;
    private TextView textViewNombreArtistaEnPinturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, ActivityArtist.class);
            startActivity(intent);
            this.finish();
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idDelArtista = bundle.getString(ARTIST_ID);
        nombreDelArtista = bundle.getString(NOMBRE);


        textViewNombreArtistaEnPinturas = findViewById(R.id.textViewNombreArtistaEnPinturas);

        textViewNombreArtistaEnPinturas.setText(nombreDelArtista);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cargarPinturas();

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void cargarPinturas() {
        ControllerPinturas controllerPinturas = new ControllerPinturas();

        final ResultListener<List<Pintura>> escuchadorDeLaVista = new ResultListener<List<Pintura>>() {
            @Override
            public void finish(List<Pintura> resultado) {
                List<Pintura> listaDePinturasPorArtista = new ArrayList<>();

                for (Pintura pintura :
                        resultado) {
                    if (pintura.getArtistId().equals(idDelArtista)) {
                        listaDePinturasPorArtista.add(pintura);
                    }
                }

                recyclerViewAdapter = new RecyclerViewAdapter(listaDePinturasPorArtista, escuchadorDePinturas);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        };
        controllerPinturas.obtenerPinturas(escuchadorDeLaVista);
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
                Intent intentPerfil = new Intent(MainActivity.this, ActivityPerfil.class);
                startActivity(intentPerfil);
                finish();
                return true;
            case R.id.action_cerrarsesion:
                LoginManager.getInstance().logOut();
                Intent intentLogin = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void seleccionaronUnaPintura(Pintura pintura) {
        Intent intent = new Intent(MainActivity.this, ActivityDetalle.class);

        Bundle bundle = new Bundle();
        bundle.putString("nombre", pintura.getName());
        bundle.putString("artistId", pintura.getArtistId());

        intent.putExtras(bundle);
        startActivity(intent);
    }
}