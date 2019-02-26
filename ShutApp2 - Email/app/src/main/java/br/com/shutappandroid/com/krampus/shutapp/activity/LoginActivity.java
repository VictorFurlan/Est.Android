package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.config.ConfiguracaoFirebase;
import br.com.shutappandroid.com.krampus.shutapp.helper.Base65Custom;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;
import br.com.shutappandroid.com.krampus.shutapp.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private String IdUsuarioLogado;

    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.edit_login_email);
        senha = findViewById(R.id.edit_login_senha);
        botaoLogar = findViewById(R.id.botao_logar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });
    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
       if(autenticacao.getCurrentUser() != null) abrirTelaPrincipal();
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    IdUsuarioLogado = Base65Custom.codificarBase64(usuario.getEmail());

                    firebase = ConfiguracaoFirebase.getFirebase()
                            .child("usuarios")
                            .child(IdUsuarioLogado);

                    valueEventListenerMensagem = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Usuario usuario = dataSnapshot.getValue(Usuario.class);

                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            preferencias.salvarDados(IdUsuarioLogado, usuario.getNome());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    firebase.addListenerForSingleValueEvent(valueEventListenerMensagem);

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();

                } else {

                    String erroExcessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcessao = "Email digitado não esta cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcessao = "Senha inconrreta!";
                    } catch (Exception e) {
                        erroExcessao = "Ao efetuar o Login!";
                        e.printStackTrace();
                    }
                        Toast.makeText(LoginActivity.this, "Erro: " + erroExcessao, Toast.LENGTH_LONG).show();
                    }
                }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class );
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class );
        startActivity(intent);
    }

    public static class ConversaActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }
}