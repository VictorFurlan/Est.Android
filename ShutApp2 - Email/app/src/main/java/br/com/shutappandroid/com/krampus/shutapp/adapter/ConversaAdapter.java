package br.com.shutappandroid.com.krampus.shutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.model.Conversa;

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);

        this.conversas = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        //testar se a lista de contatos não for vazia
        if(conversas != null){

            //inicializa o objeto para criação da view com recursos do system
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar a view a partir de um xlm
            view = inflater.inflate(R.layout.lista_contato, parent, false);

            //recupera o layout para exibição
            TextView nomeContato = view.findViewById(R.id.tv_titulo);
            TextView mensagem = view.findViewById(R.id.tv_subtitulo);

            Conversa conversa  = conversas.get( position );
            nomeContato.setText(conversa.getNome());
            mensagem.setText(conversa.getMensagem());
        }
        return view;
    }
}
