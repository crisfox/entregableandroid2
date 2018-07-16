package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.controller.ControllerMensajes;
import com.example.dh.entregableandroid2.controller.ControllerRoomMensajes;
import com.example.dh.entregableandroid2.model.pojo.ChatMessage;
import com.example.dh.entregableandroid2.util.ResultListener;
import com.example.dh.entregableandroid2.view.Adapters.RecyclerViewAdapterMensajes;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class ActivityChat extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private RecyclerViewAdapterMensajes recyclerViewAdapterMensajes;
    private RecyclerView recyclerView;
    private EditText editTextCampoChat;
    private FloatingActionButton floatingActionButtonEnviar;
    private FloatingActionButton floatingActionButtonEnviarImagen;
    private static final int PHOTO_SEND = 1;
    private ControllerMensajes controllerMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String idDelUsuario = user.getUid();

        if (idDelUsuario == null) {
            Intent intent = new Intent(ActivityChat.this, ActivityLogin.class);
            startActivity(intent);
            this.finish();
        }

        controllerMensajes = new ControllerMensajes(getApplicationContext());
        Toolbar myToolbar = findViewById(R.id.my_toolbar_chat);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        editTextCampoChat = findViewById(R.id.editTextMensaje);
        recyclerView = findViewById(R.id.recyclerViewDeLosMensajes);
        floatingActionButtonEnviar = findViewById(R.id.fab);
        floatingActionButtonEnviarImagen = findViewById(R.id.fabImagen);


        databaseReference = FirebaseDatabase.getInstance().getReference("mensajes");
        firebaseStorage = FirebaseStorage.getInstance();


        editTextCampoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollHaciaElUltimoMensaje();
            }
        });

        seteoDeRecycler();
        leerListaDeMensajes();

        floatingActionButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String getTextEditText = editTextCampoChat.getText().toString();

                if (getTextEditText.length() >= 1) {
                    ChatMessage chatMessage = new ChatMessage(getTextEditText);

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("mensajes")
                            .push()
                            .setValue(chatMessage);

                    ControllerRoomMensajes controllerRoomMensajes = new ControllerRoomMensajes(getApplicationContext());

                    controllerRoomMensajes.addMensajeAlRoom(chatMessage);
                    editTextCampoChat.setText("");
                    leerListaDeMensajes();

                    recyclerViewAdapterMensajes.notifyDataSetChanged();

                    scrollHaciaElUltimoMensaje();
                } else {
                    Toast.makeText(ActivityChat.this, "El mensaje debe contener almenos un caracter", Toast.LENGTH_SHORT).show();
                }


            }
        });

        floatingActionButtonEnviarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una Imagen"), PHOTO_SEND);
            }
        });
    }


    public void leerListaDeMensajes() {

        controllerMensajes.obtenerMensajes(new ResultListener<List<ChatMessage>>() {
            @Override
            public void finish(List<ChatMessage> resultado) {
                recyclerViewAdapterMensajes.setMensajes(resultado);
                scrollHaciaElUltimoMensaje();
                mantenerLaVistaDelRecyclerHaciaArriba();
            }
        });
    }


    public void seteoDeRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityChat.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterMensajes = new RecyclerViewAdapterMensajes();
        recyclerView.setAdapter(recyclerViewAdapterMensajes);
    }

    public void scrollHaciaElUltimoMensaje() {

        recyclerView.scrollToPosition(recyclerViewAdapterMensajes.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            storageReference = firebaseStorage.getReference("imagenes_chat");
            final StorageReference imagenReferencia = storageReference.child(uri.getLastPathSegment());
            imagenReferencia.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    Uri uri = taskSnapshot.getDownloadUrl();
                    ChatMessage chatMessage = new ChatMessage(user.getDisplayName() + " ha enviado una foto.", uri.toString());
                    databaseReference.push().setValue(chatMessage);
                }
            });

        }

    }

    private void mantenerLaVistaDelRecyclerHaciaArriba() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                Intent intentPerfil = new Intent(ActivityChat.this, ActivityPerfil.class);
                startActivity(intentPerfil);
                return true;
            case R.id.action_cerrarsesion:
                LoginManager.getInstance().logOut();
                Intent intentLogin = new Intent(ActivityChat.this, ActivityLogin.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
