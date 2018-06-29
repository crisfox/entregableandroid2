package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.dh.entregableandroid2.R;
import com.facebook.AccessToken;

public class ActivityDetalle extends AppCompatActivity {

    private static String ARTIST_ID = "artistId";
    private static String NOMBRE = "nombre";
    private TextView textViewNombrePinturaDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            Intent intent = new Intent(ActivityDetalle.this, ActivityArtist.class);
            startActivity(intent);
            this.finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String nombre = bundle.getString(NOMBRE);
        String artistId = bundle.getString(ARTIST_ID);

        textViewNombrePinturaDetalle = findViewById(R.id.textViewNombrePinturaDetalle);

        textViewNombrePinturaDetalle.setText(nombre);


    }
}
