package br.com.shutappandroid.com.krampus.shutapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.helper.Permissao;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText numDDD;
    private EditText numPais;
    private Button botaoCadastrar;

    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.SEND_SMS,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

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
                String mansagemEnvio = "ShutApp Codigo de Confirmação: " + token;

                //Salvar os dados
                Preferencias preferencias = new Preferencias( LoginActivity.this );
                preferencias.salvarUsuarioPreferencia(nomeUsuario,telSemFormatacao,token);

                /*
                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("TOKEN","T: - " + usuario.get("token"));
                */

                boolean mensagemEnviada = enviaSMS("+"+telSemFormatacao,mansagemEnvio);

                if(mensagemEnviada){

                    Intent intent = new Intent( LoginActivity.this, ValidadorActivity.class );
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean enviaSMS (String telefone, String mensagem){
        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int resultado : grantResults ){ //verifica se alguma permissão foi negada

            if( resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Permissões nogadas");
        builder.setMessage("Para utilizar este app é necessário aceitar as parmissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}