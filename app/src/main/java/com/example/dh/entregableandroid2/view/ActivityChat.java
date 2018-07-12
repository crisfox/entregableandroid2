package com.example.dh.entregableandroid2.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dh.entregableandroid2.R;
import com.example.dh.entregableandroid2.model.pojo.Artist;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityChat extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private List<ChatMessage> listaDeMensajes;
    private RecyclerViewAdapterMensajes recyclerViewAdapterMensajes;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String idDelUsuario = user.getUid();

        recyclerView = findViewById(R.id.recyclerViewDeLosMensajes);
        leerListaDeMensajes();

        databaseReference = FirebaseDatabase.getInstance().getReference("mensajes");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                recyclerViewAdapterMensajes.addMensaje(chatMessage);
                recyclerView.scrollToPosition(recyclerViewAdapterMensajes.getItemCount() - 1);
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

        FloatingActionButton floatingActionButtonEnviar = findViewById(R.id.fab);

        floatingActionButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextCampoChat = findViewById(R.id.editTextMensaje);


                String getTextEditText = editTextCampoChat.getText().toString();
                ChatMessage chatMessage = new ChatMessage(getTextEditText, user.getDisplayName().toString());


                recyclerViewAdapterMensajes.addMensaje(chatMessage);


                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("mensajes")
                        .push()
                        .setValue(chatMessage);

                editTextCampoChat.setText("");

                recyclerView.scrollToPosition(recyclerViewAdapterMensajes.getItemCount() - 1);

            }
        });
    }

    public void leerListaDeMensajes() {

        seteoDeRecycler();

    }

    public void seteoDeRecycler() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityChat.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterMensajes = new RecyclerViewAdapterMensajes();
        recyclerView.setAdapter(recyclerViewAdapterMensajes);

        recyclerViewAdapterMensajes.notifyDataSetChanged();
    }

}
