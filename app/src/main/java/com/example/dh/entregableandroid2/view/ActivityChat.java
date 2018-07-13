package com.example.dh.entregableandroid2.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dh.entregableandroid2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
        scrollHaciaElUltimoMensaje();




        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                recyclerViewAdapterMensajes.addMensaje(chatMessage);
                scrollHaciaElUltimoMensaje();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        floatingActionButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String getTextEditText = editTextCampoChat.getText().toString();

                if (getTextEditText.length() >= 1){
                    ChatMessage chatMessage = new ChatMessage(getTextEditText);

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("mensajes")
                            .push()
                            .setValue(chatMessage);

                    editTextCampoChat.setText("");

                    scrollHaciaElUltimoMensaje();
                }else {
                    Toast.makeText(ActivityChat.this,"El mensaje debe contener almenos un caracter", Toast.LENGTH_SHORT).show();
                }


            }
        });

        floatingActionButtonEnviarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una Imagen"), PHOTO_SEND);
            }
        });
    }

    public void seteoDeRecycler() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityChat.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterMensajes = new RecyclerViewAdapterMensajes();
        recyclerView.setAdapter(recyclerViewAdapterMensajes);

        recyclerViewAdapterMensajes.notifyDataSetChanged();
    }

    public void scrollHaciaElUltimoMensaje(){

        recyclerView.scrollToPosition(recyclerViewAdapterMensajes.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri uri = data.getData();
            storageReference = firebaseStorage.getReference("imagenes_chat");
            final StorageReference imagenReferencia = storageReference.child(uri.getLastPathSegment());
            imagenReferencia.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    Uri uri = taskSnapshot.getDownloadUrl();
                    ChatMessage chatMessage = new ChatMessage(user.getDisplayName() + " ha enviado una foto.",uri.toString());
                    databaseReference.push().setValue(chatMessage);
                }
            });

        }

    }
}
