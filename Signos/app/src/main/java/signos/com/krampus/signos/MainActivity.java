package signos.com.krampus.signos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaDeSignos;
    private String[] signos = {"a","b","c","d","e","f","g"};
    private String[] perfis = {"aaaaa","bbbbbb","ccccccccc","dddddddd","eeeeeee","ffffffff","gggggggggg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listaDeSignos = (ListView) findViewById(R.id.ListViewId);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                signos
        );

        listaDeSignos.setAdapter(adapter);

        listaDeSignos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int codigoPosicao = i;

                Toast.makeText(getApplicationContext(), perfis[codigoPosicao], Toast.LENGTH_LONG).show();
            }
        });
    }
}
