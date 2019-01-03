package autenticacaousuario.com.krampus.autenticacaousuario;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signOut();
        if( firebaseAuth.getCurrentUser() != null ){
            Log.i("verificanUser","Sucesso Usuario logado");
        }else{
            Log.i("verificanUser","Erro Usuario não logado");
        }




        /*
        firebaseAuth.signInWithEmailAndPassword("victor@teste.com","123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("signInUser","Sucesso ao criar Usuario");
                }else{
                    Log.i("signInUser","Erro ao criar Usuario");
                }
            }
        });
        */




        /*
        firebaseAuth.createUserWithEmailAndPassword("victor@teste.com","123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("createUser","Sucesso ao criar Usuario");
                        }else{
                            Log.i("createUser","Erro ao criar Usuario");
                        }
                    }
                });
        */
    }
}
