package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dh.entregableandroid2.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityPerfil extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textViewNombreUsuario;
    private TextView textViewEmailUsuario;
    private ImageView imageViewFotoPerfilUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String idDelUsuario = user.getUid();

        if (idDelUsuario == null) {
            Intent intent = new Intent(ActivityPerfil.this, ActivityLogin.class);
            startActivity(intent);
            this.finish();
        }

        Button button = findViewById(R.id.buttonChat);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPerfil.this, ActivityChat.class);
                startActivity(intent);
            }
        });


        Toolbar myToolbar = findViewById(R.id.my_toolbar_perfil);
        setSupportActionBar(myToolbar);


        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = findViewById(R.id.textViewEmailUsuario);
        imageViewFotoPerfilUsuario = findViewById(R.id.imageViewFotoPerfilUsuario);

        textViewNombreUsuario.setText(user.getDisplayName());
        textViewEmailUsuario.setText(user.getEmail());
        Glide.with(ActivityPerfil.this).load(user.getPhotoUrl()).into(imageViewFotoPerfilUsuario);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intentHome = new Intent(ActivityPerfil.this, ActivityArtist.class);
                startActivity(intentHome);
                finish();
                return true;
            case R.id.action_cerrarsesion:
                LoginManager.getInstance().logOut();
                Intent intentLogin = new Intent(ActivityPerfil.this, ActivityLogin.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
