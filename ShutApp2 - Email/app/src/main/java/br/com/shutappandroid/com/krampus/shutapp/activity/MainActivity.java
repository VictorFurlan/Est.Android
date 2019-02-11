package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.helper.*;
import br.com.shutappandroid.com.krampus.shutapp.adapter.TabAtapter;
import br.com.shutappandroid.com.krampus.shutapp.config.ConfiguracaoFirebase;
import br.com.shutappandroid.com.krampus.shutapp.model.Contato;
import br.com.shutappandroid.com.krampus.shutapp.model.Usuario;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioAutenticacao;
    private SlidingTapLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ShutApp");
        setSupportActionBar( toolbar );

        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        //config sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //config adapter
        TabAtapter tabAtapter = new TabAtapter( getSupportFragmentManager() );
        viewPager.setAdapter(tabAtapter);

        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do Contato");
        alertDialog.setCancelable(false); // --------- ao clicar fora do janela não fecha

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        // ---------- config botao positivo(CADASTRAR)
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();

                if(emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this,"Preencha o e-mail!",Toast.LENGTH_LONG).show();
                }
                else{
                    // ---------- verificar se o usuario ja esta cadastrado
                    identificadorContato = Base65Custom.codificarBase64(emailContato);

                    firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if( dataSnapshot.getValue() != null ){

                                // ---------- recupera o ID do usuario logado
                                Preferencias preferencias = new Preferencias(MainActivity.this);

                                // ---------- recupera nova instancia do fire base
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                // ---------- recupera dados do contato a ser adicionado a lista de contatos
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);


                                // ------ Adiciona os e-mails na lista de contatos do usuario logado
                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase.child("contatos")
                                                    .child(identificadorUsuarioLogado)
                                                     .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdContato( identificadorContato );
                                contato.setEmail( usuarioContato.getEmail() );
                                contato.setNome( usuarioContato.getNome() );

                                firebase.setValue(contato);

                            }else{
                                Toast.makeText(MainActivity.this,"Este e-mail não esta cadastrado!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
        // -------------- config botao negativo(CANCELAR)
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUsuario(){
        usuarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class );
        startActivity(intent);
        finish();
    }
}
