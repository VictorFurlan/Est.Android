package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = findViewById(R.id.edit_cond_validacao);
        validar = findViewById(R.id.botao_validar);

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskCodValidacao = new MaskTextWatcher(codigoValidacao,simpleMaskCodigoValidacao);
        codigoValidacao.addTextChangedListener(maskCodValidacao);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferencias preferencias = new Preferencias( ValidadorActivity.this );
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if( tokenDigitado.equals( tokenGerado ) ){
                    Toast.makeText(ValidadorActivity.this, "Token validado com sucesso!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Token não foi validado!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
