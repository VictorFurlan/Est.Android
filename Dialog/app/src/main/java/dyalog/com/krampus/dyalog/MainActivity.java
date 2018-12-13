package dyalog.com.krampus.dyalog;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = (Button) findViewById(R.id.botaoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Criar Alert
                dialog = new AlertDialog.Builder(MainActivity.this);

                //configurar Dialog
                dialog.setTitle("Titulo Dialog");

                //configurar msg
                dialog.setMessage("Mensagem da dialog");

                //Não fecha janela clicando fora
                dialog.setCancelable(false);

                //Icone janela
                dialog.setIcon(android.R.drawable.ic_delete);

                //botao "nao"
                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Pressionado botão não", Toast.LENGTH_SHORT).show();
                            }
                        });

                //botao "sim"
                dialog.setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Pressionado botão sim", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.create();
                dialog.show();
            }
        });

    }
}
