package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

import br.com.shutappandroid.com.krampus.shutapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText numDDD;
    private EditText numPais;
    private Button botaoCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.editTextNome);
        telefone = findViewById(R.id.edit_telefone);
        numDDD = findViewById(R.id.edit_DDD);
        numPais = findViewById(R.id.edit_Pais);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("N NNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        SimpleMaskFormatter simpleMaskDDD = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskDDD = new MaskTextWatcher(numDDD,simpleMaskDDD);
        numDDD.addTextChangedListener(maskDDD);

        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskPais = new MaskTextWatcher(numPais,simpleMaskPais);
        numPais.addTextChangedListener(maskPais);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telCompleto = numPais.getText().toString() +
                                     numDDD.getText().toString() +
                                     telefone.getText().toString();
                String telSemFormatacao = telCompleto.replace("+","");
                telSemFormatacao = telSemFormatacao.replace("-","");
                telSemFormatacao = telSemFormatacao.replace(" ","");

                //Log.i("Telefone: - ", "T - "+telSemFormatacao);

                //Exemplo de COD verificacao
                Random randomico = new Random();
                int numeroRandom = randomico.nextInt(9999-1000) + 1000;
                String token = String.valueOf(numeroRandom);


            }
        });
    }
}
