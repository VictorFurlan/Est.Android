package sharedpreferences.com.krampus.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textoNome;
    private Button botaoSalvar;
    private TextView textoExibicao;

    // final - o valor não pode ser mudado
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNome = (EditText) findViewById(R.id.textoNomeId);
        botaoSalvar = (Button) findViewById(R.id.botaoSalvarId);
        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if( textoNome.getText().toString().equals("") ){
                    Toast.makeText(MainActivity.this, "Pro favor, preencher o nome.", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("nome", textoNome.getText().toString());//gravando no arquivo com uma chave "nome"
                    editor.commit();//confirmar que a edição pode ser salva no arquivo
                    textoExibicao.setText("Olá, "+ textoNome.getText().toString());
                }
            }
        });

        //Recuperar os dados do arquivo
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if( sharedPreferences.contains("nome")){
            String nomeUsiario = sharedPreferences.getString("nome", "usuário não definido");//parametros - a chave definica e um texto caso não ache a chave
            textoExibicao.setText("Olá, " + nomeUsiario);
        }else{
            textoExibicao.setText("Olá, usuário não definido");
        }
    }
}
