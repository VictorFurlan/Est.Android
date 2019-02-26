package br.com.shutappandroid.com.krampus.shutapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.shutappandroid.com.krampus.shutapp.R;
import br.com.shutappandroid.com.krampus.shutapp.activity.ConversaActivity;
import br.com.shutappandroid.com.krampus.shutapp.activity.LoginActivity;
import br.com.shutappandroid.com.krampus.shutapp.adapter.ContatoAdapter;
import br.com.shutappandroid.com.krampus.shutapp.config.ConfiguracaoFirebase;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;
import br.com.shutappandroid.com.krampus.shutapp.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    //Manipula a vida util do fragment para não ficar rodando sempre a espera de mudanças do banco
    @Override
    public void onStart() {
        super.onStart();
        firebase.addListenerForSingleValueEvent(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
    }
    //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Instanciar objetos
        contatos = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //monta listview e adapter
        listView = view.findViewById(R.id.lv_contatos);
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();

        // Adapter criado para cada contato da lista
        adapter = new ContatoAdapter(getActivity(), contatos);

        listView.setAdapter(adapter);

        //recupera contatos do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
        .child("contatos")
        .child(identificadorUsuarioLogado);

        //Listener para recuperar os contatos
        //Só atualizará os contatos se houver alteração no nó do banco setado a cima
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //limpa a lista de contatos para lista atualizada
                contatos.clear();

                //busca todos os contatos do usuarios logado
                for ( DataSnapshot dados:dataSnapshot.getChildren()){

                    Contato contato = dados.getValue(Contato.class);
                    contatos.add( contato );
                }
                //avisa o ArrayAdapter que foi atualizado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(position);

                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());

                startActivity(intent);
            }
        });

        return view;
    }
}