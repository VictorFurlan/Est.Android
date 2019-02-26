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
import br.com.shutappandroid.com.krampus.shutapp.adapter.ConversaAdapter;
import br.com.shutappandroid.com.krampus.shutapp.config.ConfiguracaoFirebase;
import br.com.shutappandroid.com.krampus.shutapp.helper.Base65Custom;
import br.com.shutappandroid.com.krampus.shutapp.helper.Preferencias;
import br.com.shutappandroid.com.krampus.shutapp.model.Contato;
import br.com.shutappandroid.com.krampus.shutapp.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference firebase;
    private ArrayList<Contato> contatos;
    private ValueEventListener valueEventListenerConversa;


    public ConversasFragment() {
        // Required empty public constructor
    }

    //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    //Manipula a vida util do fragment para não ficar rodando sempre a espera de mudanças do banco
    @Override
    public void onStart() {
        super.onStart();
        firebase.addListenerForSingleValueEvent(valueEventListenerConversa);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversa);
    }
    //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciar objetos
        conversas = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        //monta listview e adapter
        listView = view.findViewById(R.id.lv_conversas);
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();

        // Adapter criado para cada contato da lista
        adapter = new ConversaAdapter(getActivity(), conversas);

        listView.setAdapter(adapter);

        //recupera contatos do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("conversas")
                .child(identificadorUsuarioLogado);

        //Listener para recuperar os contatos
        //Só atualizará os contatos se houver alteração no nó do banco setado a cima
        valueEventListenerConversa = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //limpa a lista de contatos para lista atualizada
                conversas.clear();

                //busca todos os contatos do usuarios logado
                for ( DataSnapshot dados:dataSnapshot.getChildren()){

                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add( conversa );
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

                Conversa conversa = conversas.get(position);

                intent.putExtra("nome", conversa.getNome());
                String email = Base65Custom.decodificarBase64(conversa.getIdUsuario());
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });

        return view;
    }
}
