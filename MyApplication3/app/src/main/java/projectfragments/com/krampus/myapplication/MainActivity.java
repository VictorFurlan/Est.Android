package projectfragments.com.krampus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*
    ParseObject pontuacao = new ParseObject("Pontuacao");
    pontuacao.put("nome","Victor");
    pontuacao.put("pontos",100);
    pontuacao.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if(e == null){
                Log.i("salvarPontos","Dados salvos");
            }else{
                Log.i("salvarPontos","Erro ao salvar");
            }
        }
    });*/

      ParseQuery<ParseObject> consulta = ParseQuery.getQuery("Pontuacao");
      consulta.getInBackground("xnPM569kD8", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if(e == null){

                  object.put("pontos", 500);
                  object.saveInBackground();

              }else{
                  Log.i("consultaPontos",e.getMessage());
              }
          }
      });

  }
}
