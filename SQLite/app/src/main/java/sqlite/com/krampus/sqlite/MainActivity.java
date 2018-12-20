package sqlite.com.krampus.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            //criar banco de dados SQLite
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(nome VARCHAR, idade INT(3))");

            //inserir dados na tabela
            bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Victor', 28)");
            bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Jessica', 28)");

            //recuperar as informações da tabela
            Cursor cursor = bancoDados.rawQuery("SELECT nome, idade FROM pessoas", null);

            //recuperar o indice de cada coluna
            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");

            //Voltar o cursor para o começo da tabela
            cursor.moveToFirst();
            while (cursor != null) {

                Log.i("RESUTADO - nome: ", cursor.getString(indiceColunaNome));
                Log.i("RESUTADO - idade: ", cursor.getString(indiceColunaIdade));
                cursor.moveToNext();
            }// resultado no logcat em info
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
