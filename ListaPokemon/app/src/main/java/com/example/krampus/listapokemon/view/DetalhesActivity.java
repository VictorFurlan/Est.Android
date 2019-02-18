package com.example.krampus.listapokemon.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.krampus.listapokemon.R;
import com.example.krampus.listapokemon.controler.AbilityAdapter;
import com.example.krampus.listapokemon.controler.AbilityGet;
import com.example.krampus.listapokemon.controler.PokeGet;
import com.example.krampus.listapokemon.interfaces.AbilityInterface;
import com.example.krampus.listapokemon.interfaces.PokeInterface;
import com.example.krampus.listapokemon.model.PokeAbiliy;
import com.example.krampus.listapokemon.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalhesActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int pokemon = bundle.getInt("NumberPokemon");
        String URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +  pokemon + ".png";

        imageView = (ImageView) findViewById(R.id.image_detalhes);
        recyclerView = (RecyclerView) findViewById(R.id.rv_detalhes);

        loadImage(URL);
    }
    private void loadImage(String url){
        Picasso.with(this).load(url)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
