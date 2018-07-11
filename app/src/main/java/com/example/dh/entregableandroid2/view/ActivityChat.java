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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String idDelUsuario = user.getUid();

        leerListaDeMensajes();

        recyclerView = findViewById(R.id.recyclerViewDeLosMensajes);


        FloatingActionButton floatingActionButtonEnviar = findViewById(R.id.fab);

        floatingActionButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextCampoChat = findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                String nombreDelUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();


                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("mensajes")
                        .push()
                        .setValue(new ChatMessage(editTextCampoChat.getText().toString(),nombreDelUsuario)
                        );

                // Clear the input
                editTextCampoChat.setText("");
                recyclerViewAdapterMensajes.notifyDataSetChanged();

            }
        });

    }

    public void leerListaDeMensajes() {

        DatabaseReference mDatabase;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

        DatabaseReference reference = mDatabase.child("mensajes");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaDeMensajes = new ArrayList<>();

                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {

                    ChatMessage chatMessage = dataSnapshotChild.getValue(ChatMessage.class);
                    listaDeMensajes.add(chatMessage);

                }

                seteoDeRecycler();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        reference.addListenerForSingleValueEvent(valueEventListener);



    }

    public void seteoDeRecycler(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityChat.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterMensajes = new RecyclerViewAdapterMensajes(listaDeMensajes);
        recyclerView.setAdapter(recyclerViewAdapterMensajes);

        recyclerViewAdapterMensajes.notifyDataSetChanged();
    }

}
