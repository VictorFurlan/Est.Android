package com.example.krampus.listapokemon.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krampus.listapokemon.R;
import com.example.krampus.listapokemon.interfaces.PokeInterface;
import com.example.krampus.listapokemon.model.Pokemon;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalhesActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ImageView imageView;
    private TextView tvType;
    private TextView tvName;

    int idPokemon = 0;

    int idPokemon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idPokemon = bundle.getInt("NumberPokemon");

        String URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +  idPokemon + ".png";

        imageView = (ImageView) findViewById(R.id.iv_image_detail);
        tvName = (TextView) findViewById(R.id.tv_detail_name);
        tvType = (TextView) findViewById(R.id.tv_detail_types);

        loadImage(URL);

        loadImage(URL);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestDataPokemon(idPokemon);
    }
    private void loadImage(String url){
        try {
            Picasso.with(this).load(url)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView, new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess(){}

                        @Override
                        public void onError() {}
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestDataPokemon(final int idPokemon) {

        PokeInterface service = retrofit.create(PokeInterface.class);
        Call<Pokemon> pokemonRespostaCall = service.obterPokemon(idPokemon);

        pokemonRespostaCall.enqueue(new Callback<Pokemon>(){
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                if (response.isSuccessful()) {

                    Pokemon pokemon = response.body();

                    tvName.setText(pokemon.getName());
                    tvType.setText(pokemon.pokeTypesToString());

                }else{
                    Log.e("POKEDEX", " on response "+ response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.i("TIPOS: ","FAIL" + t.getCause() );
            }
        });
    }
}
