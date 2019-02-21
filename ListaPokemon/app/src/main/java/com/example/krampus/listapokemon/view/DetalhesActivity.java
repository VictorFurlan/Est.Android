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

    int idPokemon = 0;
    private Retrofit retrofit;
    private ImageView imageView;
    private ImageView imageViewShiny;
    private TextView tvType;
    private TextView tvName;
    private TextView tvAttack;
    private TextView tvHP;
    private TextView tvDefense;
    private TextView tvSpeed;
    private TextView tvSpAttack;
    private TextView tvSpDefense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idPokemon = bundle.getInt("NumberPokemon");

        String URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +  idPokemon + ".png";
        String URL_SHINY = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"+ idPokemon +".png";

        imageView = (ImageView) findViewById(R.id.iv_image);
        imageViewShiny = (ImageView) findViewById(R.id.fotoImageViewShiny);
        tvName = (TextView) findViewById(R.id.tv_detail_name);
        tvType = (TextView) findViewById(R.id.tv_detail_types);
        tvAttack = (TextView) findViewById(R.id.attackTextView);
        tvHP = (TextView) findViewById(R.id.qtdTextViewHP);
        tvDefense = (TextView) findViewById(R.id.defenseTextView);
        tvSpeed = (TextView) findViewById(R.id.speedTextView);
        tvSpAttack = (TextView) findViewById(R.id.spAttackTextView);
        tvSpDefense= (TextView) findViewById(R.id.spDefenseTextView);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadImage(URL, imageView);
        loadImage(URL_SHINY, imageViewShiny);
        requestDataPokemon(idPokemon);
    }

    private void loadImage(String url, ImageView imView){
        try {
            Picasso.with(this).load(url)
                    .error(R.mipmap.ic_launcher)
                    .into(imView, new com.squareup.picasso.Callback() {

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
                    tvAttack.setText(pokemon.pokeStatsToString("attack"));
                    tvHP.setText(pokemon.pokeStatsToString("hp"));
                    tvDefense.setText(pokemon.pokeStatsToString("defense"));
                    tvSpeed.setText(pokemon.pokeStatsToString("speed"));
                    tvSpAttack.setText(pokemon.pokeStatsToString("special-attack"));
                    tvSpDefense.setText(pokemon.pokeStatsToString("special-defense"));

                }else{
                    Log.e("POKEDEX", " on response "+ response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("TIPOS: ","FAIL " + t.getCause() );
            }
        });
    }
}
