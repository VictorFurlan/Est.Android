package gasolinaoualcool.com.krampus.gasolinaoualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText alcool;
    private EditText gasolina;
    private Button botaoVerificar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alcool = (EditText) findViewById(R.id.precoAlcoolId);
        gasolina = (EditText) findViewById(R.id.precoGasolinaId);
        botaoVerificar = (Button) findViewById(R.id.botaoVerificarId);
        textoResultado = (TextView) findViewById(R.id.textoResultadoId);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoPrecoAlcool = alcool.getText().toString();
                String textoPrecoGasolina = gasolina.getText().toString();

                Double valorAlcool = Double.parseDouble(textoPrecoAlcool);
                Double valorGasolina = Double.parseDouble(textoPrecoGasolina);

                double resultado = valorAlcool/valorGasolina;

                if(resultado >= 0.7){

                    textoResultado.setText("É melhor ultilizar a Gasolina");

                }else{

                    textoResultado.setText("É melhor ultilizar o Alcool");

                }

            }
        });

    }
}
