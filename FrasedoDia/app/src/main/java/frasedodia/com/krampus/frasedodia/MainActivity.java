package frasedodia.com.krampus.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView texoNovafrase;
    private Button botaoNovaFrase;

    private String[] frases = {"frases", "outra frase", "mais uma frase"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texoNovafrase = (TextView) findViewById(R.id.textoNovaFraseId);
        botaoNovaFrase = (Button) findViewById(R.id.botaoNovaFraseId);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomico = new Random();

                int numeroAleatorio = randomico.nextInt(frases.length);

                texoNovafrase.setText( frases[numeroAleatorio] );
            }
        });

    }
}
