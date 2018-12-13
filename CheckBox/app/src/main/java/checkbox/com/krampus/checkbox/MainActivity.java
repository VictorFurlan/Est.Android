package checkbox.com.krampus.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkboxCao;
    private CheckBox checkboxGato;
    private CheckBox checkboxPapagaio;

    private Button botaoMostrar;
    private TextView textoExibicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkboxCao = (CheckBox) findViewById(R.id.checkBoxCaoId);
        checkboxGato = (CheckBox) findViewById(R.id.checkBoxGatoId);
        checkboxPapagaio = (CheckBox) findViewById(R.id.checkBoxPapagaioId);

        botaoMostrar = (Button) findViewById(R.id.botaoMostrarId);
        textoExibicao = (TextView) findViewById(R.id.textoExibiçãoId);


        botaoMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itensSelecionados = "";

                itensSelecionados += "Item: " + checkboxCao.getText() + "Status: " + checkboxCao.isChecked() + "\n";
                itensSelecionados += "Item: " + checkboxGato.getText() + "Status: " + checkboxGato.isChecked() + "\n";
                itensSelecionados += "Item: " + checkboxPapagaio.getText() + checkboxPapagaio.isChecked()  + "\n";

                textoExibicao.setText(itensSelecionados);
            }
        });

    }
}
