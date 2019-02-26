package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.adapter.MensagemAdapter;
import br.com.shutappandroid.com.krampus.shutapp.config.ConfiguracaoFirebase;
import br.com.shutappandroid.com.krampus.shutapp.helper.Base65Custom;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;
import br.com.shutappandroid.com.krampus.shutapp.model.Conversa;
import br.com.shutappandroid.com.krampus.shutapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    private String nomeUsuarioDestino;
    private String idUsuarioDestino;

    private String nomeUsuarioRemetente;
    private String idUsuarioRemetente;

    private EditText editText;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ListView listView;
    private ValueEventListener valueEventListenerMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tb_conversa);
        editText = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);
        listView = findViewById(R.id.lv_conversas);


        //RECUPERA O ID DO USUARIO LOGADO
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

        Bundle extra = getIntent().getExtras();

        //RECUPERA DADOS DO BUNDLE
        if(extra != null){

            nomeUsuarioDestino = extra.get("nome").toString();
            String emailDestino = extra.get("email").toString();
            idUsuarioDestino = Base65Custom.codificarBase64(emailDestino);
        }

        //MONTA TOOLBAR
        toolbar.setTitle(nomeUsuarioDestino);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //MONTA ADAPTER PARA MENSAGENS DA CONVERSA
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter(adapter);

        //RECUPERA MENSAGENS DO FIREBASE
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child(idUsuarioRemetente)
                .child(idUsuarioDestino);

        //CRIA LISTENER PARA MENSAGENS
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //LIMPA MENSAGENS
                mensagens.clear();

                //RECUPERA MENSAGEM
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagem);

        //BOTAO ENVIAR DAS MENSAGENS DE CONERSA
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtMensagem = editText.getText().toString();

                if(txtMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(txtMensagem);

                    //SALVA MENSAGEM

                    //para remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente, idUsuarioDestino, mensagem);
                    if(!retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this,
                                "Erro! Tente enviar a mensagem novamente.", Toast.LENGTH_LONG).show();
                    }else{
                    // para destino
                        Boolean retornoMensagemDestino = salvarMensagem(idUsuarioDestino, idUsuarioRemetente, mensagem);
                        if(!retornoMensagemRemetente){
                            Toast.makeText(ConversaActivity.this,
                                    "Erro! Tente enviar a mensagem novamente.", Toast.LENGTH_LONG).show();
                        }
                    }

                    //SALVAR CONVERSA

                    //para remetente
                    Conversa conversa = new Conversa(idUsuarioDestino,nomeUsuarioDestino, txtMensagem);
                    Boolean retornoConversaRemetente = salvarConversa(idUsuarioRemetente, idUsuarioDestino, conversa);
                    if(!retornoConversaRemetente){
                        Toast.makeText(ConversaActivity.this,
                                "Erro! Tente enviar a mensagem novamente.", Toast.LENGTH_LONG).show();
                    }else{
                     //para destino
                        conversa = new Conversa(idUsuarioRemetente, nomeUsuarioRemetente, txtMensagem);
                        Boolean retornoConversaDestino = salvarConversa(nomeUsuarioDestino, idUsuarioRemetente, conversa);
                        if(!retornoConversaDestino){
                            Toast.makeText(ConversaActivity.this,
                                    "Erro! Tente enviar a mensagem novamente.", Toast.LENGTH_LONG).show();
                        }

                    }
                    editText.setText("");
                }
            }
        });
    }

    //SALVA AS MENSAGENS DA CONVERSA NO FIREBASE
    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push() /*gera um auto incremente */
                    .setValue(mensagem);


            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //SALVA AS CONVERSAS
    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){
        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("conversas");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
