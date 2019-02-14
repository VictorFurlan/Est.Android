package com.example.krampus.listapokemon.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.krampus.listapokemon.R;
import com.example.krampus.listapokemon.controler.PokeAdapter;
import com.example.krampus.listapokemon.interfaces.PokeInterface;
import com.example.krampus.listapokemon.model.PokeGet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "LISTAPOKEMON";

    private RecyclerView recyclerView;
    private PokeAdapter listaPokemonAdapter;

    private int offset;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_pokemon);
        listaPokemonAdapter = new PokeAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int childCount = layoutManager.getChildCount();
                    int itemCount = layoutManager.getItemCount();
                    int firstPosition = layoutManager.findFirstVisibleItemPosition();

                    if(flag){
                        if( (childCount + firstPosition) > itemCount ){
                            flag = false;
                            offset+=20;
                            obterDados(offset);
                        }

                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        flag = true;
        offset = 0;
        obterDados(offset);
    }

    private void obterDados(int offset){
        PokeInterface service = retrofit.create(PokeInterface.class);
        Call<PokeGet> pokemoncall = service.listaPokemon(20, offset);

        pokemoncall.enqueue(new Callback<PokeGet>() {
            @Override
            public void onResponse(Call<PokeGet> call, Response<PokeGet> response) {
                flag = true;
                if(response.isSuccessful()){

                    PokeGet pokeGet = response.body();

                    ArrayList<PokeGet> listaPokemon = pokeGet.getResults();

                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else
                    Log.e(TAG, " on response "+ response.errorBody());

            }

            @Override
            public void onFailure(Call<PokeGet> call, Throwable t) {
                flag = true;
                Log.e(TAG," on Failure "+ t.getMessage());
            }
        });
    }
}
